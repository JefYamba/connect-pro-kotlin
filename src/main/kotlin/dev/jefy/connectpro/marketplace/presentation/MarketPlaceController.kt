package dev.jefy.connectpro.marketplace.presentation

import dev.jefy.connectpro.marketplace.application.dtos.JobPostListingResponse
import dev.jefy.connectpro.marketplace.application.dtos.SearchRequest
import dev.jefy.connectpro.marketplace.application.dtos.ServiceListingResponse
import dev.jefy.connectpro.marketplace.application.query.MarketPlaceListingQuery
import dev.jefy.connectpro.shared.application.dtos.AppResponse
import dev.jefy.connectpro.shared.application.dtos.PageResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import jakarta.validation.constraints.NotNull
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.Instant

/**
 * @author Jôph Yamba
 */
@RestController
@RequestMapping("/market")
@Tag(name = "Market Place Api")
class MarketPlaceController(
    private val query: MarketPlaceListingQuery
) {

    @PostMapping("/service")
    @Operation(summary = "Search service")
    fun searchService(
        @RequestBody @NotNull @Valid request: SearchRequest
    ): ResponseEntity<AppResponse<PageResponse<ServiceListingResponse>>> = ResponseEntity.ok(
        AppResponse(
            message = "data retrieved successfully",
            data = query.filterService(request),
            status = HttpStatus.OK.value(),
            timestamp = Instant.now()
        )
    )

    @PostMapping("/job-post")
    @Operation(summary = "Search job post")
    fun searchJobPost(
        @RequestBody @NotNull @Valid request: SearchRequest
    ): ResponseEntity<AppResponse<PageResponse<JobPostListingResponse>>> = ResponseEntity.ok(
        AppResponse(
            message = "data retrieved successfully",
            data = query.filterJobPost(request),
            status = HttpStatus.OK.value(),
            timestamp = Instant.now()
        )
    )

    @GetMapping("/recommended/service")
    @Operation(summary = "Get recommended services")
    fun getRecommendedServices(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): ResponseEntity<AppResponse<PageResponse<ServiceListingResponse>>> = ResponseEntity.ok(
        AppResponse(
            message = "recommended services retrieved successfully",
            data = query.getRecommendedServices(page, size),
            status = HttpStatus.OK.value(),
            timestamp = Instant.now()
        )
    )

    @GetMapping("/recommended/job-post")
    @Operation(summary = "Get recommended job posts")
    fun getRecommendedJobPosts(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): ResponseEntity<AppResponse<PageResponse<JobPostListingResponse>>> = ResponseEntity.ok(
        AppResponse(
            message = "recommended job posts retrieved successfully",
            data = query.getRecommendedJobPosts(page, size),
            status = HttpStatus.OK.value(),
            timestamp = Instant.now()
        )
    )
}