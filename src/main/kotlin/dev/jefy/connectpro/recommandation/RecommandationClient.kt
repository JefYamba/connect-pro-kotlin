package dev.jefy.connectpro.recommandation

import dev.jefy.connectpro.management.domain.vo.CategoryId
import dev.jefy.connectpro.recommandation.domain.vo.EventType
import dev.jefy.connectpro.recommandation.domain.vo.TargetType
import java.util.*

interface RecommandationClient {
    fun trackEvent(eventType: EventType, targetId: UUID, targetType: TargetType)
    fun untrackEvent(eventType: EventType, targetId: UUID, targetType: TargetType)
    fun getRecommendedCategories(): List<CategoryId>
}