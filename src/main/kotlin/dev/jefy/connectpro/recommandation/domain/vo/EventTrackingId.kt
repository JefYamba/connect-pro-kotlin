package dev.jefy.connectpro.recommandation.domain.vo

import jakarta.persistence.Embeddable
import java.util.*

@Embeddable
data class EventTrackingId(var value: UUID)  {
    companion object {
        fun generate(): EventTrackingId = EventTrackingId(UUID.randomUUID())
        fun of(value: UUID): EventTrackingId = EventTrackingId(value)
    }
}