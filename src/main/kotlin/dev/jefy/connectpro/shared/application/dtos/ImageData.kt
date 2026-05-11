package dev.jefy.connectpro.shared.application.dtos

import dev.jefy.connectpro.shared.domain.vo.Image
import dev.jefy.connectpro.shared.infrastructure.file_storage.ImageUrlResolver
import jakarta.validation.constraints.NotBlank

/**
 * @author  Jôph Yamba
 */
data class ImageData(@field:NotBlank var value: String){
    fun getKey() = Image(value.substringAfterLast("/"))
}
fun Image.toData(resolver: ImageUrlResolver): ImageData {
    val image = resolver.resolve(this) ?: throw IllegalArgumentException("Image not found")
    return ImageData(image)
}