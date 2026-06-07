package dev.jefy.connectpro.portfolio.application


import dev.jefy.connectpro.management.ManagementClient
import dev.jefy.connectpro.management.domain.vo.AwardId
import dev.jefy.connectpro.management.domain.vo.BadgeId
import dev.jefy.connectpro.management.domain.vo.CategoryId
import dev.jefy.connectpro.marketplace.MarketplaceClient
import dev.jefy.connectpro.marketplace.application.dtos.JobPostListingResponse
import dev.jefy.connectpro.marketplace.application.dtos.toListingResponse
import dev.jefy.connectpro.portfolio.PortfolioClient
import dev.jefy.connectpro.portfolio.application.exceptions.JobPostNotFoundException
import dev.jefy.connectpro.portfolio.application.exceptions.PortfolioNotFoundException
import dev.jefy.connectpro.portfolio.application.exceptions.ServiceNotFoundException
import dev.jefy.connectpro.portfolio.domain.repository.JobPostRepository
import dev.jefy.connectpro.portfolio.domain.repository.PortfolioRepository
import dev.jefy.connectpro.portfolio.domain.repository.ServiceRepository
import dev.jefy.connectpro.portfolio.domain.vo.JobPostId
import dev.jefy.connectpro.portfolio.domain.vo.PortfolioId
import dev.jefy.connectpro.portfolio.domain.vo.ServiceId
import dev.jefy.connectpro.shared.application.dtos.PortfolioData
import dev.jefy.connectpro.shared.application.dtos.toSummaryData
import dev.jefy.connectpro.shared.infrastructure.file_storage.ImageUrlResolver
import dev.jefy.connectpro.user.domain.vo.UserId
import jakarta.transaction.Transactional
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.util.*

/**
 * @author Jôph Yamba
 */

@Service
@Transactional
class PortfolioClientImpl(
    private val portfolioRepo: PortfolioRepository,
    private val serviceRepo: ServiceRepository,
    private val jobPostRepo: JobPostRepository,
    private val managementClient: ManagementClient,
    private val marketplaceClient: MarketplaceClient,
    private val resolver: ImageUrlResolver
) : PortfolioClient {

    private val log = LoggerFactory.getLogger(javaClass)

    override fun getPortfolioSummary(id: UserId): Optional<PortfolioData> {
        log.info("Fetching portfolio summary for userId: {}", id.value)

        val data = portfolioRepo.findByUserId(id).map { it.toSummaryData(resolver) }

        log.info(
            "Portfolio summary for userId: {} is present: {}",
            id.value,
            data.isPresent
        )

        return data
    }

    override fun getPortfolioSummary(portfolioId: PortfolioId): Optional<PortfolioData> =
        portfolioRepo.findById(portfolioId).map { it.toSummaryData(resolver) }

    override fun notExistsAndValidService(serviceId: ServiceId): Boolean {
        val service = serviceRepo.findById(serviceId)
            .orElseThrow { ServiceNotFoundException() }

        if (service.isNotActive())  return true
        
        val portfolio = getPortfolioSummaryData(service.portfolioId)
        return !portfolio.active
    }

    override fun isCategoryInUse(categoryId: CategoryId): Boolean = 
        serviceRepo.existsByCategoryId(categoryId) || jobPostRepo.existsByCategoryId(categoryId)
    
    override fun isBadgeInUse(badgeId: BadgeId): Boolean = portfolioRepo.existsByBadgeId(badgeId)
    
    override fun isAwardInUse(awardId: AwardId): Boolean = serviceRepo.existsByAwardId(awardId)
    
    override fun existsAndValidJobPost(jobPostId: JobPostId): Boolean {
        val jobPost = jobPostRepo.findById(jobPostId).orElseThrow { JobPostNotFoundException() }

        if (jobPost.isClosed) return false
        
        val portfolio = getPortfolioSummaryData(jobPost.portfolioId)
        return portfolio.active
    }

    override fun getJobPostListing(jobPostId: JobPostId): JobPostListingResponse = jobPostRepo
        .findById(jobPostId)
        .map { job ->
            val portfolio = getPortfolioSummaryData(job.portfolioId)
            val category = managementClient.getCategory(job.categoryId)
            val userApplied: Boolean = marketplaceClient.hasUserAppliedToJob(jobPostId = job.id);
            job.toListingResponse(
                portfolio = portfolio,
                category = category,
                userApplied = userApplied
            )
        }
        .orElseThrow { JobPostNotFoundException() }

    private fun getPortfolioSummaryData(portfolioId: PortfolioId): PortfolioData = portfolioRepo
        .findById(portfolioId)
        .map { it.toSummaryData(resolver) }
        .orElseThrow { PortfolioNotFoundException() }

    override fun getServiceCategoryId(targetId: UUID): Optional<CategoryId> = serviceRepo
        .findById(ServiceId(targetId))
        .map { it.categoryId }

    override fun getJobPostCategoryId(targetId: UUID): Optional<CategoryId> = jobPostRepo
        .findById(JobPostId(targetId))
        .map { it.categoryId }
}