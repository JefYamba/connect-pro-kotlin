package dev.jefy.connectpro.recommandation;

import java.util.List;
import java.util.UUID;

import dev.jefy.connectpro.management.domain.vo.CategoryId;
import dev.jefy.connectpro.recommandation.domain.vo.EventType;
import dev.jefy.connectpro.recommandation.domain.vo.TargetType;

/**
 * @author Jôph Yamba
 */
public interface RecommandationClient {
    void trackEvent(EventType eventType, UUID targetId, TargetType targetType);
    void untrackEvent(EventType eventType, UUID targetId, TargetType targetType);

    List<CategoryId> getRecommendedCategories();
}
