package dev.jefy.connectpro.portfolio.applicaion.command;


import org.jspecify.annotations.NullMarked;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import dev.jefy.connectpro.portfolio.applicaion.dtos.ProjectRequest;
import dev.jefy.connectpro.portfolio.domain.model.Project;
import dev.jefy.connectpro.portfolio.domain.repository.PortfolioRepository;
import dev.jefy.connectpro.portfolio.domain.repository.ProjectRepository;
import dev.jefy.connectpro.portfolio.domain.vo.PortfolioId;
import dev.jefy.connectpro.portfolio.domain.vo.ProjectId;
import dev.jefy.connectpro.shared.application.exceptions.ResourceNotFound;
import dev.jefy.connectpro.shared.domain.vo.ImageUrl;
import dev.jefy.connectpro.shared.infrastructure.file_storage.ImageService;
import lombok.RequiredArgsConstructor;

/**
 * @author Jôph Yamba
 */
@NullMarked
@Service
@Transactional
@RequiredArgsConstructor
public class ProjectCommandImpl implements ProjectCommand {
    private final PortfolioRepository portfolioRepo;
    private final ProjectRepository projectRepo;
    private final ImageService imageService;

    @Override
    public ProjectId create(ProjectRequest request) {
        PortfolioId portfolioId = PortfolioId.of(request.portfolioId());
        if (!portfolioRepo.existsById(portfolioId)) {
            throw  new ResourceNotFound("Portfolio with id " + portfolioId + " not found");
        }
        boolean isConflict = projectRepo.isTitleConflict(portfolioId, request.title());
        if (isConflict) {
            throw new IllegalStateException("Project with title already exists");
        }
        Project project = new Project(portfolioId, request);
        projectRepo.save(project);
        return project.getId();
    }

    @Override
    public ProjectId update(ProjectId projectId, ProjectRequest request) {
        Project project = getProject(projectId);
        boolean isConflict = projectRepo.isTitleConflict(project.getPortfolioId(), request.title());
        if (isConflict) {
            throw new IllegalStateException("project with title already exists");
        }
        project.update(request);
        projectRepo.save(project);
        return project.getId();
    }

    @Override
    public ProjectId addImage(ProjectId projectId, MultipartFile image) throws IOException {
        Assert.notNull(image, "Image must not be null");
        Project project = getProject(projectId);

        ImageUrl imageUrl = imageService.save(image);
        project.addImage(imageUrl);
        projectRepo.save(project);
        return project.getId();
    }

    @Override
    public ProjectId removeImage(ProjectId projectId, ImageUrl imageUrl) throws IOException {
        Assert.notNull(imageUrl, "Image must not be null");
        Project project = getProject(projectId);
        imageService.delete(imageUrl);
        project.removeImage(imageUrl);
        projectRepo.save(project);
        return project.getId();
    }

    private Project getProject(ProjectId projectId) {
        Assert.notNull(projectId, "Project id cannot be null");
        return projectRepo
                .findById(projectId)
                .orElseThrow(() -> new ResourceNotFound("Project not found"));
    }
}
