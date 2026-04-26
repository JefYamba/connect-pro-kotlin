package dev.jefy.connectpro.portfolio.application.query

import dev.jefy.connectpro.engagement.EngagementClient
import dev.jefy.connectpro.management.ManagementClient
import dev.jefy.connectpro.management.appliacaion.dtos.toResponse
import dev.jefy.connectpro.management.domain.repository.BadgeRepository
import dev.jefy.connectpro.marketplace.application.dtos.JobPostListingResponse
import dev.jefy.connectpro.marketplace.application.dtos.ServiceListingResponse
import dev.jefy.connectpro.marketplace.application.dtos.toListingResponse
import dev.jefy.connectpro.portfolio.application.dtos.JobPostResponse
import dev.jefy.connectpro.portfolio.application.dtos.PortfolioResponse
import dev.jefy.connectpro.portfolio.application.dtos.ServiceResponse
import dev.jefy.connectpro.portfolio.application.dtos.toResponse
import dev.jefy.connectpro.portfolio.application.exceptions.JobPostNotFoundException
import dev.jefy.connectpro.portfolio.application.exceptions.PortfolioNotFoundException
import dev.jefy.connectpro.portfolio.application.exceptions.ServiceNotFoundException
import dev.jefy.connectpro.portfolio.domain.model.JobPost
import dev.jefy.connectpro.portfolio.domain.model.PService
import dev.jefy.connectpro.portfolio.domain.model.Portfolio
import dev.jefy.connectpro.portfolio.domain.repository.JobPostRepository
import dev.jefy.connectpro.portfolio.domain.repository.PortfolioRepository
import dev.jefy.connectpro.portfolio.domain.repository.ProjectRepository
import dev.jefy.connectpro.portfolio.domain.repository.ServiceRepository
import dev.jefy.connectpro.portfolio.domain.vo.JobPostId
import dev.jefy.connectpro.portfolio.domain.vo.PortfolioId
import dev.jefy.connectpro.portfolio.domain.vo.ServiceId
import dev.jefy.connectpro.shared.application.dtos.toSummaryData
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.util.Assert

@Service
@Transactional(readOnly = true)
class PortfolioQueryImpl(
    private val portfolioRepo: PortfolioRepository,
    private val jobPostRepo: JobPostRepository,
    private val serviceRepo: ServiceRepository,
    private val badgeRepo: BadgeRepository,
    private val projectRepo: ProjectRepository,
    private val engagementClient: EngagementClient,
    private val managementClient: ManagementClient
) : PortfolioQuery {

    override fun get(portfolioId: PortfolioId): PortfolioResponse {
        Assert.notNull(portfolioId, "id cannot be null")
        val portfolio = getPortfolio(portfolioId)

        val badge = portfolio.badgeId?.let { badgeId ->
            badgeRepo.findById(badgeId).map { it.toResponse() }.orElse(null)
        }

        val services = serviceRepo.findAllByPortfolioId(portfolio.id)
            .map{ mapToServiceListingResponse(it, portfolio) }

        val jobPosts = jobPostRepo.findAllByPortfolioId(portfolio.id)
            .map { mapToJobPostListingResponse(it, portfolio) }

        val projects = projectRepo.findAllByPortfolioId(portfolio.id)
            .map{ it.toResponse()}

        return portfolio.toResponse( badge, services, jobPosts, projects)
    }

    override fun getJobPost(jobPostId: JobPostId): JobPostResponse {
        return jobPostRepo.findById(jobPostId)
            .map{ mapToJobPostResponse(it)}
            .orElseThrow { JobPostNotFoundException() }
    }

    override fun getAllJobPost(portfolioId: PortfolioId): List<JobPostListingResponse> {
        return jobPostRepo.findAllByPortfolioId(portfolioId)
            .map{ mapToJobPostListingResponse(it, getPortfolio(portfolioId)) }
    }

    override fun getService(serviceId: ServiceId): ServiceResponse {
        return serviceRepo.findById(serviceId)
            .map(mapToServiceResponse())
            .orElseThrow { ServiceNotFoundException() }
    }

    override fun getAllService(portfolioId: PortfolioId): List<ServiceListingResponse> {
        return serviceRepo.findAllByPortfolioId(portfolioId)
            .map{ mapToServiceListingResponse(it, getPortfolio(portfolioId)) }
    }

    private fun mapToServiceResponse(): (PService) -> ServiceResponse = { service ->
        val portfolio = getPortfolio(service.portfolioId)
        val category = managementClient.getCategory(service.categoryId)

        val award = service.awardId?.let { managementClient.getAward(it).orElse(null) }

        val reviewData = engagementClient.getReviewData(service.id)
        val recentReviews = engagementClient.recentReviews(service.id)
        
        service.toResponse(
            portfolio.toSummaryData(),
            category,
            award,
            reviewData,
            recentReviews
        )
    }

    private val mapToJobPostResponse : (JobPost) -> JobPostResponse = { jobPost ->
        val portfolio = getPortfolio(jobPost.portfolioId)
        val category = managementClient.getCategory(jobPost.categoryId)

        jobPost.toResponse(portfolio.toSummaryData(), category)
    }
    
    private val mapToServiceListingResponse: (PService, Portfolio) -> ServiceListingResponse = { service,portfolio ->

        val category = managementClient.getCategory(service.categoryId)

        val award = service.awardId?.let { managementClient.getAward(it).orElse(null) }

        val reviewData = engagementClient.getReviewData(service.id)
        
        service.toListingResponse(
            portfolio.toSummaryData(),
            category,
            award,
            reviewData
        )
    }
    

    private val mapToJobPostListingResponse: (JobPost, Portfolio) -> JobPostListingResponse = { jobPost, portfolio ->
        val category = managementClient.getCategory(jobPost.categoryId)
        jobPost.toListingResponse(portfolio.toSummaryData(), category)
    }

    private fun getPortfolio(portfolioId: PortfolioId): Portfolio = portfolioRepo
        .findById(portfolioId)
        .orElseThrow { PortfolioNotFoundException() }
    
}