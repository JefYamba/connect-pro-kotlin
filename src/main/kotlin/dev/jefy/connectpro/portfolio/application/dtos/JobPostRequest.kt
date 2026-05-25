package dev.jefy.connectpro.portfolio.application.dtos

import dev.jefy.connectpro.shared.domain.vo.JobType
import dev.jefy.connectpro.shared.domain.vo.WorkMode
import java.util.*

data class JobPostRequest(
    val portfolioId: UUID,
    val name: String,
    val description: String,
    val categoryId: UUID,
    val tags: Set<String>,
    val jobType: JobType?,
    val workMode: WorkMode?,
){
    init {
        require(name.isNotBlank()) { "title must not be blank" }
        require(description.isNotBlank()) { "description must not be blank" }
    }
}