package dev.jefy.connectpro.portfolio.application.query

import dev.jefy.connectpro.portfolio.application.dtos.ProjectResponse
import dev.jefy.connectpro.portfolio.domain.vo.PortfolioId
import dev.jefy.connectpro.portfolio.domain.vo.ProjectId

interface ProjectQuery {
    fun get(projectId: ProjectId): ProjectResponse
    fun getAllForPortfolio(portfolioId: PortfolioId): List<ProjectResponse>
}