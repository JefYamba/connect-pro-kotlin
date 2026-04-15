package dev.jefy.connectpro.portfolio.domain.model;


import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import dev.jefy.connectpro.portfolio.applicaion.dtos.ProjectRequest;
import dev.jefy.connectpro.portfolio.domain.vo.PortfolioId;
import dev.jefy.connectpro.portfolio.domain.vo.ProjectId;
import dev.jefy.connectpro.shared.domain.vo.ImageUrl;
import dev.jefy.connectpro.shared.infrastructure.converter.ImagesUrlListConverter;
import dev.jefy.connectpro.shared.infrastructure.ddd.DAggregateRoot;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author Jôph Yamba
 */

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "portfolio_projects")
public class Project implements DAggregateRoot<ProjectId> {
    @EmbeddedId
    @AttributeOverride(name = "value", column = @Column(name = "id"))
    private ProjectId id;
    
    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "portfolio_id"))
    private PortfolioId portfolioId;
    
    private String title;
    
    private String description;
    
    @Convert(converter = ImagesUrlListConverter.class)
    @Column(name = "images")
    private final List<ImageUrl> imageUrls = new ArrayList<>();
    
    private LocalDate startAt;
    
    private LocalDate completedAt;

    public Project(PortfolioId portfolioId, ProjectRequest request) {
        Assert.notNull(portfolioId, "id must not be null");
        Assert.hasText(request.title(), "title must not be empty");
        this.id = ProjectId.generate();
        this.portfolioId = PortfolioId.of(request.portfolioId());
        this.title = request.title();
        this.description = request.description();
        this.startAt = request.startAt();
        this.completedAt = request.completedAt();
    }
    
    public void update(ProjectRequest request){
        Assert.hasText(title, "title must not be empty");
        this.title = request.title();
        this.description = request.description();
        this.startAt = request.startAt();
        this.completedAt = request.completedAt();
    }
    
    public void addImage(ImageUrl imageUrl){
        Assert.notNull(imageUrl, "value must not be null");
        if (imageUrls.size() >= 4) {
            throw new IllegalStateException("A project must not have more than 4 images");
        }
        this.imageUrls.add(imageUrl);
    }
    public void removeImage(ImageUrl imageUrl){
        Assert.notNull(imageUrl, "value must not be null");
        this.imageUrls.remove(imageUrl);
    }
}