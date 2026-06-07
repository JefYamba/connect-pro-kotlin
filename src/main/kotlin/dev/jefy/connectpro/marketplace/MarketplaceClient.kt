package dev.jefy.connectpro.marketplace

import dev.jefy.connectpro.portfolio.domain.vo.JobPostId
import dev.jefy.connectpro.user.domain.vo.UserId

/**
 * @author Jôph Yamba
 */
interface MarketplaceClient {
    fun hasUserAppliedToJob(jobPostId: JobPostId, userId: UserId? = null): Boolean
} 
