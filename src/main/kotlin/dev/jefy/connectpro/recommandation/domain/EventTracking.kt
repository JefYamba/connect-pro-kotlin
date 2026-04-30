package dev.jefy.connectpro.recommandation.domain

import dev.jefy.connectpro.recommandation.domain.vo.EventTrackingId
import dev.jefy.connectpro.recommandation.domain.vo.EventType
import dev.jefy.connectpro.recommandation.domain.vo.TargetType
import dev.jefy.connectpro.user.domain.vo.UserId
import jakarta.persistence.*
import java.time.Instant
import java.util.*

@Entity
@Table(name = "events_tracking")
open class EventTracking(userId: UserId, eventType: EventType, targetId: UUID, targetType: TargetType) {
    @EmbeddedId
    @AttributeOverride(name = "value", column = Column(name = "id"))
    var id: EventTrackingId = EventTrackingId.generate()
        protected set

    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "user_id"))
    var userId: UserId = userId
        protected set

    @Enumerated(EnumType.STRING)
    var eventType: EventType = eventType
        protected set

    var targetId: UUID = targetId
        protected set

    @Enumerated(EnumType.STRING)
    var targetType: TargetType = targetType
        protected set

    var timestamp: Instant = Instant.now()
}