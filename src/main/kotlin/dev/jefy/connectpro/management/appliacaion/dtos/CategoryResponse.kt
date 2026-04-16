package dev.jefy.connectpro.management.appliacaion.dtos

import dev.jefy.connectpro.management.domain.Category
import java.util.*


/**
 * @author Jôph Yamba
 */

data class CategoryResponse(val id: UUID, val name: String, val description: String)

fun Category.toResponse() = CategoryResponse(
    id = this.id.value,
    name = this.name,
    description = this.description
)

