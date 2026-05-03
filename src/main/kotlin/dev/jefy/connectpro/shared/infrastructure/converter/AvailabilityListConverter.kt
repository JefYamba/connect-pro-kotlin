package dev.jefy.connectpro.shared.infrastructure.converter

import dev.jefy.connectpro.portfolio.domain.vo.Availability
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

@Converter
class AvailabilityListConverter : AttributeConverter<MutableList<Availability>, String> {

    override fun convertToDatabaseColumn(availabilities: MutableList<Availability>?): String =
        availabilities
            ?.takeIf { it.isNotEmpty() }
            ?.joinToString(",") { it.name }
            ?: ""

    override fun convertToEntityAttribute(value: String?): MutableList<Availability> =
        value
            ?.takeIf { it.isNotBlank() }
            ?.split(",")
            ?.mapNotNull { img -> img.trim().takeIf { it.isNotEmpty() } }
            ?.map { it ->
                runCatching { Availability.valueOf(it) }
                    .getOrElse { throw IllegalArgumentException("Invalid availability value: $it") }
            }
            ?.toMutableList()
            ?: mutableListOf()
}