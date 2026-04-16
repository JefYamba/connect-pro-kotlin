package dev.jefy.connectpro.management.appliacaion.command

import dev.jefy.connectpro.management.appliacaion.dtos.CategoryRequest
import dev.jefy.connectpro.management.domain.vo.CategoryId

/**
 * @author Jôph Yamba
 */

interface CategoryCommand {
    fun create(request: CategoryRequest): CategoryId
    fun update(categoryId: CategoryId, request: CategoryRequest): CategoryId
    fun delete(categoryId: CategoryId)
}
