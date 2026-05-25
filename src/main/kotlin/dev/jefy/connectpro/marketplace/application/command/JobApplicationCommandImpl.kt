package dev.jefy.connectpro.marketplace.application.command

import dev.jefy.connectpro.marketplace.application.dtos.JobApplicationRequest
import dev.jefy.connectpro.marketplace.application.exception.JobApplicationNotFound
import dev.jefy.connectpro.marketplace.application.exception.JobPostNotExistOrValidException
import dev.jefy.connectpro.marketplace.domain.JobApplication
import dev.jefy.connectpro.marketplace.domain.repository.JobApplicationRepository
import dev.jefy.connectpro.marketplace.domain.vo.JobApplicationId
import dev.jefy.connectpro.portfolio.PortfolioClient
import dev.jefy.connectpro.portfolio.domain.vo.JobPostId
import dev.jefy.connectpro.shared.infrastructure.annotations.CommandService
import dev.jefy.connectpro.user.UserClient

/**
 * @author Jôph Yamba
 */
@CommandService
class JobApplicationCommandImpl(
    private val jobApplicationRepo: JobApplicationRepository,
    private val portfolioClient: PortfolioClient,
    private val userClient: UserClient
) : JobApplicationCommand {

    override fun apply(request: JobApplicationRequest): JobApplicationId {
        val jobPostId = JobPostId.of(request.jobPostId)
        if (!portfolioClient.existsAndValidJobPost(jobPostId)) {
            throw JobPostNotExistOrValidException()
        }
        val user = userClient.getCurrentUser()
        val application = JobApplication(
            id = JobApplicationId(applicantId = user.id, jobPostId = jobPostId.value), 
            motivation = request.motivation
        )
        jobApplicationRepo.save(application)

        return application.id
    }

    override fun accept(applicationId: JobApplicationId): JobApplicationId {
        val application = getJobApplication(applicationId)
        application.accept()
        jobApplicationRepo.save(application)
        return application.id
    }

    override fun reject(applicationId: JobApplicationId): JobApplicationId {
        val application = getJobApplication(applicationId)
        application.reject()
        jobApplicationRepo.save(application)
        return application.id
    }

    private fun getJobApplication(applicationId: JobApplicationId): JobApplication = jobApplicationRepo
        .findById(applicationId)
        .orElseThrow { JobApplicationNotFound() }
}