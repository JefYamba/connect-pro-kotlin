package dev.jefy.connectpro.engagement.application.dtos

import dev.jefy.connectpro.engagement.domain.Review
import java.time.Instant
import java.util.*

/**
 * @author Jôph Yamba
 */

data class ReviewResponse(
    val reviewerId: UUID,
    val serviceId: UUID,
    val rating: Int,
    val comment: String,
    val createdAt: Instant
)

fun Review.toResponse(): ReviewResponse = ReviewResponse(
    serviceId = this.id.serviceId,
    reviewerId = this.id.reviewerId,
    rating = this.rating.value,
    comment = this.comment,
    createdAt = this.createdAt
)
