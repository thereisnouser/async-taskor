package com.thereisnouser.asynctaskor.dto

import java.time.Instant

data class JobDto(
    val jobId: String,
    val type: JobType,
    val status: JobStatus,
    val createdAt: Instant,
    val error: JobError? = null,
)

data class JobError(val code: String, val message: String)

enum class JobType {
    IMAGE_COMPRESSION,
}

enum class JobStatus {
    QUEUED,
    RUNNING,
    SUCCESS,
    FAILED,
}