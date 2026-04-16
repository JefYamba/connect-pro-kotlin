package dev.jefy.connectpro.recommandation.application;

import java.util.UUID;

import dev.jefy.connectpro.recommandation.domain.vo.EventType;
import dev.jefy.connectpro.recommandation.domain.vo.TargetType;

/**
 * @author Jôph Yamba
 */
public interface EventTrackingService {
    void trackEvent(EventType eventType, UUID targetId, TargetType targetType);
    void untrackEvent(EventType eventType, UUID targetId, TargetType targetType);
}
