package dev.jefy.connectpro.shared.infrastructure.converter

import dev.jefy.connectpro.shared.domain.vo.ImageUrl
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter
import java.util.Collections.emptyList

@Converter
class ImagesUrlListConverter : AttributeConverter<MutableList<ImageUrl>, String> {

    override fun convertToDatabaseColumn(imageUrls: MutableList<ImageUrl>?): String =
        imageUrls
            ?.takeIf { it.isNotEmpty() }
            ?.joinToString(",") { it.value }
            ?: ""

    override fun convertToEntityAttribute(value: String?): MutableList<ImageUrl> =
        (value
            ?.takeIf { it.isNotBlank() }
            ?.split(",")
            ?.mapNotNull { stringTag -> stringTag.trim().takeIf { it.isNotEmpty() } }
            ?.map { text ->
                runCatching { ImageUrl(text) }
                    .getOrElse { throw IllegalArgumentException("Invalid image URL: $it") }
            }
            ?: emptyList()) as MutableList<ImageUrl>
}