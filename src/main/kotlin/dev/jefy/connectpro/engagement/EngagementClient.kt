package dev.jefy.connectpro.engagement

import dev.jefy.connectpro.engagement.application.dtos.ReviewResponse
import dev.jefy.connectpro.marketplace.application.dtos.ServiceReviewData
import dev.jefy.connectpro.portfolio.domain.vo.ServiceId
import dev.jefy.connectpro.user.domain.vo.UserId

interface EngagementClient {
    fun countLike(serviceId: ServiceId): Long
    fun hasLiked(serviceId: ServiceId, userId: UserId): Boolean
    fun recentReviews(serviceId: ServiceId): List<ReviewResponse>
    fun getReviewData(id: ServiceId): ServiceReviewData
}