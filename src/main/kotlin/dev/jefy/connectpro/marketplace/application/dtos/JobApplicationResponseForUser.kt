package dev.jefy.connectpro.marketplace.application.dtos

import dev.jefy.connectpro.marketplace.application.dtos.JobPostListingResponse
import dev.jefy.connectpro.marketplace.domain.model.JobApplication
import dev.jefy.connectpro.shared.domain.vo.JobApplicationStatus
import java.time.Instant


/**
 * @author Jôph Yamba
 */

data class JobApplicationResponseForUser(
    val jobPost: JobPostListingResponse,
    val motivation: String,
    val appliedAt: Instant,
    val status: JobApplicationStatus
)
fun JobApplication.toUserResponse(jobPostListing: JobPostListingResponse) = JobApplicationResponseForUser(
    jobPost = jobPostListing,
    motivation = this.motivation,
    appliedAt = this.appliedAt,
    status = this.status
)
