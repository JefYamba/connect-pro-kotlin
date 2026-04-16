package dev.jefy.connectpro.shared.infrastructure.file_storage

import dev.jefy.connectpro.shared.domain.vo.ImageUrl
import org.springframework.web.multipart.MultipartFile
import java.io.IOException

interface ImageService {

    @Throws(IOException::class)
    fun save(imageFile: MultipartFile): ImageUrl

    @Throws(IOException::class)
    fun delete(imageUrl: ImageUrl)
}