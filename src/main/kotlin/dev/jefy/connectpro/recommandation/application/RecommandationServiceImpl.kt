package dev.jefy.connectpro.recommandation.application

import dev.jefy.connectpro.management.domain.Category
import dev.jefy.connectpro.management.domain.repository.CategoryRepository
import dev.jefy.connectpro.management.domain.vo.CategoryId
import dev.jefy.connectpro.portfolio.PortfolioClient
import dev.jefy.connectpro.recommandation.domain.EventTracking
import dev.jefy.connectpro.recommandation.domain.repository.EventTrackingRepository
import dev.jefy.connectpro.recommandation.domain.vo.EventType
import dev.jefy.connectpro.recommandation.domain.vo.TargetType
import dev.jefy.connectpro.user.UserClient
import dev.jefy.connectpro.user.application.dtos.UserData
import dev.jefy.connectpro.user.domain.vo.UserId
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class RecommandationServiceImpl(
    private val eventTrackingRepo: EventTrackingRepository,
    private val userClient: UserClient,
    private val portfolioClient: PortfolioClient,
    private val categoryRepository: CategoryRepository
) : RecommandationService {

    override fun getRecommendedCategories(): List<CategoryId> {
        val currentUser: UserData = userClient.getCurrentUser()
        val events = eventTrackingRepo.findAllByUserId(UserId.of(currentUser.id))

        if (events.isEmpty()) {
            return getPopularCategories()
        }

        val categoryScores = mutableMapOf<CategoryId, Int>()

        events.forEach { event ->
            val categoryIdOpt = getCategoryIdFromTarget(event)
            categoryIdOpt?.let { categoryId ->
                val weight = getEventWeight(event)
                categoryScores[categoryId] = categoryScores.getOrDefault(categoryId, 0) + weight
            }
        }

        if (categoryScores.isEmpty()) {
            return getPopularCategories()
        }

        return categoryScores.entries
            .sortedByDescending { it.value }
            .map { it.key }
            .take(5)
    }

    private fun getPopularCategories(): List<CategoryId> {
        return categoryRepository.findAll(PageRequest.of(0, 5))
            .content
            .map(Category::id)
    }

    private fun getCategoryIdFromTarget(event: EventTracking): CategoryId? {
        return when (event.targetType) {
            TargetType.SERVICE, TargetType.CONTACT_FOR_SERVICE -> portfolioClient.getServiceCategoryId(event.targetId).orElse(null)
            TargetType.JOB_POST, TargetType.JOB_APPLICATION -> portfolioClient.getJobPostCategoryId(event.targetId).orElse(null)
            TargetType.LIKE -> portfolioClient.getServiceCategoryId(event.targetId).orElse(null)
            TargetType.REVIEW -> portfolioClient.getServiceCategoryId(event.targetId).orElse(null)
            else -> null
        }
    }

    private fun getEventWeight(event: EventTracking): Int {
        return when (event.eventType) {
            EventType.LIKE -> 5
            EventType.REVIEW -> 4
            EventType.CLICK -> 2
            EventType.VIEW -> 1
        }
    }
}