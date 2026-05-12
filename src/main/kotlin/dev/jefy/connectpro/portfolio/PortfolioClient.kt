package dev.jefy.connectpro.portfolio

import dev.jefy.connectpro.management.domain.vo.AwardId
import dev.jefy.connectpro.management.domain.vo.BadgeId
import dev.jefy.connectpro.management.domain.vo.CategoryId
import dev.jefy.connectpro.marketplace.application.dtos.JobPostListingResponse
import dev.jefy.connectpro.portfolio.domain.vo.JobPostId
import dev.jefy.connectpro.portfolio.domain.vo.PortfolioId
import dev.jefy.connectpro.portfolio.domain.vo.ServiceId
import dev.jefy.connectpro.shared.application.dtos.PortfolioData
import dev.jefy.connectpro.user.domain.vo.UserId
import java.util.*

/**
 * @author Jôph Yamba
 */
interface PortfolioClient {
    fun getPortfolioSummary(id: UserId): Optional<PortfolioData>

    fun getPortfolioSummary(portfolioId: PortfolioId): Optional<PortfolioData>

    fun notExistsAndValidService(serviceId: ServiceId): Boolean

    fun isCategoryInUse(categoryId: CategoryId): Boolean

    fun isBadgeInUse(badgeId: BadgeId): Boolean

    fun isAwardInUse(awardId: AwardId): Boolean

    fun existsAndValidJobPost(jobPostId: JobPostId): Boolean

    fun getJobPostListing(jobPostId: JobPostId): JobPostListingResponse

    fun getServiceCategoryId(targetId: UUID): Optional<CategoryId>

    fun getJobPostCategoryId(targetId: UUID): Optional<CategoryId>
}
