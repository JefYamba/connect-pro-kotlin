package dev.jefy.connectpro.user.application.dtos

import jakarta.validation.constraints.NotBlank

data class ChangePasswordRequest(
    @field:NotBlank(message = "current password must not be blank") 
    val currentPassword: String,
    @field:NotBlank(message = "new password must not be blank") 
    val newPassword: String,
    @field:NotBlank(message = "confirm password must not be blank") 
    val confirmPassword: String
)