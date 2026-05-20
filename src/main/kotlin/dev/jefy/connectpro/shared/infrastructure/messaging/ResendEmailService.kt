package dev.jefy.connectpro.shared.infrastructure.messaging

import com.resend.Resend
import com.resend.services.emails.model.CreateEmailOptions
import dev.jefy.connectpro.shared.infrastructure.config.ResendEmailProperties
import dev.jefy.connectpro.shared.infrastructure.messaging.strategy.EmailStrategy
import dev.jefy.connectpro.user.domain.vo.Email
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.stereotype.Service

/**
 * @author  Jôph Yamba
 */
@Service
@ConditionalOnProperty(
    name = ["app.mail.provider"],
    havingValue = "resend"
)
class ResendEmailService(
    val emailProperties: ResendEmailProperties
) : EmailService {
    private val resend = Resend(emailProperties.apiKey)
    private val logger: Logger = LoggerFactory.getLogger(ResendEmailService::class.java)
    override fun send(email: Email, strategy: EmailStrategy) {
        try {
            val request = CreateEmailOptions.builder()
                .from(emailProperties.email)
                .to(listOf(email.value))
                .subject(strategy.subject())
                .text(strategy.message())
                .build()

            resend.emails().send(request)
        } catch (e: Exception) {
            logger.error("Resend API error: ${e.message}", e)
            throw EmailNotSentException()
        }
    }
}