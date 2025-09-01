package com.thereisnouser.asynctaskor.job.domain

import com.thereisnouser.asynctaskor.job.entity.JobStatus
import com.thereisnouser.asynctaskor.job.entity.JobType
import java.time.Instant
import java.util.*

class Job(
    val id: UUID,
    val type: JobType,
    status: JobStatus,
    val srcKey: String,
    dstKey: String?,
    error: String?,
    val createdAt: Instant,
    completedAt: Instant?
) {

    var status: JobStatus = status
        private set

    var dstKey: String? = dstKey
        private set

    var error: String? = error
        private set

    var completedAt: Instant? = completedAt
        private set

    fun complete(dstKey: String) {
        require(status == JobStatus.RUNNING) { "Job must be RUNNING to complete" }

        this.status = JobStatus.SUCCESS
        this.dstKey = dstKey
        this.completedAt = Instant.now()
    }

    fun fail(reason: String) {
        require(status == JobStatus.RUNNING) { "Job must be RUNNING to complete" }

        this.status = JobStatus.FAILED
        this.error = reason
        this.completedAt = Instant.now()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Job

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

}
