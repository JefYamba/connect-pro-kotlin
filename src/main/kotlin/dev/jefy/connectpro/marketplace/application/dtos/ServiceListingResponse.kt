package dev.jefy.connectpro.marketplace.application.dtos

import dev.jefy.connectpro.management.appliacaion.dtos.AwardResponse
import dev.jefy.connectpro.management.appliacaion.dtos.CategoryResponse
import dev.jefy.connectpro.portfolio.domain.model.PService
import dev.jefy.connectpro.shared.application.dtos.PortfolioData
import dev.jefy.connectpro.shared.application.dtos.PricingData
import dev.jefy.connectpro.shared.application.dtos.toData
import dev.jefy.connectpro.shared.infrastructure.file_storage.ImageUrlResolver
import java.util.*


/**
 * @author Jôph Yamba
 */
data class ServiceListingResponse(
    val id: UUID,
    val portfolio: PortfolioData,
    val title: String,
    val description: String,
    val category: CategoryResponse,
    val tags: List<String>,
    val coverImage: String?,
    val images: List<String>,
    val pricing: PricingData?,
    val award: AwardResponse?,
    val reviewData: ServiceReviewData
)

fun PService.toListingResponse(
    portfolio: PortfolioData,
    category: CategoryResponse,
    award: AwardResponse?,
    reviewData: ServiceReviewData,
    resolver: ImageUrlResolver
) = ServiceListingResponse(
    id = this.id.value,
    portfolio = portfolio,
    title = this.title,
    description = this.description,
    category = category,
    tags = this.tags.map{ it.name },
    coverImage = resolver.resolve(this.coverImage),
    images = resolver.resolve(this.images),
    pricing = this.pricing?.toData(),
    award = award,
    reviewData = reviewData
)