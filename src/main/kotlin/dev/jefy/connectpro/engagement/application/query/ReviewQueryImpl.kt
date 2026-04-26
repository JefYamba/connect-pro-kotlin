package dev.jefy.connectpro.engagement.application.query

import dev.jefy.connectpro.engagement.application.dtos.ReviewResponse
import dev.jefy.connectpro.engagement.application.dtos.toResponse
import dev.jefy.connectpro.engagement.application.exception.ReviewNotFountException
import dev.jefy.connectpro.engagement.domain.repository.ReviewRepository
import dev.jefy.connectpro.engagement.domain.vo.ReviewId
import dev.jefy.connectpro.portfolio.domain.vo.ServiceId
import dev.jefy.connectpro.user.UserClient
import dev.jefy.connectpro.user.application.dtos.UserData
import dev.jefy.connectpro.user.domain.vo.UserId
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class ReviewQueryImpl(
    private val userClient: UserClient,
    private val reviewRepo: ReviewRepository
) : ReviewQuery {

    override fun findServiceReviewForCurrentUser(serviceId: ServiceId): ReviewResponse {
        val user: UserData = userClient.getCurrentUser()
        return reviewRepo.findById(ReviewId.of(UserId.of(user.id), serviceId))
            .map { it.toResponse(userClient.getData(UserId(it.id.reviewerId))) }
            .orElseThrow { ReviewNotFountException()}
    }

    override fun findByService(serviceId: ServiceId): List<ReviewResponse> {
        return reviewRepo.findByIdServiceId(serviceId.value)
            .map { it.toResponse(userClient.getData(UserId(it.id.reviewerId))) }
    }
}