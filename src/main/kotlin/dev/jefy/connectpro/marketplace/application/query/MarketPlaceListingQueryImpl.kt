package dev.jefy.connectpro.marketplace.application.query

import dev.jefy.connectpro.engagement.EngagementClient
import dev.jefy.connectpro.management.ManagementClient
import dev.jefy.connectpro.management.domain.vo.CategoryId
import dev.jefy.connectpro.marketplace.MarketplaceClient
import dev.jefy.connectpro.marketplace.application.dtos.JobPostListingResponse
import dev.jefy.connectpro.marketplace.application.dtos.SearchRequest
import dev.jefy.connectpro.marketplace.application.dtos.ServiceListingResponse
import dev.jefy.connectpro.marketplace.application.dtos.toListingResponse
import dev.jefy.connectpro.portfolio.PortfolioClient
import dev.jefy.connectpro.portfolio.application.exceptions.AwardNotFoundException
import dev.jefy.connectpro.portfolio.domain.model.JobPost
import dev.jefy.connectpro.portfolio.domain.model.Service
import dev.jefy.connectpro.portfolio.domain.repository.JobPostRepository
import dev.jefy.connectpro.portfolio.domain.repository.ServiceRepository
import dev.jefy.connectpro.portfolio.domain.vo.PortfolioId
import dev.jefy.connectpro.recommandation.RecommandationClient
import dev.jefy.connectpro.shared.application.dtos.PageResponse
import dev.jefy.connectpro.shared.application.dtos.PortfolioData
import dev.jefy.connectpro.shared.application.dtos.toPageResponse
import dev.jefy.connectpro.shared.infrastructure.annotations.QueryService
import dev.jefy.connectpro.shared.infrastructure.file_storage.ImageUrlResolver
import org.springframework.data.domain.PageRequest

/**
 * @author Jôph Yamba
 */
@QueryService
class MarketPlaceListingQueryImpl(
    private val serviceRepo: ServiceRepository,
    private val jobPostRepo: JobPostRepository,
    private val engagementClient: EngagementClient,
    private val managementClient: ManagementClient,
    private val portfolioClient: PortfolioClient,
    private val recommandationClient: RecommandationClient,
    private val marketplaceClient: MarketplaceClient,
    private val resolver: ImageUrlResolver
) : MarketPlaceListingQuery {

    override fun filterService(request: SearchRequest): PageResponse<ServiceListingResponse> {
        val page = PageRequest.of(request.page, request.size)
        val search = request.search ?: ""
        val categoryId = request.categoryId?.let { CategoryId.of(it) }
        return serviceRepo
            .filter(search, categoryId, page)
            .map{ mapToServiceListingResponse(it) }
            .toPageResponse()
    }

    override fun filterJobPost(request: SearchRequest): PageResponse<JobPostListingResponse> {
        val page = PageRequest.of(request.page, request.size)
        val search = request.search ?: ""
        val categoryId = request.categoryId?.let { CategoryId.of(it) }

        
        return jobPostRepo
            .filter(search, categoryId, page)
            .map{ mapToJobPostListingResponse(it)}
            .toPageResponse()
    }

    override fun getRecommendedServices(page: Int, size: Int): PageResponse<ServiceListingResponse> {
        val categories = recommandationClient.getRecommendedCategories()
        if (categories.isEmpty()) return filterService(SearchRequest(null, page, size, null))

        val pageable = PageRequest.of(page, size)
        
        return serviceRepo
            .filter("", categories[0], pageable)
            .map{ mapToServiceListingResponse(it) }
            .toPageResponse()
    }

    override fun getRecommendedJobPosts(page: Int, size: Int): PageResponse<JobPostListingResponse> {
        val categories = recommandationClient.getRecommendedCategories()
        if (categories.isEmpty()) return filterJobPost(SearchRequest(null, page, size, null))

        val pageable = PageRequest.of(page, size)
        return jobPostRepo
            .filter("", categories[0], pageable)
            .map{ mapToJobPostListingResponse(it) }
            .toPageResponse()
    }

    private val mapToServiceListingResponse: (Service) -> ServiceListingResponse = { service ->
        val category = managementClient.getCategory(service.categoryId)
        val award = service.awardId?.let { 
            managementClient.getAward(it).orElseThrow{ AwardNotFoundException() } 
        }
        val reviewData = engagementClient.getReviewData(service.id)
        val portfolioData = getPortfolioData(service.portfolioId)

        val isLiked = engagementClient.isLiked(service.id)

        service.toListingResponse(portfolioData, category, award, reviewData, resolver, isLiked)
    }

    private val mapToJobPostListingResponse: (JobPost) -> JobPostListingResponse = { jobPost ->
        val category = managementClient.getCategory(jobPost.categoryId)
        val portfolioData = getPortfolioData(jobPost.portfolioId)
        
        val userApplied: Boolean = marketplaceClient.hasUserAppliedToJob(jobPostId = jobPost.id)
        jobPost.toListingResponse(
            portfolio = portfolioData,
            category = category,
            userApplied = userApplied
        )
    }

    private fun getPortfolioData(portfolioId: PortfolioId): PortfolioData =
        portfolioClient.getPortfolioSummary(portfolioId)
            .orElseThrow { IllegalStateException("Portfolio not found") }
}