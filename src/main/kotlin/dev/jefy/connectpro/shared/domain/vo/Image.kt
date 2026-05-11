package dev.jefy.connectpro.shared.domain.vo

import jakarta.persistence.Embeddable

@Embeddable
data class Image(var value: String){
    init {
        require(value.matches(IMAGE_KEY_REGEX)) { "Invalid image key format" }
    }
    companion object {
        private val IMAGE_KEY_REGEX = Regex(
            "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}\\.(png|jpg|jpeg)$"
        )
    }
}