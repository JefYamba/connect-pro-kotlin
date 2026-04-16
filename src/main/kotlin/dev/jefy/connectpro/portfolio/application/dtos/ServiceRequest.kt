package dev.jefy.connectpro.portfolio.application.dtos

import dev.jefy.connectpro.shared.application.dtos.PricingData
import java.util.UUID

data class ServiceRequest(
    val portfolioId: UUID,
    val title: String,
    val description: String,
    val categoryId: UUID,
    val tags: Set<String>,
    val pricing: PricingData?
){
    init {
        require(title.isNotBlank()) { "title must not be blank" }
        require(description.isNotBlank()) { "description must not be blank" }
    }
}