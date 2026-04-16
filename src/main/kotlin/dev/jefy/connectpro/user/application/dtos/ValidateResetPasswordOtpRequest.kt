package dev.jefy.connectpro.user.application.dtos

import jakarta.validation.constraints.NotBlank
import org.hibernate.validator.constraints.Length
import java.util.*

data class ValidateResetPasswordOtpRequest(
    val tokenId: UUID,
    @field:NotBlank(message = "OTP must not be blank")
    @field:Length(min = 4, max = 4, message = "OTP must be 4 digits")
    val code: String
)