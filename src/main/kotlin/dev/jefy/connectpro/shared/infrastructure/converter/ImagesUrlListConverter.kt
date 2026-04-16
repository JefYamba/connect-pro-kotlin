package dev.jefy.connectpro.shared.infrastructure.converter

import dev.jefy.connectpro.shared.domain.vo.ImageUrl
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

@Converter
class ImagesUrlListConverter : AttributeConverter<List<ImageUrl>, String> {

    override fun convertToDatabaseColumn(imageUrls: List<ImageUrl>?): String =
        imageUrls
            ?.takeIf { it.isNotEmpty() }
            ?.joinToString(",") { it.value }
            ?: ""

    override fun convertToEntityAttribute(value: String?): List<ImageUrl> =
        value
            ?.takeIf { it.isNotBlank() }
            ?.split(",")
            ?.mapNotNull { it.trim().takeIf { it.isNotEmpty() } }
            ?.map {
                runCatching { ImageUrl(it) }
                    .getOrElse { throw IllegalArgumentException("Invalid image URL: $it") }
            }
            ?: emptyList()
}