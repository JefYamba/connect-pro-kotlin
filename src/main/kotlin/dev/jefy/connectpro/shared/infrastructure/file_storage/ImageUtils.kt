package dev.jefy.connectpro.shared.infrastructure.file_storage

import dev.jefy.connectpro.shared.infrastructure.config.AwsProperties
import org.springframework.stereotype.Component
import org.springframework.util.Assert
import org.springframework.web.multipart.MultipartFile
import java.util.*

@Component
class ImageUtils(private val awsProperties: AwsProperties) {



    fun fileUrl(imageName: String): String {
        return "${getBucketUrlPrefix()}$imageName"
    }

    fun getBucketUrlPrefix(): String {
        val activeProfile = System.getProperty("spring.profiles.active")
        val isDev = activeProfile == null || activeProfile != "prod"

        return if (isDev) "${awsProperties.s3.publicUrl}/${awsProperties.s3.bucketName}/"
        else "https://${awsProperties.s3.bucketName}.s3.${awsProperties.region.static}.amazonaws.com/"
        
    }

    private fun getExtensionFromMimeContentType(image: MultipartFile): String {
        val contentType = image.contentType ?: throw IllegalArgumentException("Unable to determine image content type")

        val mimeToExt = mapOf("image/jpeg" to "jpg", "image/png" to "png")

        return mimeToExt[contentType.lowercase()] ?: throw IllegalArgumentException("The image type not supported: $contentType")
    }

    fun createUniqueImageName(extension: String): String {
        Assert.notNull(extension, "The image extension cannot be null")
        return "${UUID.randomUUID()}.$extension"
    }

    fun getExtension(image: MultipartFile): String {
        Assert.notNull(image, "The image cannot be null")

        // Validate MIME type first
        val extFromMime = getExtensionFromMimeContentType(image)

        // Cross-check with the actual file title (extra security)
        val originalFilename = image.originalFilename
        if (!originalFilename.isNullOrEmpty() && originalFilename.contains(".")) {
            var extFromName = originalFilename.substringAfterLast('.').lowercase()
            if (extFromName == "jpeg") extFromName = "jpg"
            if (extFromName != extFromMime) {
                throw IllegalArgumentException("File extension does not match the content type")
            }
        }

        return extFromMime
    }
}