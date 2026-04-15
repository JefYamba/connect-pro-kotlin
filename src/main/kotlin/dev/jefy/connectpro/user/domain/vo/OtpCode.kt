package dev.jefy.connectpro.user.domain.vo

import jakarta.persistence.Embeddable
import org.springframework.util.Assert
import kotlin.random.Random

@Embeddable
data class OtpCode(var value: String) {
    init {
        require(value.isNotBlank() && value.matches(Regex("\\d{4}"))) { "OTP code must be exactly 4 digits" }
    }
    
    companion object {
        fun generate(): OtpCode {
            val code = (0..9999)
                .random()
                .toString()
                .padStart(4, '0')

            return OtpCode(code)
        }

        fun of(code: String): OtpCode {
            return OtpCode(code)
        }
    }
}