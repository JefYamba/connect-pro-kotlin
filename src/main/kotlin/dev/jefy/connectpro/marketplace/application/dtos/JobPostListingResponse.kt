package dev.jefy.connectpro.marketplace.application.dtos

import dev.jefy.connectpro.management.appliacaion.dtos.CategoryResponse
import dev.jefy.connectpro.portfolio.domain.model.JobPost
import dev.jefy.connectpro.shared.application.dtos.PortfolioData
import dev.jefy.connectpro.shared.domain.vo.JobType
import dev.jefy.connectpro.shared.domain.vo.WorkMode
import java.util.*

/**
 * @author Jôph Yamba
 */

data class JobPostListingResponse(
    val id: UUID,
    val portfolio: PortfolioData,
    val name: String,
    val description: String,
    val category: CategoryResponse,
    val tags: List<String>,
    val jobType: JobType?,
    val workMode: WorkMode?,
)

fun JobPost.toListingResponse(portfolio: PortfolioData, category: CategoryResponse) = JobPostListingResponse(
    id = this.id.value,
    portfolio = portfolio,
    name = this.name,
    description = this.description,
    category = category,
    tags = this.tags.map{ it.name },
    jobType = this.jobType,
    workMode = this.workMode
)
