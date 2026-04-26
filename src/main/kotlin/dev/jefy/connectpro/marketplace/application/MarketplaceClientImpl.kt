package dev.jefy.connectpro.marketplace.application

import dev.jefy.connectpro.marketplace.MarketplaceClient
import dev.jefy.connectpro.marketplace.domain.repository.JobApplicationRepository
import dev.jefy.connectpro.marketplace.domain.vo.JobApplicationId
import dev.jefy.connectpro.portfolio.domain.vo.JobPostId
import dev.jefy.connectpro.user.UserClient
import org.springframework.stereotype.Service


/**
 * @author Jôph Yamba
 */
@Service
class MarketplaceClientImpl(val jobApplicationRepo: JobApplicationRepository, val userClient: UserClient) : MarketplaceClient {
    override fun jobPostHasApplications(jobPostId: JobPostId): Boolean {
        val user = userClient.getCurrentUser()
        val id = JobApplicationId(user.id, jobPostId.value)
        return jobApplicationRepo.existsById(id)
    }
}
