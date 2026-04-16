package dev.jefy.connectpro.marketplace.presentation;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import dev.jefy.connectpro.marketplace.applicaion.dtos.JobPostListingResponse;
import dev.jefy.connectpro.marketplace.applicaion.dtos.SearchRequest;
import dev.jefy.connectpro.marketplace.applicaion.dtos.ServiceListingResponse;
import dev.jefy.connectpro.marketplace.applicaion.query.MarketPlaceListingQuery;
import dev.jefy.connectpro.shared.application.dtos.AppResponse;
import dev.jefy.connectpro.shared.application.dtos.PageResponse;
import dev.jefy.connectpro.shared.infrastructure.AppResponseBuilder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

/**
 * @author Jôph Yamba
 */
@RestController
@RequestMapping("/market")
@Tag(name = "Market Place Api")
@RequiredArgsConstructor
public class MarketPlaceController {

    private final MarketPlaceListingQuery query;


    @PostMapping("/service")
    @Operation(summary = "Search service")
    public ResponseEntity<AppResponse<PageResponse<ServiceListingResponse>>> searchService(
            @RequestBody @NotNull @Valid SearchRequest request
    ) {
        return AppResponseBuilder.<PageResponse<ServiceListingResponse>>builder()
                .message("data retrieved successfully")
                .data(query.filterService(request))
                .build();
    }

    @PostMapping("/job-post")
    @Operation(summary = "Search job post")
    public ResponseEntity<AppResponse<PageResponse<JobPostListingResponse>>> searchJobPost(
            @RequestBody @NotNull @Valid SearchRequest request
    ) {
        return AppResponseBuilder.<PageResponse<JobPostListingResponse>>builder()
                .message("fata retrieved successfully")
                .data(query.filterJobPost(request))
                .build();
    }

    @GetMapping("/recommended/service")
    @Operation(summary = "Get recommended services")
    public ResponseEntity<AppResponse<PageResponse<ServiceListingResponse>>> getRecommendedServices(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return AppResponseBuilder.<PageResponse<ServiceListingResponse>>builder()
                .message("recommended services retrieved successfully")
                .data(query.getRecommendedServices(page, size))
                .build();
    }

    @GetMapping("/recommended/job-post")
    @Operation(summary = "Get recommended job posts")
    public ResponseEntity<AppResponse<PageResponse<JobPostListingResponse>>> getRecommendedJobPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return AppResponseBuilder.<PageResponse<JobPostListingResponse>>builder()
                .message("recommended job posts retrieved successfully")
                .data(query.getRecommendedJobPosts(page, size))
                .build();
    }
}
