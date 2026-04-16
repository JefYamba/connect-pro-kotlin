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

    override fun convertToEntityAttribute(value: String?): MutableSet<Tag> {
        return  value?.takeIf { it.isNotBlank() }
            ?.split(",")
            ?.map { Tag(it) }
            ?.toMutableSet()
            ?: mutableSetOf()
    }
}