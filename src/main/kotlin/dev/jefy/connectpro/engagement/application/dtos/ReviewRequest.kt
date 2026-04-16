package dev.jefy.connectpro.engagement.application.dtos

import jakarta.validation.constraints.NotBlank


/**
 * @author Jôph Yamba
 */

data class ReviewRequest(
    val rating: Int,
    @field:NotBlank(message = "comment is required")
    val comment: String
)
