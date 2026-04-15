package dev.jefy.connectpro.marketplace.applicaion.dtos;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import dev.jefy.connectpro.management.appliacaion.dtos.CategoryResponse;
import dev.jefy.connectpro.portfolio.domain.model.JobPost;
import dev.jefy.connectpro.portfolio.domain.vo.Tag;
import dev.jefy.connectpro.shared.application.dtos.BudgetData;
import dev.jefy.connectpro.shared.application.dtos.PortfolioSummaryData;
import dev.jefy.connectpro.shared.domain.vo.JobType;
import dev.jefy.connectpro.shared.domain.vo.WorkMode;

/**
 * @author Jôph Yamba
 */
public record JobPostListingResponse (
        UUID id,
        PortfolioSummaryData portfolio,
        String title,
        CategoryResponse category,
        List<String> tags,
        BudgetData budget,
        JobType jobType,
        WorkMode workMode,
        LocalDate deadline
) {
    
    public static JobPostListingResponse from(JobPost jobPost, PortfolioSummaryData portfolioData, CategoryResponse category) {
        return new JobPostListingResponse(
                jobPost.getId().value(),
                portfolioData,
                jobPost.getTitle(),
                category,
                jobPost.getTags()
                        .stream()
                        .map(Tag::value)
                        .toList(),
                BudgetData.from(jobPost.getBudget()),
                jobPost.getJobType(),
                jobPost.getWorkMode(),
                jobPost.getDeadline()
        );
    }
}
