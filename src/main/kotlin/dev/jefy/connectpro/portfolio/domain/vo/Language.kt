package dev.jefy.connectpro.portfolio.domain.vo
import jakarta.persistence.Embeddable

@Embeddable
data class Language(var value: String) {
    init {
        require(value.isNotBlank()) { "language must not be blank" }
    }
    companion object {
        fun of(language: String) = Language(language)
    }
}