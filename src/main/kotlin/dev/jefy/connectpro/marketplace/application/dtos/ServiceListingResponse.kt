package dev.jefy.connectpro.marketplace.application.dtos

import dev.jefy.connectpro.management.appliacaion.dtos.AwardResponse
import dev.jefy.connectpro.management.appliacaion.dtos.CategoryResponse
import dev.jefy.connectpro.portfolio.domain.model.PService
import dev.jefy.connectpro.portfolio.domain.vo.Tag
import dev.jefy.connectpro.shared.application.dtos.PortfolioSummaryData
import dev.jefy.connectpro.shared.application.dtos.PricingData
import dev.jefy.connectpro.shared.application.dtos.toData
import java.util.*


/**
 * @author Jôph Yamba
 */
data class ServiceListingResponse(
    val id: UUID,
    val portfolio: PortfolioSummaryData,
    val title: String,
    val description: String,
    val category: CategoryResponse,
    val tags: List<String>,
    val coverImageUrl: String?,
    val pricing: PricingData?,
    val award: AwardResponse?,
    val reviewData: ServiceReviewData
)

fun PService.toListingResponse(
    portfolio: PortfolioSummaryData, category: CategoryResponse, award: AwardResponse?, reviewData: ServiceReviewData
) = ServiceListingResponse(
    id = this.id.value,
    portfolio = portfolio,
    title = this.title,
    description = this.description,
    category = category,
    tags = this.tags.map{ it.value},
    coverImageUrl = this.coverImageUrl?.value,
    pricing = this.pricing?.toData(),
    award = award,
    reviewData = reviewData
)