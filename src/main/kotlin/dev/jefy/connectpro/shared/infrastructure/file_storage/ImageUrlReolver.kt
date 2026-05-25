package dev.jefy.connectpro.shared.infrastructure.file_storage

import org.springframework.stereotype.Component

/**
 * @author  Jôph Yamba
 */
@Component
class ImageUrlResolver(private val imageUtils: ImageUtils) {

    fun resolve(image: String?): String? {
        return image?.let {
            imageUtils.fileUrl(it)
        }
    }

    fun resolve(images: Collection<String>): List<String> {
        return images.map {
            imageUtils.fileUrl(it)
        }
    }
}