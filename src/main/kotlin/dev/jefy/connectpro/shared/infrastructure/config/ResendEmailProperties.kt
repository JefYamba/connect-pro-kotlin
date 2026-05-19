package dev.jefy.connectpro.shared.infrastructure.config

import org.springframework.boot.context.properties.ConfigurationProperties

/**
 * @author  Jôph Yamba
 */
@ConfigurationProperties(prefix = "resend")
data class ResendEmailProperties(
    val apiKey: String = "",
    val email: String = ""
)
