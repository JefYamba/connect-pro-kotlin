package dev.jefy.connectpro.portfolio.applicaion;


import org.jspecify.annotations.NullMarked;
import org.springframework.util.Assert;

import java.util.Optional;
import java.util.UUID;

import dev.jefy.connectpro.management.ManagementClient;
import dev.jefy.connectpro.management.domain.vo.AwardId;
import dev.jefy.connectpro.management.domain.vo.BadgeId;
import dev.jefy.connectpro.management.domain.vo.CategoryId;
import dev.jefy.connectpro.marketplace.applicaion.dtos.JobPostListingResponse;
import dev.jefy.connectpro.portfolio.PortfolioClient;
import dev.jefy.connectpro.portfolio.domain.model.JobPost;
import dev.jefy.connectpro.portfolio.domain.model.PService;
import dev.jefy.connectpro.portfolio.domain.repository.JobPostRepository;
import dev.jefy.connectpro.portfolio.domain.repository.PortfolioRepository;
import dev.jefy.connectpro.portfolio.domain.repository.ServiceRepository;
import dev.jefy.connectpro.portfolio.domain.vo.JobPostId;
import dev.jefy.connectpro.portfolio.domain.vo.PortfolioId;
import dev.jefy.connectpro.shared.application.dtos.PortfolioSummaryData;
import dev.jefy.connectpro.shared.application.exceptions.ResourceNotFound;
import dev.jefy.connectpro.user.domain.vo.UserId;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Jôph Yamba
 */
@Slf4j
@NullMarked
@Transactional
@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class PortfolioClientImpl implements PortfolioClient {
    private final PortfolioRepository portfolioRepo;
    private final ServiceRepository serviceRepo;
    private final JobPostRepository jobPostRepo;
    private final ManagementClient managementClient;

    @Override
    public Optional<PortfolioSummaryData> getPortfolioSummary(UserId userId) {
        log.info("Fetching portfolio summary for userId: {}", userId.value());
        Optional<PortfolioSummaryData> data = portfolioRepo.findByUserId(userId)
                .map(PortfolioSummaryData::from);
        log.info("Portfolio summary for userId: {} is present: {}", userId.value(), data.isPresent());
        return data;
    }

    @Override
    public Optional<PortfolioSummaryData> getPortfolioSummary(PortfolioId portfolioId) {
        return portfolioRepo.findById(portfolioId).map(PortfolioSummaryData::from);
    }

    @Override
    public boolean notExistsAndValidService(ServiceId serviceId) {
        PService service = serviceRepo.findById(serviceId)
                .orElseThrow(() -> new ResourceNotFound(
                        "Service with value: %s not found"
                        .formatted(serviceId.value())
                ));
        if (service.isNotActive()) {
            return true;
        }
        var portfolio = getPortfolioSummaryData(service.getPortfolioId());
        
        return !portfolio.active();
    }

    @Override
    public boolean isCategoryInUse(CategoryId categoryId) {
        Assert.notNull(categoryId, "categoryId cannot be null");
        if (serviceRepo.existsByCategoryId(categoryId)) {
            return true;
        }
        return jobPostRepo.existsByCategoryId(categoryId);
    }

    @Override
    public boolean isBadgeInUse(BadgeId badgeId) {
        Assert.notNull(badgeId, "badgeId cannot be null");
        return portfolioRepo.existsByBadgeId(badgeId);
    }
    
    @Override
    public boolean isAwardInUse(AwardId awardId) {
        Assert.notNull(awardId, "award Id cannot be null");
        return serviceRepo.existsByAwardId(awardId);
    }

    @Override
    public boolean existsAndValidJobPost(JobPostId jobPostId) {
        JobPost jobPost = jobPostRepo.findById(jobPostId)
                .orElseThrow(() -> new ResourceNotFound(
                        "Job post with value: %s not found"
                                .formatted(jobPostId.value())
                ));
        if (jobPost.isClosed()) {
            return false;
        }
        var portfolio = getPortfolioSummaryData(jobPost.getPortfolioId());

        return portfolio.active();
    }
    

    @Override
    public JobPostListingResponse getJobPostListing(JobPostId jobPostId) {
        return jobPostRepo
                .findById(jobPostId)
                .map(job -> {
                    var portfolio = getPortfolioSummaryData(job.getPortfolioId());
                    var category = managementClient.getCategory(job.getCategoryId());
                    return JobPostListingResponse.from(job, portfolio, category);
                })
                .orElseThrow(() -> new ResourceNotFound(
                        "Job Post with value: %s not found"
                                .formatted(jobPostId.value())
                ));
    }
    
    private PortfolioSummaryData getPortfolioSummaryData(PortfolioId portfolioId){
        return portfolioRepo.findById(portfolioId)
                .map(PortfolioSummaryData::from)
                .orElseThrow(() -> new ResourceNotFound(
                "Portfolio with value: %s not found"
                        .formatted(portfolioId.value())
        ));
    }

    @Override
    public Optional<CategoryId> getServiceCategoryId(UUID targetId) {
        return serviceRepo.findById(new ServiceId(targetId))
                .map(PService::getCategoryId);
    }

    @Override
    public Optional<CategoryId> getJobPostCategoryId(UUID targetId) {
        return jobPostRepo.findById(new JobPostId(targetId))
                .map(JobPost::getCategoryId);
    }
}
