package dev.jefy.connectpro.shared.application.dtos

import dev.jefy.connectpro.portfolio.domain.model.Portfolio
import dev.jefy.connectpro.shared.infrastructure.file_storage.ImageUrlResolver
import java.util.*

data class PortfolioData(
    val id: UUID,
    val name: String,
    val coverImage: String?,
    val bio: String,
    val active: Boolean,
    val userId: UUID,
) 

fun Portfolio.toSummaryData(resolver: ImageUrlResolver): PortfolioData {
    return PortfolioData(
        id = this.id.value,
        name = this.name,
        coverImage = resolver.resolve(this.coverImage),
        bio = this.bio,
        active = this.isActive(),
        userId = this.userId.value
    )
}