package dev.jefy.connectpro.engagement.domain.vo

import jakarta.persistence.Embeddable

@Embeddable
data class Rating(var value: Int) {

    init {
        require(value in 1..5) { "rating must be between 1 and 5" }
    }

    companion object {
        fun of(rating: Int): Rating = Rating(rating)
    }
}