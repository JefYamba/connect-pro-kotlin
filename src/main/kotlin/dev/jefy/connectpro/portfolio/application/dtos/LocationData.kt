package dev.jefy.connectpro.portfolio.application.dtos

import dev.jefy.connectpro.portfolio.domain.vo.Location


/**
 * @author Jôph Yamba
 */

data class LocationData(
    val country: String,
    val city: String,
    val address: String?,
    val latitude: Double?,
    val longitude: Double?
) {
    fun toDomain(): Location {
        return Location(
            country = this.country,
            city = this.city,
            address = this.address,
            latitude = this.latitude,
            longitude = this.longitude
        )
    }
    
}

fun Location.toData(): LocationData = LocationData(
    country = this.country,
    city = this.city,
    address = this.address,
    latitude = this.latitude,
    longitude = this.longitude
)
