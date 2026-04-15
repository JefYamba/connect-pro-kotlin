package dev.jefy.connectpro.portfolio.applicaion.dtos;


import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import dev.jefy.connectpro.management.appliacaion.dtos.CategoryResponse;
import dev.jefy.connectpro.portfolio.domain.model.JobPost;
import dev.jefy.connectpro.portfolio.domain.vo.Language;
import dev.jefy.connectpro.portfolio.domain.vo.Tag;
import dev.jefy.connectpro.shared.application.dtos.BudgetData;
import dev.jefy.connectpro.shared.application.dtos.PortfolioSummaryData;
import dev.jefy.connectpro.shared.domain.vo.JobType;
import dev.jefy.connectpro.shared.domain.vo.WorkMode;

/**
 * @author Jôph Yamba
 */
public record JobPostResponse (
        UUID id,
        PortfolioSummaryData portfolio,
        String title,
        String description,
        CategoryResponse category,
        List<String> tags,
        BudgetData budget,
        JobType jobType,
        WorkMode workMode,
        List<String> requiredSpokenLanguages,
        LocalDate deadline
) {
    public static JobPostResponse from(JobPost jobPost, PortfolioSummaryData portfolioData, CategoryResponse category) {
        return new JobPostResponse(
                jobPost.getId().value(),
                portfolioData,
                jobPost.getTitle(),
                jobPost.getDescription(), 
                category,
                jobPost.getTags()
                        .stream()
                        .map(Tag::value)
                        .toList(),
                BudgetData.from(jobPost.getBudget()),
                jobPost.getJobType(),
                jobPost.getWorkMode(),
                jobPost.getSpokenLanguages()
                        .stream()
                        .map(Language::value)
                        .toList(),
                jobPost.getDeadline()
        );
    }
}

