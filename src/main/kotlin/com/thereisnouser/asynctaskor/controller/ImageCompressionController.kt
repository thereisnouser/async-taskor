package com.thereisnouser.asynctaskor.controller

import com.github.f4b6a3.uuid.UuidCreator
import com.thereisnouser.asynctaskor.dto.JobDto
import com.thereisnouser.asynctaskor.dto.JobStatus
import com.thereisnouser.asynctaskor.dto.JobType
import com.thereisnouser.asynctaskor.infrastructure.storage.ObjectStorage
import jakarta.validation.constraints.DecimalMax
import jakarta.validation.constraints.DecimalMin
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.time.Instant

@RestController
@RequestMapping("/api/v1/img/compression-jobs")
@Validated
class ImageCompressionController(
    private val objectStorage: ObjectStorage,
) {

    @GetMapping(
        value = ["/{jobId}"],
        produces = [MediaType.APPLICATION_JSON_VALUE],
    )
    fun getJob(@PathVariable jobId: String): ResponseEntity<JobDto> {
        val job = JobDto(jobId, JobType.IMAGE_COMPRESSION, JobStatus.QUEUED, Instant.now())
        return ResponseEntity.ok(job)
    }

    @GetMapping(
        value = ["/{jobId}/result"],
        produces = [MediaType.IMAGE_JPEG_VALUE],
    )
    fun getResult(@PathVariable jobId: String): ResponseEntity<ByteArray> {
        return ResponseEntity.ok(byteArrayOf())
    }

    @PostMapping(
        consumes = [MediaType.MULTIPART_FORM_DATA_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun enqueue(
        @RequestPart file: MultipartFile,
        @RequestParam(defaultValue = "0.8") @DecimalMin("0.1") @DecimalMax("1.0") quality: Double,
    ): ResponseEntity<JobDto> {
        val jobId = UuidCreator.getTimeOrderedEpoch()
        val job = JobDto(jobId.toString(), JobType.IMAGE_COMPRESSION, JobStatus.QUEUED, Instant.now())

        objectStorage.putSrc(jobId.toString(), file.bytes, file.contentType ?: "application/octet-stream")

        return ResponseEntity.accepted().body(job)
    }

}