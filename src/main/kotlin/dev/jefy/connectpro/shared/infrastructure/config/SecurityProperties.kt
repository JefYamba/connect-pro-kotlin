package dev.jefy.connectpro.shared.infrastructure.config

/**
 * @author  Jôph Yamba
 */

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "security")
data class SecurityProperties(val otpToken: OtpTokenProperties, val jwt: JwtProperties)
@ConfigurationProperties(prefix = "security.otp-token")
data class OtpTokenProperties(val expirationTime: Long)
@ConfigurationProperties(prefix = "security.jwt")
data class JwtProperties(val secret: String, val expirationTime: Long)