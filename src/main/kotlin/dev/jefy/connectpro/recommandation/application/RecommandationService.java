package dev.jefy.connectpro.recommandation.application;

import java.util.List;

import dev.jefy.connectpro.management.domain.vo.CategoryId;

/**
 * @author Jôph Yamba
 */
public interface RecommandationService {
    List<CategoryId> getRecommendedCategories();
}
