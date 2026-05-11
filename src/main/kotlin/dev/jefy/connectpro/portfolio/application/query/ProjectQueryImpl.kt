package dev.jefy.connectpro.portfolio.application.query

import dev.jefy.connectpro.portfolio.application.dtos.ProjectResponse
import dev.jefy.connectpro.portfolio.application.exceptions.ProjectNotFoundException
import dev.jefy.connectpro.portfolio.domain.repository.ProjectRepository
import dev.jefy.connectpro.portfolio.domain.vo.PortfolioId
import dev.jefy.connectpro.portfolio.domain.vo.ProjectId
import dev.jefy.connectpro.shared.infrastructure.file_storage.ImageUrlResolver
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class ProjectQueryImpl(
    private val projectRepo: ProjectRepository,
    private val resolver: ImageUrlResolver
) : ProjectQuery {

    override fun get(projectId: ProjectId): ProjectResponse = projectRepo.findById(projectId)
            .map { ProjectResponse.fromDomain(it, resolver) }
            .orElseThrow { ProjectNotFoundException() }

    override fun getAllForPortfolio(portfolioId: PortfolioId): List<ProjectResponse> = projectRepo
        .findAllByPortfolioId(portfolioId)
        .map { ProjectResponse.fromDomain(it, resolver) }
    
}