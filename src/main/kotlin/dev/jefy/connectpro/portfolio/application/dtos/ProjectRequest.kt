package dev.jefy.connectpro.portfolio.application.dtos

import java.time.LocalDate
import java.util.UUID

data class ProjectRequest(
    val portfolioId: UUID,
    val title: String,
    val description: String,
    val startAt: LocalDate?,
    val completedAt: LocalDate?
){
    init {
        require(title.isNotBlank()) { "title must not be blank" }
        require(description.isNotBlank()) { "description must not be blank" }
    }
}