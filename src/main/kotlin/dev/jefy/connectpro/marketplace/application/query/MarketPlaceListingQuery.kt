package dev.jefy.connectpro.marketplace.application.query

import dev.jefy.connectpro.marketplace.application.dtos.JobPostListingResponse
import dev.jefy.connectpro.marketplace.application.dtos.SearchRequest
import dev.jefy.connectpro.marketplace.application.dtos.ServiceListingResponse
import dev.jefy.connectpro.shared.application.dtos.PageResponse
import org.jspecify.annotations.NullMarked

/**
 * @author Jôph Yamba
 */
@NullMarked
interface MarketPlaceListingQuery {
    fun filterService(request: SearchRequest): PageResponse<ServiceListingResponse>
    fun filterJobPost(request: SearchRequest): PageResponse<JobPostListingResponse>

    fun getRecommendedServices(page: Int, size: Int): PageResponse<ServiceListingResponse>
    fun getRecommendedJobPosts(page: Int, size: Int): PageResponse<JobPostListingResponse>
}
