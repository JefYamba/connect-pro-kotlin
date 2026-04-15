package dev.jefy.connectpro.shared.application.dtos

import dev.jefy.connectpro.portfolio.domain.model.Portfolio
import java.util.UUID

data class PortfolioSummaryData(
    val id: UUID,
    val name: String,
    val coverImageUrl: String?,
    val shortDescription: String,
    val active: Boolean
) 

fun Portfolio.toSummaryData(): PortfolioSummaryData {
    val imageUrl = this.generalInfo.coverImageUrl?.value
    return PortfolioSummaryData(
        id = this.id.value,
        name = this.generalInfo.name,
        coverImageUrl = imageUrl,
        shortDescription = this.generalInfo.shortDescription,
        active = this.isActive
    )
}