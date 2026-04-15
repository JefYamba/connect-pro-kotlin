package dev.jefy.connectpro.portfolio.applicaion.query;

import org.jspecify.annotations.NullMarked;

import java.util.List;

import dev.jefy.connectpro.portfolio.applicaion.dtos.ProjectResponse;
import dev.jefy.connectpro.portfolio.domain.vo.PortfolioId;

/**
 * @author Jôph Yamba
 */
@NullMarked
public interface ProjectQuery {
    ProjectResponse get(ProjectId projectId);
    List<ProjectResponse> getAllForPortfolio(PortfolioId portfolioId);
}
