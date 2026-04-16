package dev.jefy.connectpro.shared.infrastructure.file_storage;

import org.jspecify.annotations.NullMarked;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import dev.jefy.connectpro.shared.domain.vo.ImageUrl;
import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

/**
 * @author Jôph Yamba
 */
@NullMarked
@Service
@RequiredArgsConstructor
class AwsS3ImageServiceImpl implements ImageService {

    private final ImageUtils imageUtils;


    private final S3Client s3Client;

    @Value("${aws.s3.bucket-name}")
    private String s3Bucket;


    @Override
    public ImageUrl save(MultipartFile imageFile) throws IOException {
        String extension = imageUtils.getExtension(imageFile);
        String imageName = imageUtils.createUniqueImageName(extension);
        PutObjectRequest objectRequest = PutObjectRequest
                .builder()
                .bucket(s3Bucket)
                .key(imageName)
                .contentType(imageFile.getContentType())
                .build();

        s3Client.putObject(
                objectRequest,
                RequestBody.fromBytes(imageFile.getBytes())
        );
        return new ImageUrl(imageUtils.fileUrl(imageName));
    }


    @Override
    public void delete(ImageUrl image) throws IOException {
        String imageUrl = image.value();
        String bucketUrlPrefix = imageUtils.getBucketUrlPrefix();
        if (!imageUrl.startsWith(bucketUrlPrefix)) {
            throw new IllegalArgumentException("Invalid file URL: " + imageUrl);
        }

        // Extract the S3 key from the URL
        String key = imageUrl.substring(bucketUrlPrefix.length());

        // Delete the object
        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                .bucket(s3Bucket)
                .key(key)
                .build();

        s3Client.deleteObject(deleteObjectRequest);
    }
}
