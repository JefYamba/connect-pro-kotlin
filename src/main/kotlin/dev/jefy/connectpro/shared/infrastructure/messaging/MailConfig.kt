package dev.jefy.connectpro.shared.infrastructure.messaging

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.mail.javamail.JavaMailSenderImpl

/**
 * @author  Jôph Yamba
 */
@Configuration
class MailConfig {
    
    @Bean
    fun mailSender() = JavaMailSenderImpl()
}