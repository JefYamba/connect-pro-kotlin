package dev.jefy.connectpro.portfolio.application.dtos

import java.util.*

data class ProjectRequest(
    val portfolioId: UUID,
    val name: String,
    val description: String,
){
    init {
        require(name.isNotBlank()) { "title must not be blank" }
        require(description.isNotBlank()) { "title must not be blank" }
    }
}