package dev.jefy.connectpro.engagement.application

import dev.jefy.connectpro.engagement.EngagementClient
import dev.jefy.connectpro.engagement.application.dtos.ReviewResponse
import dev.jefy.connectpro.engagement.application.dtos.toResponse
import dev.jefy.connectpro.engagement.domain.repository.LikeRepository
import dev.jefy.connectpro.engagement.domain.repository.ReviewRepository
import dev.jefy.connectpro.engagement.domain.vo.LikeId
import dev.jefy.connectpro.marketplace.application.dtos.ServiceReviewData
import dev.jefy.connectpro.portfolio.domain.vo.ServiceId
import dev.jefy.connectpro.user.UserClient
import dev.jefy.connectpro.user.domain.vo.UserId
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class EngagementClientImpl(
    private val likeRepo: LikeRepository,
    private val reviewRepo: ReviewRepository,
    private val userClient: UserClient
) : EngagementClient {

    override fun countLike(serviceId: ServiceId): Long {
        return likeRepo.countByIdServiceId(serviceId.value)
    }

    override fun isLiked(serviceId: ServiceId): Boolean {
        val userId = UserId(userClient.getCurrentUser().id);
        return likeRepo.existsById(LikeId.of(userId, serviceId))
    }

    override fun recentReviews(serviceId: ServiceId): List<ReviewResponse> {
        return reviewRepo.findTop10ByIdServiceIdOrderByCreatedAtDesc(serviceId.value)
            .map { it.toResponse(userClient.getById(UserId(it.id.reviewerId))) }
    }

    override fun getReviewData(id: ServiceId): ServiceReviewData {
        val avg = reviewRepo.averageRating(id.value) ?: 0.0
        val nbrReviews = reviewRepo.countByIdServiceId(id.value)
        return ServiceReviewData(avg, nbrReviews.toInt())
    }

    override fun deleteLikesAndReviewsForService(serviceId: ServiceId) {
        likeRepo.deleteAllByServiceId(serviceId.value)
        reviewRepo.deleteAllByServiceId(serviceId.value)
    }
}