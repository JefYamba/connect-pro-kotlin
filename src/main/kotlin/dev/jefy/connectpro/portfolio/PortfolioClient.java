package dev.jefy.connectpro.portfolio;

import java.util.Optional;
import java.util.UUID;

import dev.jefy.connectpro.management.domain.vo.AwardId;
import dev.jefy.connectpro.management.domain.vo.BadgeId;
import dev.jefy.connectpro.management.domain.vo.CategoryId;
import dev.jefy.connectpro.marketplace.applicaion.dtos.JobPostListingResponse;
import dev.jefy.connectpro.portfolio.domain.vo.JobPostId;
import dev.jefy.connectpro.portfolio.domain.vo.PortfolioId;
import dev.jefy.connectpro.shared.application.dtos.PortfolioSummaryData;
import dev.jefy.connectpro.user.domain.vo.UserId;

/**
 * @author Jôph Yamba
 */
public interface PortfolioClient {
    Optional<PortfolioSummaryData> getPortfolioSummary(UserId id);
    
    Optional<PortfolioSummaryData> getPortfolioSummary(PortfolioId portfolioId);

    boolean notExistsAndValidService(ServiceId serviceId);

    boolean isCategoryInUse(CategoryId categoryId);

    boolean isBadgeInUse(BadgeId badgeId);

    boolean isAwardInUse(AwardId awardId);

    boolean existsAndValidJobPost(JobPostId jobPostId);
    
    JobPostListingResponse getJobPostListing(JobPostId jobPostId);

    Optional<CategoryId> getServiceCategoryId(UUID targetId);

    Optional<CategoryId> getJobPostCategoryId(UUID targetId);

}
