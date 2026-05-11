package dev.jefy.connectpro.portfolio.application.dtos

import dev.jefy.connectpro.engagement.application.dtos.ReviewResponse
import dev.jefy.connectpro.management.appliacaion.dtos.AwardResponse
import dev.jefy.connectpro.management.appliacaion.dtos.CategoryResponse
import dev.jefy.connectpro.marketplace.application.dtos.ServiceReviewData
import dev.jefy.connectpro.portfolio.domain.model.PService
import dev.jefy.connectpro.shared.application.dtos.PortfolioSummaryData
import dev.jefy.connectpro.shared.application.dtos.PricingData
import dev.jefy.connectpro.shared.application.dtos.toData
import dev.jefy.connectpro.shared.infrastructure.file_storage.ImageUrlResolver
import java.util.*


/**
 * @author Jôph Yamba
 */

data class ServiceResponse(
    val id: UUID,
    val portfolio: PortfolioSummaryData,
    val title: String,
    val description: String,
    val category: CategoryResponse,
    val tags: List<String>,
    val coverImage: String?,
    val images: List<String>,
    val pricing: PricingData?,
    val faqs: List<FAQResponse>,
    val award: AwardResponse?,
    val reviewData: ServiceReviewData,
    val recentReviews: List<ReviewResponse>
)

fun PService.toResponse(
    portfolio: PortfolioSummaryData,
    category: CategoryResponse,
    award: AwardResponse?,
    reviewData: ServiceReviewData,
    recentReviews: List<ReviewResponse>,
    resolver: ImageUrlResolver
): ServiceResponse = ServiceResponse(
    id = this.id.value,
    portfolio = portfolio,
    title = this.title,
    description = this.description,
    category = category,
    tags = this.tags.map{ it.value }.toList(),
    coverImage = resolver.resolve(this.coverImage),
    images = resolver.resolve(this.images),
    pricing = this.pricing?.toData(),
    faqs = this.faqs.map{ it.toResponse() }.toList(),
    award = award,
    reviewData = reviewData,
    recentReviews = recentReviews
)

