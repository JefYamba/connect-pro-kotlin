package dev.jefy.connectpro.shared.infrastructure.file_storage

import dev.jefy.connectpro.shared.domain.vo.Image
import org.springframework.web.multipart.MultipartFile
import java.io.IOException

interface ImageService {

    @Throws(IOException::class)
    fun save(imageFile: MultipartFile): Image

    @Throws(IOException::class)
    fun delete(image: Image)
}