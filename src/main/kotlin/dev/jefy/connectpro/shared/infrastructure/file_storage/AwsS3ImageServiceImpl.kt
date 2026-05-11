package dev.jefy.connectpro.shared.infrastructure.file_storage

import dev.jefy.connectpro.shared.domain.vo.Image
import dev.jefy.connectpro.shared.infrastructure.config.AwsProperties
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import software.amazon.awssdk.core.sync.RequestBody
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import java.io.IOException

@Service
class AwsS3ImageServiceImpl(
    private val imageUtils: ImageUtils,
    private val s3Client: S3Client,
    private val awsProperties: AwsProperties
) : ImageService {
    
    @Throws(IOException::class)
    override fun save(imageFile: MultipartFile): Image {
        val extension = imageUtils.getExtension(imageFile)
        val imageName = imageUtils.createUniqueImageName(extension)
        val objectRequest = PutObjectRequest.builder()
            .bucket(awsProperties.s3.bucketName)
            .key(imageName)
            .contentType(imageFile.contentType)
            .build()
        
        s3Client.putObject(objectRequest, RequestBody.fromBytes(imageFile.bytes))
        return Image(imageName)
    }

    @Throws(IOException::class)
    override fun delete(image: Image) {
        val deleteObjectRequest = DeleteObjectRequest.builder()
            .bucket(awsProperties.s3.bucketName)
            .key(image.value)
            .build()

        s3Client.deleteObject(deleteObjectRequest)
    }
}