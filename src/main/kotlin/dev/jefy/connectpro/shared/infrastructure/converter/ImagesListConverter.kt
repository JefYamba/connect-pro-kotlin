package dev.jefy.connectpro.shared.infrastructure.converter

import dev.jefy.connectpro.shared.domain.vo.Image
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

@Converter
class ImagesListConverter : AttributeConverter<MutableList<Image>, String> {

    override fun convertToDatabaseColumn(images: MutableList<Image>?): String =
        images
            ?.takeIf { it.isNotEmpty() }
            ?.joinToString(",") { it.value }
            ?: ""

    override fun convertToEntityAttribute(value: String?): MutableList<Image> =
        value
            ?.takeIf { it.isNotBlank() }
            ?.split(",")
            ?.mapNotNull { img -> img.trim().takeIf { it.isNotEmpty() } }
            ?.map { text ->
                runCatching { Image(text) }
                    .getOrElse { throw IllegalArgumentException("Invalid image URL: $it") }
            }
            ?.toMutableList()
            ?: mutableListOf()
}