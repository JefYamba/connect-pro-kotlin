package dev.jefy.connectpro.shared.infrastructure.converter

import dev.jefy.connectpro.portfolio.domain.vo.Availability
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

@Converter
class AvailabilityListConverter : AttributeConverter<List<Availability>, String> {

    override fun convertToDatabaseColumn(availabilities: List<Availability>?): String {
        return availabilities
            ?.joinToString(",") { it.name }
            ?: ""
    }

    override fun convertToEntityAttribute(value: String?): List<Availability> {
        if (value.isNullOrBlank()) return emptyList()
        return value.split(",").map { Availability.valueOf(it) }
    }
}