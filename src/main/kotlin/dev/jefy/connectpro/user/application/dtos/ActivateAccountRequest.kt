package dev.jefy.connectpro.user.application.dtos

import jakarta.validation.constraints.NotBlank
import java.util.UUID
import jakarta.validation.constraints.NotNull
import org.hibernate.validator.constraints.Length

data class ActivateAccountRequest(
    val tokenId: UUID, 
    @field:NotBlank(message = "otp code must not be blank")
    @field:Length(min = 4, max = 4)
    val code: String)