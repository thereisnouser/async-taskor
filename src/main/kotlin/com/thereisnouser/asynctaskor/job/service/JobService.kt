package com.thereisnouser.asynctaskor.job.service

import com.github.f4b6a3.uuid.UuidCreator
import com.thereisnouser.asynctaskor.job.domain.Job
import com.thereisnouser.asynctaskor.job.domain.JobRepository
import com.thereisnouser.asynctaskor.job.dto.CreateJobCommand
import com.thereisnouser.asynctaskor.job.entity.JobStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant
import java.util.*

@Service
class JobService(
    private val jobRepository: JobRepository,
) {

    @Transactional
    fun createJob(cmd: CreateJobCommand): UUID {
        val now = Instant.now()
        val job = Job(
            id = UuidCreator.getTimeOrderedEpoch(),
            type = cmd.type,
            status = JobStatus.QUEUED,
            srcKey = cmd.srcKey,
            dstKey = null,
            error = null,
            createdAt = now,
            completedAt = null,
        )

        return jobRepository.createJob(job)
    }

    @Transactional
    fun processNextJob(): Job? {
        return jobRepository.pickQueuedAndMarkRunning()
    }

    @Transactional
    fun markDone(id: UUID, dstKey: String): Boolean {
        val job = jobRepository.findById(id) ?: return false
        job.complete(dstKey)
        jobRepository.save(job)
        return true
    }

    @Transactional
    fun markFailed(id: UUID, error: String): Boolean {
        val job = jobRepository.findById(id) ?: return false
        job.fail(error)
        jobRepository.save(job)
        return true
    }

}