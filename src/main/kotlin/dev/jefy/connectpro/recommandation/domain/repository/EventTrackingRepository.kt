package dev.jefy.connectpro.recommandation.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

import dev.jefy.connectpro.recommandation.domain.EventTracking;
import dev.jefy.connectpro.recommandation.domain.vo.EventTrackingId;
import dev.jefy.connectpro.recommandation.domain.vo.EventType;
import dev.jefy.connectpro.recommandation.domain.vo.TargetType;
import dev.jefy.connectpro.user.domain.vo.UserId;

/**
 * @author Jôph Yamba
 */
public interface EventTrackingRepository extends JpaRepository<EventTracking, EventTrackingId> {
    
    List<EventTracking> findAllByUserId(UserId userId);
    
    @Query("SELECT e.targetId FROM EventTracking e WHERE e.userId = :userId AND e.eventType = :eventType AND e.targetType = :targetType")
    List<UUID> findTargetIdsByUserIdAndEventTypeAndTargetType(
            @Param("userId") UserId userId,
            @Param("eventType") EventType eventType,
            @Param("targetType") TargetType targetType
    );

    void deleteByUserIdAndEventTypeAndTargetIdAndTargetType(UserId userId, EventType eventType, UUID targetId, TargetType targetType);
}
