package dev.jefy.connectpro.portfolio.application.dtos

import java.util.*

data class ServiceRequest(
    val portfolioId: UUID,
    val name: String,
    val description: String,
    val categoryId: UUID,
    val tags: Set<String>,
){
    init {
        require(name.isNotBlank()) { "title must not be blank" }
        require(description.isNotBlank()) { "description must not be blank" }
    }
}