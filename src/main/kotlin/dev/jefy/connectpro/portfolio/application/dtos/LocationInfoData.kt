package dev.jefy.connectpro.portfolio.application.dtos

import dev.jefy.connectpro.portfolio.domain.vo.LocationInfo


/**
 * @author Jôph Yamba
 */

data class LocationInfoData(
    val country: String,
    val city: String,
    val address: String?,
    val latitude: Double?,
    val longitude: Double?
) {
    fun toDomain(): LocationInfo {
        return LocationInfo(
            country = this.country,
            city = this.city,
            address = this.address,
            latitude = this.latitude,
            longitude = this.longitude
        )
    }
    
}

fun LocationInfo.toData(): LocationInfoData = LocationInfoData(
    country = this.country,
    city = this.city,
    address = this.address,
    latitude = this.latitude,
    longitude = this.longitude
)
