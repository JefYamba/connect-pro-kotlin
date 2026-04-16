package dev.jefy.connectpro.recommandation.application.dtos

import dev.jefy.connectpro.recommandation.domain.vo.EventType
import dev.jefy.connectpro.recommandation.domain.vo.TargetType
import jakarta.validation.constraints.NotNull
import java.util.UUID

data class EventTrackingRequest(
    val eventType: EventType,
    val targetId: UUID,
    val targetType: TargetType
)