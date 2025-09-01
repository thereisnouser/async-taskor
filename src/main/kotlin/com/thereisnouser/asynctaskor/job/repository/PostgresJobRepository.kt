package com.thereisnouser.asynctaskor.job.repository

import com.thereisnouser.asynctaskor.job.domain.Job
import com.thereisnouser.asynctaskor.job.domain.JobRepository
import com.thereisnouser.asynctaskor.job.entity.JobEntity
import com.thereisnouser.asynctaskor.job.entity.JobStatus
import com.thereisnouser.asynctaskor.job.entity.JobType
import org.springframework.data.repository.findByIdOrNull
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository
import java.sql.ResultSet
import java.util.*

@Repository
class PostgresJobRepository(
    private val jpa: JobJpaRepository,
    private val jdbc: JdbcTemplate,
) : JobRepository {

    override fun createJob(job: Job): UUID {
        val e = JobEntity.fromDomain(job)
        return jpa.save(e).id
    }

    override fun save(job: Job) {
        val e = JobEntity.fromDomain(job)
        jpa.save(e).id
    }

    override fun findById(id: UUID): Job? {
        return jpa.findByIdOrNull(id)?.toDomain()
    }

    override fun pickQueuedAndMarkRunning(): Job? {
        val sql = """
            WITH queued AS (
              SELECT id
              FROM jobs
              WHERE status = 'QUEUED'
              ORDER BY created_at ASC
              FOR UPDATE SKIP LOCKED
              LIMIT 1
            )
            UPDATE jobs j
            SET status = 'RUNNING'
            FROM queued
            WHERE j.id = queued.id
            RETURNING j.id, j.type, j.status, j.src_key, j.dst_key, j.error, j.created_at, j.completed_at
        """.trimIndent()

        val mapper = RowMapper<Job> { rs: ResultSet, _: Int ->
            Job(
                id = UUID.fromString(rs.getString("id")),
                type = JobType.valueOf(rs.getString("type")),
                status = JobStatus.valueOf(rs.getString("status")),
                srcKey = rs.getString("src_key"),
                dstKey = rs.getString("dst_key"),
                error = rs.getString("error"),
                createdAt = rs.getTimestamp("created_at").toInstant(),
                completedAt = rs.getTimestamp("completed_at")?.toInstant()
            )
        }

        return jdbc.query(sql, mapper).firstOrNull()
    }

}