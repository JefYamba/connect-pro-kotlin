package dev.jefy.connectpro.shared.infrastructure.config

import org.springframework.boot.context.properties.ConfigurationProperties

/**
 * @author  Jôph Yamba
 */

@ConfigurationProperties(prefix = "app")
data class AppProperties(
    val mail: MailProperties
)

@ConfigurationProperties(prefix = "app.mail")
data class MailProperties(
    val provider: String = "javaMail"
)