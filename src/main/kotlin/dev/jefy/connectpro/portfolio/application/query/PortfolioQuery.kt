package dev.jefy.connectpro.portfolio.application.query

import dev.jefy.connectpro.marketplace.application.dtos.JobPostListingResponse
import dev.jefy.connectpro.marketplace.application.dtos.ServiceListingResponse
import dev.jefy.connectpro.portfolio.application.dtos.JobPostResponse
import dev.jefy.connectpro.portfolio.application.dtos.PortfolioResponse
import dev.jefy.connectpro.portfolio.application.dtos.ServiceResponse
import dev.jefy.connectpro.portfolio.domain.vo.JobPostId
import dev.jefy.connectpro.portfolio.domain.vo.PortfolioId
import dev.jefy.connectpro.portfolio.domain.vo.ServiceId

interface PortfolioQuery {
    fun get(portfolioId: PortfolioId): PortfolioResponse
    fun getJobPost(jobPostId: JobPostId): JobPostResponse
    fun getAllJobPost(portfolioId: PortfolioId): List<JobPostListingResponse>
    fun getService(serviceId: ServiceId): ServiceResponse
    fun getAllService(portfolioId: PortfolioId): List<ServiceListingResponse>
}