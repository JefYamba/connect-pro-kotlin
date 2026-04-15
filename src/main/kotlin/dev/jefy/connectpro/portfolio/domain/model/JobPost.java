package dev.jefy.connectpro.portfolio.domain.model;


import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.List;

import dev.jefy.connectpro.management.domain.vo.CategoryId;
import dev.jefy.connectpro.portfolio.applicaion.dtos.JobPostRequest;
import dev.jefy.connectpro.portfolio.domain.vo.*;
import dev.jefy.connectpro.shared.domain.vo.JobType;
import dev.jefy.connectpro.shared.domain.vo.WorkMode;
import dev.jefy.connectpro.shared.infrastructure.converter.LanguageListConverter;
import dev.jefy.connectpro.shared.infrastructure.converter.TagListConverter;
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
@Table(name = "job_posts")
public class JobPost implements DAggregateRoot<JobPostId> {

    @EmbeddedId
    @AttributeOverride(name = "value", column = @Column(name = "id"))
    private JobPostId id;
    
    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "portfolio_id"))
    private PortfolioId portfolioId;
    
    private String title;
    
    private String description;
    
    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "category_id"))
    private CategoryId categoryId;
    
    @Convert(converter = TagListConverter.class)
    @Column(name = "tags")
    private List<Tag> tags;
    
    @Embedded
    private Budget budget;
    
    @Enumerated(EnumType.STRING)
    private JobType jobType;
    
    @Enumerated(EnumType.STRING)
    private WorkMode workMode;
    
    @Convert(converter = LanguageListConverter.class)
    @Column(name = "spoken_languages")
    private List<Language> spokenLanguages;
    
    private boolean isClosed;
    
    private LocalDate deadline;

    public JobPost(PortfolioId portfolioId, JobPostRequest request) {
        validate(portfolioId, request);

        this.id = JobPostId.generate();
        this.portfolioId = portfolioId;
        this.isClosed = false;
        setValues(request);
    }

    public void update(JobPostRequest request){
        validate(this.portfolioId, request);
        setValues(request);
    }

    private void setValues(JobPostRequest request) {
        this.title = request.title();
        this.description = request.description();
        this.categoryId = CategoryId.of(request.categoryId());
        this.setTags(request.tags());
        this.budget = request.budget().toDomain();
        this.jobType = request.jobType();
        this.workMode = request.workMode();
        this.setLanguages(request.spokenLanguages());
        this.deadline = request.deadline();
    }

    private void setTags(List<String> tags){
        Assert.notNull(tags, "tags must not be null");
        this.tags.clear();
        tags.stream()
            .map(Tag::new)
            .forEach(this.tags::add);
    }
    private void setLanguages(List<String> languages){
        Assert.notNull(languages, "tags must not be null");
        this.spokenLanguages.clear();
        languages.stream()
            .map(Language::new)
            .forEach(this.spokenLanguages::add);
    }
    
    private void validate(PortfolioId portfolioId, JobPostRequest request) {
        Assert.notNull(portfolioId, "id cannot be null");
        Assert.notNull(request, "request cannot be null");
        Assert.hasText(request.title(), "title cannot be empty");
        Assert.hasText(request.description(), "description cannot be empty");
        Assert.notNull(request.categoryId(), "categoryId cannot be null");
        Assert.notNull(request.jobType(), "jobType cannot be null");
        Assert.notNull(request.workMode(), "workMode cannot be null");
        Assert.notNull(request.deadline(), "deadline cannot be null");
    }

    public void open() {
        this.isClosed = false;
    }

    public void close() {
        this.isClosed = true;
    }
}

