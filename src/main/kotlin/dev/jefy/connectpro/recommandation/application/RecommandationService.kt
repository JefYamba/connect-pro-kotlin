package dev.jefy.connectpro.recommandation.application

import dev.jefy.connectpro.management.domain.vo.CategoryId

interface RecommandationService {
    fun getRecommendedCategories(): List<CategoryId>
}