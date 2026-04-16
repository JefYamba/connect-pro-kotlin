package dev.jefy.connectpro.user.domain.vo

import jakarta.persistence.Embeddable
import java.util.*

/**
 * @author  Jôph Yamba
 */
@Embeddable
data class UserId(var value: UUID) {
    companion object {
        fun generate(): UserId = UserId(UUID.randomUUID())
        fun of(id: UUID): UserId = UserId(id)
    }
}

@Embeddable
data class TokenId(var value: UUID) {
    companion object {
        fun generate(): TokenId = TokenId(UUID.randomUUID())
        fun of(value: UUID): TokenId = TokenId(value)
    }
}