package dev.jefy.connectpro.recommandation.application

import dev.jefy.connectpro.recommandation.domain.vo.EventType
import dev.jefy.connectpro.recommandation.domain.vo.TargetType
import java.util.*

interface EventTrackingService {
    fun trackEvent(eventType: EventType, targetId: UUID, targetType: TargetType)
    fun untrackEvent(eventType: EventType, targetId: UUID, targetType: TargetType)
}