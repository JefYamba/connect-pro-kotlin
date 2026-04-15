package dev.jefy.connectpro.portfolio.applicaion.dtos;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import dev.jefy.connectpro.portfolio.domain.model.Project;
import dev.jefy.connectpro.shared.domain.vo.ImageUrl;

/**
 * @author Jôph Yamba
 */
public record ProjectResponse(
        UUID id,
        UUID portfolioId,
        String title,
        String description,
        List<String>imageUrls,
        LocalDate startAt,
        LocalDate completedAt
) {
    public static ProjectResponse fromDomain(Project project) {
        return new ProjectResponse(
                project.getId().value(),
                project.getPortfolioId().value(),
                project.getTitle(),
                project.getDescription(),
                project.getImageUrls().stream().map(ImageUrl::value).toList(),
                project.getStartAt(),
                project.getCompletedAt()
        );
    }

    public static ProjectResponse from(Project project) {
        return new ProjectResponse(
                project.getId().value(),
                project.getPortfolioId().value(),
                project.getTitle(),
                project.getDescription(),
                project.getImageUrls().stream().map(ImageUrl::value).toList(),
                project.getStartAt(),
                project.getCompletedAt()
        );
    }
}

