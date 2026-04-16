package dev.jefy.connectpro.portfolio.application.dtos

import dev.jefy.connectpro.shared.application.dtos.BudgetData
import dev.jefy.connectpro.shared.domain.vo.JobType
import dev.jefy.connectpro.shared.domain.vo.WorkMode
import java.time.LocalDate
import java.util.*

data class JobPostRequest(
    val portfolioId: UUID,
    val title: String,
    val description: String,
    val categoryId: UUID,
    val tags: List<String>,
    val budget: BudgetData?,
    val jobType: JobType?,
    val workMode: WorkMode?,
    val spokenLanguages: List<String>,
    val deadline: LocalDate?
){
    init {
        require(title.isNotBlank()) { "title must not be blank" }
        require(description.isNotBlank()) { "description must not be blank" }
    }
}