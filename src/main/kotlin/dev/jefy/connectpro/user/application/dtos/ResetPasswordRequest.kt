package dev.jefy.connectpro.user.application.dtos

import jakarta.validation.constraints.NotBlank
import java.util.UUID

data class ResetPasswordRequest(
    val tokenId: UUID,
    @field:NotBlank(message = "new password must not be blank") 
    val newPassword: String,
    @field:NotBlank(message = "confirm password must not be blank") 
    val confirmPassword: String
)