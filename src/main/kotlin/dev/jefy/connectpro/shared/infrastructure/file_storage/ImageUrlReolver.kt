package dev.jefy.connectpro.shared.infrastructure.file_storage

import dev.jefy.connectpro.shared.domain.vo.Image
import org.springframework.stereotype.Component

/**
 * @author  Jôph Yamba
 */
@Component
class ImageUrlResolver(private val imageUtils: ImageUtils) {

    fun resolve(image: Image?): String? {
        return image?.let {
            imageUtils.fileUrl(it.value)
        }
    }

    fun resolve(images: Collection<Image>): List<String> {
        return images.map {
            imageUtils.fileUrl(it.value)
        }
    }
}