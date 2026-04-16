package dev.jefy.connectpro.portfolio.domain.vo

import dev.jefy.connectpro.shared.infrastructure.converter.AvailabilityListConverter
import jakarta.persistence.Column
import jakarta.persistence.Convert
import jakarta.persistence.Embeddable

@Embeddable
data class ProfessionalInfo(
    var numberOfEmployees: Int?,
    var activeYears: Int?,
    @Convert(converter = AvailabilityListConverter::class)
    @Column(name = "availabilities", columnDefinition = "text")
    var availabilities: List<Availability>
) {
    init {
        numberOfEmployees?.let { require(it > 0) { "Number of employees must be greater than 0" } }
    }
}