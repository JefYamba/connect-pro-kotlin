package dev.jefy.connectpro.shared.application.dtos

import dev.jefy.connectpro.portfolio.domain.model.Portfolio
import dev.jefy.connectpro.shared.infrastructure.file_storage.ImageUrlResolver
import java.util.*

data class PortfolioSummaryData(
    val id: UUID,
    val name: String,
    val coverImage: String?,
    val shortDescription: String,
    val active: Boolean
) 

fun Portfolio.toSummaryData(resolver: ImageUrlResolver): PortfolioSummaryData {
    val image = this.generalInfo.coverImage
    return PortfolioSummaryData(
        id = this.id.value,
        name = this.generalInfo.name,
        coverImage = resolver.resolve(image),
        shortDescription = this.generalInfo.shortDescription,
        active = this.isActive()
    )
}