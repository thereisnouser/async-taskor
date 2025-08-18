package com.thereisnouser.asynctaskor.controller

import com.thereisnouser.asynctaskor.dto.JobDto
import com.thereisnouser.asynctaskor.dto.JobStatus
import com.thereisnouser.asynctaskor.dto.JobType
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.time.Instant

@RestController
@RequestMapping("/api/v1/img/compression-jobs")
class ImageCompressionController {

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
    fun enqueue(@RequestPart file: MultipartFile): ResponseEntity<JobDto> {
        val job = JobDto("", JobType.IMAGE_COMPRESSION, JobStatus.QUEUED, Instant.now())
        return ResponseEntity.accepted().body(job)
    }

}