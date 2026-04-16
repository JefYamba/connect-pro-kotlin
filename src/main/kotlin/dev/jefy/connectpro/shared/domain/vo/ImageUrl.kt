package dev.jefy.connectpro.shared.domain.vo

import jakarta.persistence.Embeddable

@Embeddable
data class ImageUrl(var value: String){
    init {
        require(value.isNotBlank()) { "image url must not be blank" }
    }
}