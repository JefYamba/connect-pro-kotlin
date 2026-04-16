package dev.jefy.connectpro.recommandation.domain.repository

import dev.jefy.connectpro.recommandation.domain.EventTracking
import dev.jefy.connectpro.recommandation.domain.vo.EventTrackingId
import dev.jefy.connectpro.recommandation.domain.vo.EventType
import dev.jefy.connectpro.recommandation.domain.vo.TargetType
import dev.jefy.connectpro.user.domain.vo.UserId
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*

interface EventTrackingRepository : JpaRepository<EventTracking, EventTrackingId> {

    fun findAllByUserId(userId: UserId): List<EventTracking>

    @Query("""
        SELECT event.targetId FROM EventTracking event 
        WHERE event.userId = :userId 
        AND event.eventType = :eventType 
        AND event.targetType = :targetType
    """)
    fun findTargetIdsByUserIdAndEventTypeAndTargetType(
        @Param("userId") userId: UserId,
        @Param("eventType") eventType: EventType,
        @Param("targetType") targetType: TargetType
    ): List<UUID>

    fun deleteByUserIdAndEventTypeAndTargetIdAndTargetType(
        userId: UserId, 
        eventType: EventType, 
        targetId: UUID, 
        targetType: TargetType
    )
}