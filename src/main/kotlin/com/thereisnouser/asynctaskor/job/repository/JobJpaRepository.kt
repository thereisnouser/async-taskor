package com.thereisnouser.asynctaskor.job.repository

import com.thereisnouser.asynctaskor.job.entity.JobEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface JobJpaRepository : JpaRepository<JobEntity, UUID>