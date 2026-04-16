package dev.jefy.connectpro.shared.infrastructure.converter

import dev.jefy.connectpro.portfolio.domain.vo.Availability
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

@Converter
class AvailabilityListConverter : AttributeConverter<List<Availability>, String> {

    override fun convertToDatabaseColumn(availabilities: List<Availability>?): String =
        availabilities
            ?.takeIf { it.isNotEmpty() }
            ?.joinToString(",") { it.name }
            ?: ""

    override fun convertToEntityAttribute(value: String?): List<Availability> =
        value
            ?.takeIf { it.isNotBlank() }
            ?.split(",")
            ?.mapNotNull { it.trim().takeIf { it.isNotEmpty() } }
            ?.map {
                runCatching { Availability.valueOf(it) }
                    .getOrElse { throw IllegalArgumentException("Invalid availability value: $it") }
            }
            ?: emptyList()
}