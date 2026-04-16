package dev.jefy.connectpro.shared.infrastructure.messaging

import dev.jefy.connectpro.shared.infrastructure.messaging.strategy.EmailStrategy
import dev.jefy.connectpro.user.domain.vo.Email
import jakarta.mail.MessagingException
import org.springframework.beans.factory.annotation.Value
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import kotlin.jvm.Throws

@Service
class EmailServiceImpl(
    private val mailSender: JavaMailSender,
    @Value("\${spring.mail.username}") private val sendingEmail: String
) : EmailService {

    @Async
    override fun sendEmail(email: Email, strategy: EmailStrategy) {
        val message = strategy.message()
        val subject = strategy.subject()
        try {
            doSendEmail(email.value, message, subject)
        } catch (_: MessagingException) {
            throw EmailNotSentException()
        }
    }

    @Throws(MessagingException::class)
    private fun doSendEmail(toEmail: String, message: String, subject: String) {
        val mailMessage = SimpleMailMessage().apply {
            from = sendingEmail
            setTo(toEmail)
            this.subject = subject
            text = message
        }
        mailSender.send(mailMessage)
    }
}