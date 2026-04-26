package dev.jefy.connectpro.marketplace

import dev.jefy.connectpro.portfolio.domain.vo.JobPostId

/**
 * @author Jôph Yamba
 */
interface MarketplaceClient {
    fun jobPostHasApplications(jobPostId: JobPostId): Boolean
} 
