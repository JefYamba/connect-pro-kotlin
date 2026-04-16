package dev.jefy.connectpro.shared.domain.vo

import jakarta.persistence.Embeddable
import jakarta.persistence.Embedded
import org.hibernate.annotations.EmbeddedTable

@Embeddable
data class ImageUrl(var value: String){
    init {
        require(value.isNotBlank()) { "image url must not be blank" }
    }
}