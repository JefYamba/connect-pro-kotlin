package dev.jefy.connectpro.recommandation.application.dtos

import dev.jefy.connectpro.recommandation.domain.vo.EventType
import dev.jefy.connectpro.recommandation.domain.vo.TargetType
import java.util.*

data class EventTrackingRequest(
    val eventType: EventType,
    val targetId: UUID,
    val targetType: TargetType
)