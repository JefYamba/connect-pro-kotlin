package dev.jefy.connectpro.user.application.dtos

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import org.jspecify.annotations.NullMarked

@NullMarked
data class CreateAccountRequest(
    @field:NotBlank(message = "name cannot be empty")
    val name: String,
    @field:Email(message = "email is not valid email address")
    @field:NotBlank(message = "email cannot be empty")
    val email: String,
    @field:NotBlank(message = "password cannot be empty")
    val password: String,
    @field:NotBlank(message = "confirm value cannot be empty")
    val confirmPassword: String
)