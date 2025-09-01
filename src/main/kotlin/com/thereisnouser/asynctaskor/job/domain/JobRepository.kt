package com.thereisnouser.asynctaskor.job.domain

import java.util.*

interface JobRepository {
    fun createJob(job: Job): UUID
    fun save(job: Job)
    fun findById(id: UUID): Job?
    fun pickQueuedAndMarkRunning(): Job?
}