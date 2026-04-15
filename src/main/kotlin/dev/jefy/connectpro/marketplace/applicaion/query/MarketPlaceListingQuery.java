package dev.jefy.connectpro.marketplace.applicaion.query;

import org.jspecify.annotations.NullMarked;

import dev.jefy.connectpro.marketplace.applicaion.dtos.JobPostListingResponse;
import dev.jefy.connectpro.marketplace.applicaion.dtos.SearchRequest;
import dev.jefy.connectpro.marketplace.applicaion.dtos.ServiceListingResponse;
import dev.jefy.connectpro.shared.application.dtos.PageResponse;

/**
 * @author Jôph E.F. Yamba
 * @date 08/04/2026
 * @time 11:48:26
 */
/**
 * @author Jôph Yamba
 */
@NullMarked
public interface MarketPlaceListingQuery {
    
    PageResponse<ServiceListingResponse> filterService(SearchRequest request);
    PageResponse<JobPostListingResponse> filterJobPost(SearchRequest request);

    PageResponse<ServiceListingResponse> getRecommendedServices(int page, int size);
    PageResponse<JobPostListingResponse> getRecommendedJobPosts(int page, int size);
}
