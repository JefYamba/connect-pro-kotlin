package dev.jefy.connectpro.shared.infrastructure.converter


import dev.jefy.connectpro.portfolio.domain.vo.Pricing
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter
import tools.jackson.databind.ObjectMapper
import tools.jackson.module.kotlin.jacksonObjectMapper

@Converter
class PricingConverter : AttributeConverter<Pricing, String> {

    companion object {
        private val objectMapper: ObjectMapper = jacksonObjectMapper()
    }

    override fun convertToDatabaseColumn(pricing: Pricing?): String? = pricing?.let { it ->
        runCatching { objectMapper.writeValueAsString(it) }
            .getOrElse { throw IllegalArgumentException("Error converting Pricing to JSON", it) }
        }

    override fun convertToEntityAttribute(value: String?): Pricing? = value?.takeIf { it.isNotBlank() }?.let { it ->
        runCatching { objectMapper.readValue(it, Pricing::class.java) }
            .getOrElse { throw IllegalArgumentException("Error converting JSON to Pricing", it) }
        }
}