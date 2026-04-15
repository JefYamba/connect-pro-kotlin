package dev.jefy.connectpro.portfolio.domain.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

import dev.jefy.connectpro.portfolio.domain.model.Project;
import dev.jefy.connectpro.portfolio.domain.vo.PortfolioId;
import dev.jefy.connectpro.portfolio.domain.vo.ProjectId;
import dev.jefy.connectpro.shared.infrastructure.ddd.AggregateRepository;

/**
 * @author Jôph Yamba
 */
@Repository
public interface ProjectRepository extends AggregateRepository<Project, ProjectId> {
    @Query("""
        select count(project) > 0 from Project project
        where project.portfolioId = :portfolioId
        and project.title = :title
    """)
    boolean isTitleConflict(@Param("id") PortfolioId portfolioId, @Param("title") String title);

    List<Project> findAllByPortfolioId(PortfolioId portfolioId);
}
