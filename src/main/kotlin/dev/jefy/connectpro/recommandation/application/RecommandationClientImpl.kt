package dev.jefy.connectpro.recommandation.application;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import dev.jefy.connectpro.management.domain.vo.CategoryId;
import dev.jefy.connectpro.recommandation.RecommandationClient;
import dev.jefy.connectpro.recommandation.domain.vo.EventType;
import dev.jefy.connectpro.recommandation.domain.vo.TargetType;
import lombok.RequiredArgsConstructor;

/**
 * @author Jôph Yamba
 */
@Service
@RequiredArgsConstructor
public class RecommandationClientImpl implements RecommandationClient {
    private final EventTrackingService eventTrackingService;
    private final RecommandationService recommandationService;

    @Override
    public void trackEvent(EventType eventType, UUID targetId, TargetType targetType) {
        eventTrackingService.trackEvent(eventType, targetId, targetType);
    }

    @Override
    public void untrackEvent(EventType eventType, UUID targetId, TargetType targetType) {
        eventTrackingService.untrackEvent(eventType, targetId, targetType);
    }

    @Override
    public List<CategoryId> getRecommendedCategories() {
        return recommandationService.getRecommendedCategories();
    }
}
