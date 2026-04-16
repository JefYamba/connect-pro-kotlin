package dev.jefy.connectpro.management.appliacaion.query

import dev.jefy.connectpro.management.appliacaion.dtos.CategoryResponse
import dev.jefy.connectpro.management.domain.vo.CategoryId
import org.jspecify.annotations.NullMarked

/**
 * @author Jôph Yamba
 */

interface CategoryQuery {
    fun get(categoryId: CategoryId): CategoryResponse
    fun getAll(): List<CategoryResponse>
}
