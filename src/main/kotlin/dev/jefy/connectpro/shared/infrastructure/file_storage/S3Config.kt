package dev.jefy.connectpro.shared.infrastructure.file_storage

import dev.jefy.connectpro.shared.infrastructure.config.AwsProperties
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.s3.S3Client
import java.net.URI

@Configuration
class S3Config {

    @Bean
    fun s3Client(awsProperties: AwsProperties): S3Client {
        
        val credentials = AwsBasicCredentials.create(
            awsProperties.credentials.accessKey, 
            awsProperties.credentials.secretKey
        )
        return S3Client.builder()
            .endpointOverride(URI.create(awsProperties.s3.endpoint))
            .region(Region.of(awsProperties.region.static))
            .credentialsProvider(StaticCredentialsProvider.create(credentials))
            .forcePathStyle(true)
            .build()
    }
}