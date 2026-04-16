package dev.jefy.connectpro.engagement.domain.vo

import dev.jefy.connectpro.portfolio.domain.vo.ServiceId
import dev.jefy.connectpro.user.domain.vo.UserId
import jakarta.persistence.Embeddable
import java.util.*

@Embeddable
data class LikeId(var userId: UUID, var serviceId: UUID) {
    companion object {
        fun of(userId: UserId, serviceId: ServiceId): LikeId = LikeId(userId.value, serviceId.value)
    }
}


@Embeddable
data class ReviewId(var reviewerId: UUID, var serviceId: UUID) {
    companion object {
        fun of(reviewerId: UserId, serviceId: ServiceId): ReviewId = ReviewId(reviewerId.value, serviceId.value)
    }
}