package dev.jefy.connectpro.user.application.dtos

import jakarta.validation.constraints.NotBlank
import org.hibernate.validator.constraints.Length
import java.util.*

data class ActivateAccountRequest(
    val tokenId: UUID, 
    @field:NotBlank(message = "otp code must not be blank")
    @field:Length(min = 4, max = 4)
    val code: String)