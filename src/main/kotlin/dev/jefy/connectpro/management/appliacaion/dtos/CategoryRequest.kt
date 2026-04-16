package dev.jefy.connectpro.management.appliacaion.dtos

import jakarta.validation.constraints.NotBlank


/**
 * @author Jôph Yamba
 */

data class CategoryRequest(
    @field:NotBlank(message = "category name is required")
    val name: String,
    @field:NotBlank(message = "category description is required")
    val description: String
)
