package dev.jefy.connectpro.marketplace.domain.repository

import dev.jefy.connectpro.marketplace.domain.model.JobApplication
import dev.jefy.connectpro.marketplace.domain.vo.JobApplicationId
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

/**
 * @author Jôph Yamba
 */
@Repository
interface JobApplicationRepository : JpaRepository<JobApplication, JobApplicationId> {

    @Query("""
        select application from JobApplication application
        where application.id.applicantId = :applicantId
    """)
    fun findAllByApplicantId(@Param("applicantId") applicantId: UUID): List<JobApplication>

    @Query("""
        select application from JobApplication application
        where application.id.jobPostId = :jobPostId
    """)
    fun findAllByJobPostId(@Param("jobPostId") jobPostId: UUID): List<JobApplication>
}