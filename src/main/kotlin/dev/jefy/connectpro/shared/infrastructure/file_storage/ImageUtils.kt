package dev.jefy.connectpro.shared.infrastructure.file_storage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
import java.util.UUID;

/**
 * @author Jôph Yamba
 */
@Component
public class ImageUtils {
    @Value("${aws.region.static}")
    private String s3Region;

    @Value("${aws.s3.bucket-name}")
    private String s3Bucket;

    @Value("${aws.s3.endpoint}")
    private String s3Endpoint;


    String fileUrl(String imageName) {
        return "%s/%s".formatted(getBucketUrlPrefix(), imageName);
    }

    String getBucketUrlPrefix() {
        String activeProfile = System.getProperty("spring.profiles.active");
        boolean isDev = activeProfile == null || !activeProfile.equals("prod");

        if (isDev) return "%s/%s/".formatted(s3Endpoint, s3Bucket);

        return "https://%s.s3.%s.amazonaws.com/".formatted(s3Bucket, s3Region);
    }

    private String getExtensionFromMimeContentType(MultipartFile image) {
        String contentType = image.getContentType();

        if (contentType == null) {
            throw new IllegalArgumentException("Unable to determine image content type");
        }

        var mimeToExt = Map.of("image/jpeg", "jpg", "image/png", "png");

        String extFromMime = mimeToExt.get(contentType.toLowerCase());
        if (extFromMime == null) {
            throw new IllegalArgumentException("The image type not supported: " + contentType);
        }
        return extFromMime;
    }

    String createUniqueImageName(String extension) {
        Assert.notNull(extension, "The image extension cannot be null");
        return String.format("%s.%s", UUID.randomUUID(), extension);
    }

    String getExtension(MultipartFile image) {
        Assert.notNull(image, "The image cannot be null");

        // Validate MIME type first
        String extFromMime = getExtensionFromMimeContentType(image);

        // Cross-check with the actual file title (extra security)
        String originalFilename = image.getOriginalFilename();
        if (originalFilename != null && originalFilename.contains(".")) {
            String extFromName = originalFilename.substring(originalFilename.lastIndexOf('.') + 1).toLowerCase();
            if (extFromName.equals("jpeg")) extFromName = "jpg";
            if (!extFromName.equals(extFromMime)) {
                throw new IllegalArgumentException("File extension does not match the content type");
            }
        }

        return extFromMime;
    }
}
