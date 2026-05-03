package dev.jefy.connectpro.shared.infrastructure.config

import org.springframework.boot.context.properties.ConfigurationProperties

/**
 * @author  Jôph Yamba
 */

@ConfigurationProperties(prefix = "aws")
data class AwsProperties(
    val region: RegionProperties,
    val credentials: CredentialsProperties,
    val s3: S3Properties
)
@ConfigurationProperties(prefix = "aws.region")
data class RegionProperties(
    val static: String
)
@ConfigurationProperties(prefix = "aws.credentials")
data class CredentialsProperties(
    val accessKey: String,
    val secretKey: String
)
@ConfigurationProperties(prefix = "aws.s3")
data class S3Properties(
    val endpoint: String,
    val bucketName: String,
    val publicUrl: String
)