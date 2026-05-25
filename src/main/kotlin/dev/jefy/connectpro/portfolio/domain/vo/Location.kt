package dev.jefy.connectpro.portfolio.domain.vo

import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
data class Location(
    @Column(nullable = false)
    var country: String,
    @Column(nullable = false)
    var city: String,
    var address: String? = null,
    var latitude: Double? = null,
    var longitude: Double? = null
) {
    init {
        require(country.isNotBlank()) { "country must not be blank" }
        require(city.isNotBlank()) { "city must not be blank" }
    }
}