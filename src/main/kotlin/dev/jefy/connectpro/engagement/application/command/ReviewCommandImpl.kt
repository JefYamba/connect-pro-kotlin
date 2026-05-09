package dev.jefy.connectpro.engagement.application.command

import dev.jefy.connectpro.engagement.application.dtos.ReviewRequest
import dev.jefy.connectpro.engagement.application.exception.ServiceNotExistOrValidException
import dev.jefy.connectpro.engagement.domain.Review
import dev.jefy.connectpro.engagement.domain.repository.ReviewRepository
import dev.jefy.connectpro.engagement.domain.vo.Rating
import dev.jefy.connectpro.engagement.domain.vo.ReviewId
import dev.jefy.connectpro.engagement.presentation.ReviewOperationResult
import dev.jefy.connectpro.portfolio.PortfolioClient
import dev.jefy.connectpro.portfolio.domain.vo.ServiceId
import dev.jefy.connectpro.recommandation.RecommandationClient
import dev.jefy.connectpro.recommandation.domain.vo.EventType
import dev.jefy.connectpro.recommandation.domain.vo.TargetType
import dev.jefy.connectpro.user.UserClient
import dev.jefy.connectpro.user.domain.vo.UserId
import org.jspecify.annotations.NullMarked
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * @author Jôph Yamba
 */
@NullMarked
@Service
@Transactional
class ReviewCommandImpl(
    private val portfolioClient: PortfolioClient,
    private val userClient: UserClient,
    private val reviewRepo: ReviewRepository,
    private val recommandationClient: RecommandationClient
) : ReviewCommand {
    
    override fun createOrUpdate(serviceId: ServiceId, request: ReviewRequest): ReviewOperationResult {
        if (portfolioClient.notExistsAndValidService(serviceId)) {
            throw ServiceNotExistOrValidException()
        }

        val user = userClient.getCurrentUser()
        val reviewId = ReviewId.of(UserId(user.id), serviceId)

        val existingReview = reviewRepo.findById(reviewId)
        val result = if (existingReview.isPresent) ReviewOperationResult.UPDATED else ReviewOperationResult.CREATED
        val review = existingReview
            .map {
                it.update(Rating.of(request.rating), request.comment)
                it
            }
            .orElseGet {
                Review(UserId(user.id), serviceId, Rating.of(request.rating), request.comment)
            }
        reviewRepo.save(review)
        recommandationClient.trackEvent(EventType.REVIEW, serviceId.value, TargetType.SERVICE)
        return result
    }

    override fun delete(serviceId: ServiceId) {
        if (portfolioClient.notExistsAndValidService(serviceId)) {
            throw ServiceNotExistOrValidException()
        }

        val user = userClient.getCurrentUser()
        val reviewId = ReviewId.of(UserId(user.id), serviceId)

        reviewRepo.deleteById(reviewId)
        recommandationClient.untrackEvent(EventType.REVIEW, serviceId.value, TargetType.SERVICE)
    }
}