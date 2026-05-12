package dev.jefy.connectpro.marketplace.application.query

import dev.jefy.connectpro.marketplace.application.dtos.JobApplicationResponseForEmployer
import dev.jefy.connectpro.marketplace.application.dtos.JobApplicationResponseForUser
import dev.jefy.connectpro.marketplace.application.dtos.toEmployerResponse
import dev.jefy.connectpro.marketplace.application.dtos.toUserResponse
import dev.jefy.connectpro.marketplace.application.exception.JobApplicationNotFound
import dev.jefy.connectpro.marketplace.domain.JobApplication
import dev.jefy.connectpro.marketplace.domain.repository.JobApplicationRepository
import dev.jefy.connectpro.marketplace.domain.vo.JobApplicationId
import dev.jefy.connectpro.portfolio.PortfolioClient
import dev.jefy.connectpro.portfolio.domain.vo.JobPostId
import dev.jefy.connectpro.user.UserClient
import dev.jefy.connectpro.user.domain.vo.UserId
import org.jspecify.annotations.NullMarked
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * @author Jôph Yamba
 */
@NullMarked
@Service
@Transactional(readOnly = true)
class JobApplicationQueryImpl(
    private val applicationRepo: JobApplicationRepository,
    private val userClient: UserClient,
    private val portfolioClient: PortfolioClient
) : JobApplicationQuery {
    
    override fun getForUser(jobPostId: JobPostId): JobApplicationResponseForUser {
        val currentUser = userClient.getCurrentUser()

        val id = JobApplicationId(
            applicantId = currentUser.id,
            jobPostId = jobPostId.value
        )

        return applicationRepo.findById(id)
            .map(mapForUser)
            .orElseThrow { JobApplicationNotFound() }
    }
    
    override fun getForEmployer(applicationId: JobApplicationId): JobApplicationResponseForEmployer = applicationRepo
        .findById(applicationId)
        .map(mapForEmployer)
        .orElseThrow { JobApplicationNotFound() }

    override fun findForCurrentUser(): List<JobApplicationResponseForUser> {
        val user = userClient.getCurrentUser()
        return applicationRepo
            .findAllByApplicantId(user.id)
            .map(mapForUser)
    }

    override fun findByJobPost(jobPostId: JobPostId): List<JobApplicationResponseForEmployer> =
        applicationRepo
            .findAllByJobPostId(jobPostId.value)
            .map(mapForEmployer)

    private val mapForUser: (JobApplication) -> JobApplicationResponseForUser = { application ->
        val jobPostId = JobPostId.of(application.id.jobPostId)
        val jobPost = portfolioClient.getJobPostListing(jobPostId)
        application.toUserResponse(jobPost)
    }

    private val mapForEmployer: (JobApplication) -> JobApplicationResponseForEmployer = { application ->
        val applicantId = UserId.of(application.id.applicantId)
        val user = userClient.getById(applicantId)
        application.toEmployerResponse(user)
    }
}