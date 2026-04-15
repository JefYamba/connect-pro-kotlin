package dev.jefy.connectpro.shared.infrastructure.file_storage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URI;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

/**
 * @author Jôph Yamba
 */
@Configuration
public class S3Config {
    @Bean
    public S3Client s3Client(
            @Value("${aws.region.static}") String region,
            @Value("${aws.s3.endpoint}") String endpoint,
            @Value("${aws.credentials.access-key}") String accessKey,
            @Value("${aws.credentials.secret-key}") String secretKey
    ) {
        var  credentials = AwsBasicCredentials.create(accessKey, secretKey);
        return S3Client
                .builder()
                .endpointOverride(URI.create(endpoint))
                .region(Region.of(region))
                .credentialsProvider(
                        StaticCredentialsProvider.create(credentials)
                )
                .forcePathStyle(true)
                .build();
    }
}
