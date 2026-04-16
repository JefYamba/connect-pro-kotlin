package dev.jefy.connectpro.recommandation.domain.vo

import java.util.UUID

data class EventTrackingId(val value: UUID)  {
    companion object {
        fun generate(): EventTrackingId = EventTrackingId(UUID.randomUUID())
        fun of(value: UUID): EventTrackingId = EventTrackingId(value)
    }
}