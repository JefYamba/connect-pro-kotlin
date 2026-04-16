package dev.jefy.connectpro.marketplace.application.query

import dev.jefy.connectpro.marketplace.application.dtos.JobApplicationResponseForEmployer
import dev.jefy.connectpro.marketplace.application.dtos.JobApplicationResponseForUser
import dev.jefy.connectpro.marketplace.domain.vo.JobApplicationId
import dev.jefy.connectpro.portfolio.domain.vo.JobPostId

/**
 * @author Jôph Yamba
 */
interface JobApplicationQuery {
    fun getForUser(jobPostId: JobPostId): JobApplicationResponseForUser
    fun getForEmployer(applicationId: JobApplicationId): JobApplicationResponseForEmployer
    fun findForCurrentUser(): List<JobApplicationResponseForUser>
    fun findByJobPost(jobPostId: JobPostId): List<JobApplicationResponseForEmployer>
}
