package com.thereisnouser.asynctaskor.job.entity

import com.thereisnouser.asynctaskor.job.domain.Job
import jakarta.persistence.*
import java.time.Instant
import java.util.*

@Entity
@Table(name = "jobs")
class JobEntity(
    @Id
    val id: UUID,
    @Enumerated(EnumType.STRING)
    val type: JobType,
    @Enumerated(EnumType.STRING)
    val status: JobStatus,
    val srcKey: String,
    val dstKey: String?,
    val error: String?,
    val createdAt: Instant,
    val completedAt: Instant? = null,
) {

    fun toDomain() = Job(id, type, status, srcKey, dstKey, error, createdAt, completedAt)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as JobEntity

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    companion object {
        fun fromDomain(j: Job) = JobEntity(
            id = j.id,
            type = j.type,
            status = j.status,
            srcKey = j.srcKey,
            dstKey = j.dstKey,
            error = j.error,
            createdAt = j.createdAt,
            completedAt = j.completedAt
        )
    }

}

enum class JobType {
    IMAGE_COMPRESSION,
}

enum class JobStatus {
    QUEUED,
    RUNNING,
    SUCCESS,
    FAILED,
}
