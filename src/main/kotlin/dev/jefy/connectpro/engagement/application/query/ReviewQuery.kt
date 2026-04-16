package dev.jefy.connectpro.engagement.application.query

import dev.jefy.connectpro.engagement.application.dtos.ReviewResponse
import dev.jefy.connectpro.portfolio.domain.vo.ServiceId


interface ReviewQuery {
    fun findServiceReviewForCurrentUser(serviceId: ServiceId): ReviewResponse
    fun findByService(serviceId: ServiceId): List<ReviewResponse>
}