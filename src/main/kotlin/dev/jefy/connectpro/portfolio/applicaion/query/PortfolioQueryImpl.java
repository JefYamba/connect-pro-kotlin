package dev.jefy.connectpro.portfolio.applicaion.query;


import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.function.Function;

import dev.jefy.connectpro.engagement.EngagementClient;
import dev.jefy.connectpro.management.ManagementClient;
import dev.jefy.connectpro.management.appliacaion.dtos.BadgeResponse;
import dev.jefy.connectpro.management.domain.repositoty.BadgeRepository;
import dev.jefy.connectpro.marketplace.applicaion.dtos.JobPostListingResponse;
import dev.jefy.connectpro.marketplace.applicaion.dtos.ServiceListingResponse;
import dev.jefy.connectpro.portfolio.applicaion.dtos.JobPostResponse;
import dev.jefy.connectpro.portfolio.applicaion.dtos.PortfolioResponse;
import dev.jefy.connectpro.portfolio.applicaion.dtos.ProjectResponse;
import dev.jefy.connectpro.portfolio.applicaion.dtos.ServiceResponse;
import dev.jefy.connectpro.portfolio.domain.model.JobPost;
import dev.jefy.connectpro.portfolio.domain.model.PService;
import dev.jefy.connectpro.portfolio.domain.model.Portfolio;
import dev.jefy.connectpro.portfolio.domain.repository.JobPostRepository;
import dev.jefy.connectpro.portfolio.domain.repository.PortfolioRepository;
import dev.jefy.connectpro.portfolio.domain.repository.ProjectRepository;
import dev.jefy.connectpro.portfolio.domain.repository.ServiceRepository;
import dev.jefy.connectpro.portfolio.domain.vo.JobPostId;
import dev.jefy.connectpro.portfolio.domain.vo.PortfolioId;
import dev.jefy.connectpro.shared.application.dtos.PortfolioSummaryData;
import dev.jefy.connectpro.shared.application.exceptions.ResourceNotFound;
import lombok.RequiredArgsConstructor;

/**
 * @author Jôph Yamba
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PortfolioQueryImpl implements PortfolioQuery {
    private final PortfolioRepository portfolioRepo;
    private final JobPostRepository jobPostRepo;
    private final ServiceRepository serviceRepo;
    private final BadgeRepository badgeRepo;
    private final ProjectRepository projectRepo;
    private final EngagementClient engagementClient;
    private final ManagementClient managementClient;
    
    @Override
    public PortfolioResponse get(PortfolioId portfolioId) {
        Assert.notNull(portfolioId, "id cannot be null");
        var portfolio = getPortfolio(portfolioId);
        
        BadgeResponse badge = portfolio.getBadgeId() == null 
                ? null 
                : badgeRepo.findById(portfolio.getBadgeId())
                .map(BadgeResponse::from)
                .orElse(null);
        
        List<ServiceListingResponse> services = serviceRepo.findAllByPortfolioId(portfolio.getId())
                .stream()
                .map(mapToServiceListingResponse(portfolio))
                .toList();
        
        List<JobPostListingResponse> jobPosts = jobPostRepo.findAllByPortfolioId(portfolio.getId())
                .stream()
                .map(mapToJobPostListingResponse(portfolio))
                .toList();
        
        List<ProjectResponse> projects = projectRepo.findAllByPortfolioId(portfolio.getId())
                .stream()
                .map(ProjectResponse::from)
                .toList();
        
        
        return PortfolioResponse.from(portfolio, badge, services, jobPosts, projects);
    }



    @Override
    public JobPostResponse getJopPost(JobPostId jobPostId) {
        return jobPostRepo.findById(jobPostId)
                .map(mapToJobPostResponse())
                .orElseThrow(()-> new ResourceNotFound("job post with id: " + jobPostId + " not found"));
    }

    @Override
    public ServiceResponse getService(ServiceId serviceId) {
        return serviceRepo.findById(serviceId)
                .map(mapToServiceResponse())
                .orElseThrow(()-> new ResourceNotFound("Service with id " + serviceId + " not found"));
        
    }

    

    private Function<PService, ServiceResponse> mapToServiceResponse() {
        return sevice -> {
            Portfolio portfolio = getPortfolio(sevice.getPortfolioId());
            var category = managementClient.getCategory(sevice.getCategoryId());
            var award = sevice.getAwardId() != null
                    ? managementClient.getAward(sevice.getAwardId()).orElse(null)
                    : null;
            var reviewData = engagementClient.getReviewData(sevice.getId());
            var recentReviews = engagementClient.recentReviews(sevice.getId());
            PortfolioSummaryData portfolioData = PortfolioSummaryData.from(portfolio);
            return ServiceResponse.from(sevice, portfolioData, category, award, reviewData, recentReviews);
        };
    }
    
    private Function<JobPost, JobPostResponse> mapToJobPostResponse() {
        return jobPost -> {
            Portfolio portfolio = getPortfolio(jobPost.getPortfolioId());
            var category = managementClient.getCategory(jobPost.getCategoryId());
            PortfolioSummaryData portfolioData = PortfolioSummaryData.from(portfolio);
            return JobPostResponse.from(jobPost, portfolioData, category);
        };
    }

    private Function<PService, ServiceListingResponse> mapToServiceListingResponse(Portfolio portfolio) {
        return sevice -> {
            var category = managementClient.getCategory(sevice.getCategoryId());
            var award = sevice.getAwardId() != null
                    ? managementClient.getAward(sevice.getAwardId()).orElse(null)
                    : null;
            var reviewData = engagementClient.getReviewData(sevice.getId());
            PortfolioSummaryData portfolioData = PortfolioSummaryData.from(portfolio);
            return ServiceListingResponse.from(sevice, portfolioData, category, award, reviewData);
        };
    }
    private Function<JobPost, JobPostListingResponse> mapToJobPostListingResponse(Portfolio portfolio) {
        return jobPost -> {
            var category = managementClient.getCategory(jobPost.getCategoryId());
            PortfolioSummaryData portfolioData = PortfolioSummaryData.from(portfolio);
            return JobPostListingResponse.from(jobPost, portfolioData, category);
        };
    }

    private @NonNull Portfolio getPortfolio(PortfolioId portfolioId) {
        return portfolioRepo.findById(portfolioId)
                .orElseThrow(() -> new ResourceNotFound("Portfolio with id " + portfolioId + " not found"));
    }
}
