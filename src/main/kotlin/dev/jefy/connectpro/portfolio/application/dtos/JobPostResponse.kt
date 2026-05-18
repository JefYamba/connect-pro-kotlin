package dev.jefy.connectpro.portfolio.application.dtos

import dev.jefy.connectpro.management.appliacaion.dtos.CategoryResponse
import dev.jefy.connectpro.portfolio.domain.model.JobPost
import dev.jefy.connectpro.shared.application.dtos.BudgetData
import dev.jefy.connectpro.shared.application.dtos.PortfolioData
import dev.jefy.connectpro.shared.application.dtos.toData
import dev.jefy.connectpro.shared.domain.vo.JobType
import dev.jefy.connectpro.shared.domain.vo.WorkMode
import java.time.LocalDate
import java.util.*


/**
 * @author Jôph Yamba
 */
data class JobPostResponse(
    val id: UUID,
    val portfolio: PortfolioData,
    val title: String,
    val description: String,
    val category: CategoryResponse,
    val tags: List<String>,
    val budget: BudgetData?,
    val jobType: JobType?,
    val workMode: WorkMode?,
    val spokenLanguages: List<String>,
    val deadline: LocalDate?
)

fun JobPost.toResponse(portfolioData: PortfolioData, category: CategoryResponse): JobPostResponse = JobPostResponse(
    id = this.id.value,
    portfolio = portfolioData,
    title = this.title,
    description = this.description,
    category = category,
    tags = this.tags.map{ it.name }.toList(),
    budget = this.budget?.toData(),
    jobType = this.jobType,
    workMode = this.workMode,
    spokenLanguages = this.spokenLanguages.map{ it.value }.toList(),
    deadline = this.deadline
)
