package dev.jefy.connectpro.management.appliacaion.dtos

import jakarta.validation.constraints.NotBlank


/**
 * @author Jôph Yamba
 */

data class AwardRequest(
    @field:NotBlank(message = "award name cannot be blank")
    val name: String,
    @field:NotBlank(message = "award description cannot be blank")
    val description: String,
    @field:NotBlank(message = "award color cannot be blank")
    val color: String
)

