package dev.jefy.connectpro.marketplace.application.command

import dev.jefy.connectpro.marketplace.application.dtos.JobApplicationRequest
import dev.jefy.connectpro.marketplace.application.exception.JobApplicationNotFound
import dev.jefy.connectpro.marketplace.application.exception.JobPostNotExistOrValidException
import dev.jefy.connectpro.marketplace.domain.model.JobApplication
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
@Transactional
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
        val application = JobApplication(UserId(user.id), jobPostId, request.motivation)
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