package dev.jefy.connectpro.user.domain.vo

import jakarta.persistence.Embeddable

@Embeddable
data class Email(var value: String) {
    init {
        require(value.isNotBlank()) { "email must not be blank" }
    }
    companion object {
        fun of(email: String) = Email(email)
    }
}