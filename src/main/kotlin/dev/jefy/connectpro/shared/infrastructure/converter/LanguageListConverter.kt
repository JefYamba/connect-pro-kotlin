package dev.jefy.connectpro.shared.infrastructure.converter

import dev.jefy.connectpro.portfolio.domain.vo.Language
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

@Converter
class LanguageListConverter : AttributeConverter<List<Language>, String> {

    override fun convertToDatabaseColumn(languages: List<Language>?): String =
        languages
            ?.takeIf { it.isNotEmpty() }
            ?.joinToString(",") { it.value }
            ?: ""

    override fun convertToEntityAttribute(value: String?): List<Language> =
        value
            ?.takeIf { it.isNotBlank() }
            ?.split(",")
            ?.mapNotNull { it.trim().takeIf { it.isNotEmpty() } }
            ?.map {
                runCatching { Language(it) }
                    .getOrElse { throw IllegalArgumentException("Invalid language: $it") }
            }
            ?: emptyList()
}