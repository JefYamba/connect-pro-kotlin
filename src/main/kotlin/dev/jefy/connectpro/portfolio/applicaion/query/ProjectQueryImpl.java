package dev.jefy.connectpro.portfolio.applicaion.query;


import org.jspecify.annotations.NullMarked;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

import dev.jefy.connectpro.portfolio.applicaion.dtos.ProjectResponse;
import dev.jefy.connectpro.portfolio.domain.repository.ProjectRepository;
import dev.jefy.connectpro.portfolio.domain.vo.PortfolioId;
import dev.jefy.connectpro.shared.application.exceptions.ResourceNotFound;
import lombok.RequiredArgsConstructor;

/**
 * @author Jôph Yamba
 */
@NullMarked
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProjectQueryImpl implements ProjectQuery {
    private final ProjectRepository projectRepo;
    @Override
    public ProjectResponse get(ProjectId projectId) {
        Assert.notNull(projectId, "projectId cannot be null");
        return projectRepo
                .findById(projectId)
                .map(ProjectResponse::fromDomain)
                .orElseThrow(()-> new ResourceNotFound("Project not found"));
    }

    @Override
    public List<ProjectResponse> getAllForPortfolio(PortfolioId portfolioId) {
        Assert.notNull(portfolioId, "id cannot be null");
        return projectRepo
                .findAllByPortfolioId(portfolioId)
                .stream()
                .map(ProjectResponse::fromDomain)
                .toList();
    }
}
