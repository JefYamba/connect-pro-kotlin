package dev.jefy.connectpro.portfolio.domain.vo

import jakarta.persistence.Embeddable


@Embeddable
data class Tag(var value: String){
    init {
        require(value.isNotBlank()) { "tag must not be blank" }
    }
    companion object {
        fun of(tag: String) = Tag(tag)
    }
}