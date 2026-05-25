package dev.jefy.connectpro.portfolio.domain.repository

import dev.jefy.connectpro.portfolio.domain.model.Project
import dev.jefy.connectpro.portfolio.domain.vo.PortfolioId
import dev.jefy.connectpro.portfolio.domain.vo.ProjectId
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface ProjectRepository : JpaRepository<Project, ProjectId> {

    @Query(
        """
        select count(project) > 0 from Project project
        where project.portfolioId = :portfolioId
        and project.name = :title
        """
    )
    fun isTitleConflict(
        @Param("portfolioId") portfolioId: PortfolioId,
        @Param("title") title: String
    ): Boolean

    fun findAllByPortfolioId(portfolioId: PortfolioId): List<Project>
}