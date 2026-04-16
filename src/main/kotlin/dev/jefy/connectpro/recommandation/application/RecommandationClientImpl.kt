package dev.jefy.connectpro.recommandation.application

import dev.jefy.connectpro.management.domain.vo.CategoryId
import dev.jefy.connectpro.recommandation.RecommandationClient
import dev.jefy.connectpro.recommandation.domain.vo.EventType
import dev.jefy.connectpro.recommandation.domain.vo.TargetType
import org.springframework.stereotype.Service
import java.util.*

@Service
class RecommandationClientImpl(
    private val eventTrackingService: EventTrackingService,
    private val recommandationService: RecommandationService
) : RecommandationClient {

    override fun trackEvent(eventType: EventType, targetId: UUID, targetType: TargetType) {
        eventTrackingService.trackEvent(eventType, targetId, targetType)
    }

    override fun untrackEvent(eventType: EventType, targetId: UUID, targetType: TargetType) {
        eventTrackingService.untrackEvent(eventType, targetId, targetType)
    }

    override fun getRecommendedCategories(): List<CategoryId> {
        return recommandationService.getRecommendedCategories()
    }
}