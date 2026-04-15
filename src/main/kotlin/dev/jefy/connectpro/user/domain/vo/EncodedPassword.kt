package dev.jefy.connectpro.user.domain.vo

import jakarta.persistence.Embeddable

@Embeddable
data class EncodedPassword(var value: String){
    init {
        require(value.isNotBlank()) { "Encoded password must not be blank" }
    }
}