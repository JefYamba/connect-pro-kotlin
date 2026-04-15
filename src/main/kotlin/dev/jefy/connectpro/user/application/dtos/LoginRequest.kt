package dev.jefy.connectpro.user.application.dtos

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import org.jspecify.annotations.NullMarked

@NullMarked
data class LoginRequest(
    @field:NotBlank(message = "email must not be blank")
    @field:Email(message = "email is not valid email address")
    val email: String,
    @field:NotBlank(message = "Password must not be blank")
    val password: String
)