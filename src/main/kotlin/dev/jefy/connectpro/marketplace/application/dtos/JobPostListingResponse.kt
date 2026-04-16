package dev.jefy.connectpro.marketplace.application.dtos

import dev.jefy.connectpro.management.appliacaion.dtos.CategoryResponse
import dev.jefy.connectpro.portfolio.domain.model.JobPost
import dev.jefy.connectpro.shared.application.dtos.BudgetData
import dev.jefy.connectpro.shared.application.dtos.PortfolioSummaryData
import dev.jefy.connectpro.shared.application.dtos.toData
import dev.jefy.connectpro.shared.domain.vo.JobType
import dev.jefy.connectpro.shared.domain.vo.WorkMode
import java.time.LocalDate
import java.util.*

/**
 * @author Jôph Yamba
 */

data class JobPostListingResponse(
    val id: UUID,
    val portfolio: PortfolioSummaryData,
    val title: String,
    val category: CategoryResponse,
    val tags: List<String>,
    val budget: BudgetData?,
    val jobType: JobType?,
    val workMode: WorkMode?,
    val deadline: LocalDate?
)

fun JobPost.toListingResponse(portfolio: PortfolioSummaryData, category: CategoryResponse) = JobPostListingResponse(
    id = this.id.value,
    portfolio = portfolio,
    title = this.title,
    category = category,
    tags = this.tags.map{ it.value },
    budget = this.budget?.toData(),
    jobType = this.jobType,
    workMode = this.workMode,
    deadline = this.deadline
)
