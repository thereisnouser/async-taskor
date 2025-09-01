package com.thereisnouser.asynctaskor.job.dto

import com.thereisnouser.asynctaskor.job.entity.JobType

data class CreateJobCommand(
    val srcKey: String,
    val type: JobType,
)
