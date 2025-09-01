package com.thereisnouser.asynctaskor.job.dto

import com.thereisnouser.asynctaskor.job.entity.JobStatus
import com.thereisnouser.asynctaskor.job.entity.JobType

data class JobDto(
    val jobId: String,
    val type: JobType,
    val status: JobStatus,
    val error: String? = null,
)
