package dev.jefy.connectpro.shared.infrastructure.converter

import dev.jefy.connectpro.portfolio.domain.vo.Tag
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

@Converter
class TagListConverter : AttributeConverter<MutableSet<Tag>, String> {

    override fun convertToDatabaseColumn(tags: MutableSet<Tag>?): String {
        return tags?.takeIf { it.isNotEmpty() }
            ?.joinToString(",") { it.value } ?: ""
    }

    override fun convertToEntityAttribute(value: String?): MutableSet<Tag> =
        value
            ?.takeIf { it.isNotBlank() }
            ?.split(",")
            ?.mapNotNull { img -> img.trim().takeIf { it.isNotEmpty() } }
            ?.map { it ->
                runCatching { Tag(it) }
                    .getOrElse { throw IllegalArgumentException("Invalid Tag: $it") }
            }
            ?.toMutableSet()
            ?: mutableSetOf()
}