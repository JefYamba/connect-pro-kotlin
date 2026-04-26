package dev.jefy.connectpro.engagement.application.dtos

import dev.jefy.connectpro.engagement.domain.Review
import dev.jefy.connectpro.user.application.dtos.UserData
import java.time.Instant
import java.util.*

/**
 * @author Jôph Yamba
 */

data class ReviewResponse(
    val reviewerId: UUID,
    val reviewerName: String,
    val reviewerAvatar: String?,
    val serviceId: UUID,
    val rating: Int,
    val comment: String,
    val createdAt: Instant
)

fun Review.toResponse(reviewer: UserData): ReviewResponse = ReviewResponse(
    serviceId = this.id.serviceId,
    reviewerId = this.id.reviewerId,
    rating = this.rating.value,
    comment = this.comment,
    createdAt = this.createdAt,
    reviewerName = reviewer.name,
    reviewerAvatar = reviewer.imageUrl
)
