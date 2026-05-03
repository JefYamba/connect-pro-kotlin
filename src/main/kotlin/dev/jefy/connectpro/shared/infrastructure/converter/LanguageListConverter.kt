package dev.jefy.connectpro.shared.infrastructure.converter

import dev.jefy.connectpro.portfolio.domain.vo.Language
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

@Converter
class LanguageListConverter : AttributeConverter<MutableList<Language>, String> {

    override fun convertToDatabaseColumn(languages: MutableList<Language>?): String =
        languages
            ?.takeIf { it.isNotEmpty() }
            ?.joinToString(",") { it.value }
            ?: ""

    override fun convertToEntityAttribute(value: String?): MutableList<Language> =
        value
            ?.takeIf { it.isNotBlank() }
            ?.split(",")
            ?.mapNotNull { img -> img.trim().takeIf { it.isNotEmpty() } }
            ?.map { it ->
                runCatching { Language(it) }
                    .getOrElse { throw IllegalArgumentException("Invalid language: $it") }
            }
            ?.toMutableList()
            ?: mutableListOf()
}