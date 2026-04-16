package dev.jefy.connectpro.management.appliacaion.dtos

import jakarta.validation.constraints.NotBlank


/**
 * @author Jôph Yamba
 */
data class BadgeRequest(
    @field:NotBlank(message = "badge name cannot be blank")
    val name: String,
    @field:NotBlank(message = "badge description cannot be blank")
    val description: String,
    @field:NotBlank(message = "badge color cannot be blank")
    val color: String
)
