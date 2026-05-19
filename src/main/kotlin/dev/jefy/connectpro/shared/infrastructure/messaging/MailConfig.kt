package dev.jefy.connectpro.shared.infrastructure.messaging

import dev.jefy.connectpro.shared.infrastructure.config.ResendEmailProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration

/**
 * @author  Jôph Yamba
 */
@Configuration
@EnableConfigurationProperties(ResendEmailProperties::class)
class MailConfig {
}