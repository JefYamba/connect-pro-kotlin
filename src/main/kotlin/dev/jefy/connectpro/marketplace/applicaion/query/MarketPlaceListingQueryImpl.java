package dev.jefy.connectpro.marketplace.applicaion.query;


import org.jspecify.annotations.NullMarked;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Function;

import dev.jefy.connectpro.engagement.EngagementClient;
import dev.jefy.connectpro.management.ManagementClient;
import dev.jefy.connectpro.management.domain.vo.CategoryId;
import dev.jefy.connectpro.marketplace.applicaion.dtos.JobPostListingResponse;
import dev.jefy.connectpro.marketplace.applicaion.dtos.SearchRequest;
import dev.jefy.connectpro.marketplace.applicaion.dtos.ServiceListingResponse;
import dev.jefy.connectpro.portfolio.PortfolioClient;
import dev.jefy.connectpro.portfolio.domain.model.JobPost;
import dev.jefy.connectpro.portfolio.domain.model.PService;
import dev.jefy.connectpro.portfolio.domain.repository.JobPostRepository;
import dev.jefy.connectpro.portfolio.domain.repository.ServiceRepository;
import dev.jefy.connectpro.portfolio.domain.vo.PortfolioId;
import dev.jefy.connectpro.recommandation.RecommandationClient;
import dev.jefy.connectpro.shared.application.dtos.PageResponse;
import dev.jefy.connectpro.shared.application.dtos.PortfolioSummaryData;
import lombok.RequiredArgsConstructor;

/**
 * @author Jôph E.F. Yamba
 * @date 08/04/2026
 * @time 11:50:08
 */
/**
 * @author Jôph Yamba
 */
@NullMarked
@Service                                
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MarketPlaceListingQueryImpl implements MarketPlaceListingQuery {
    private final ServiceRepository serviceRepo;
    private final JobPostRepository jobPostRepo;
    private final EngagementClient engagementClient;
    private final ManagementClient managementClient;
    private final PortfolioClient portfolioClient;
    private final RecommandationClient recommandationClient;
    
    @Override
    public PageResponse<ServiceListingResponse> filterService(SearchRequest request) {
        var page = PageRequest.of(request.page(), request.size());
        var search = request.search() == null ? "" : request.search();
        
        var categoryId = request.categoryId() == null ? null : CategoryId.of(request.categoryId());
        
        var servicePage = serviceRepo.filter(search, categoryId, page)
                .map(mapToServiceListingResponse());
        return new PageResponse<>(servicePage);
    }

    @Override
    public PageResponse<JobPostListingResponse> filterJobPost(SearchRequest request) {
        var page = PageRequest.of(request.page(), request.size());
        var search = request.search() == null ? "" : request.search();
        var categoryId = request.categoryId() == null ? null : CategoryId.of(request.categoryId());
        
        var jobPostPage =  jobPostRepo.filter(search, categoryId, page)
                .map(mapToJobPostListingResponse());
        return new PageResponse<>(jobPostPage);
    }

    @Override
    public PageResponse<ServiceListingResponse> getRecommendedServices(int page, int size) {
        List<CategoryId> categories = recommandationClient.getRecommendedCategories();
        if (categories.isEmpty()) {
            return filterService(new SearchRequest(null, page, size, null));
        }
        
        var pageable = PageRequest.of(page, size);
        // Simplification: use the first (most relevant) category for now
        // A better approach would be to find across all categories
        Page<PService> servicePage = serviceRepo.filter("", categories.get(0), pageable);
        return new PageResponse<>(servicePage.map(mapToServiceListingResponse()));
    }

    @Override
    public PageResponse<JobPostListingResponse> getRecommendedJobPosts(int page, int size) {
        List<CategoryId> categories = recommandationClient.getRecommendedCategories();
        if (categories.isEmpty()) {
            return filterJobPost(new SearchRequest(null, page, size, null));
        }

        var pageable = PageRequest.of(page, size);
        Page<JobPost> jobPostPage = jobPostRepo.filter("", categories.get(0), pageable);
        return new PageResponse<>(jobPostPage.map(mapToJobPostListingResponse()));
    }

    private Function<PService, ServiceListingResponse> mapToServiceListingResponse() {
        return sevice -> {
            var category = managementClient.getCategory(sevice.getCategoryId());
            var award = managementClient.getAward(sevice.getAwardId()).orElse(null);
            var reviewData = engagementClient.getReviewData(sevice.getId());
            PortfolioSummaryData portfolioData = getPortfolioData(sevice.getPortfolioId());
            return ServiceListingResponse.from(sevice, portfolioData, category, award, reviewData);
        };
    }
    private Function<JobPost, JobPostListingResponse> mapToJobPostListingResponse() {
        return jobPost -> {
            var category = managementClient.getCategory(jobPost.getCategoryId());
            PortfolioSummaryData portfolioData = getPortfolioData(jobPost.getPortfolioId());
            return JobPostListingResponse.from(jobPost, portfolioData, category);
        };
    }
    
    private PortfolioSummaryData getPortfolioData(PortfolioId portfolioId){
        return portfolioClient.getPortfolioSummary(portfolioId)
                .orElseThrow(()-> new IllegalStateException("Portfolio not found"));
    }
}
