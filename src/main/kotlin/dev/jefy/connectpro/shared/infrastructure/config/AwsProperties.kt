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

data class RegionProperties(
    val static: String
)

data class CredentialsProperties(
    val accessKey: String,
    val secretKey: String
)

data class S3Properties(
    val endpoint: String,
    val bucketName: String
)