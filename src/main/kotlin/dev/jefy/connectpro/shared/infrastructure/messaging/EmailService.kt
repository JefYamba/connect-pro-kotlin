package dev.jefy.connectpro.shared.infrastructure.messaging

import org.springframework.scheduling.annotation.Async
import dev.jefy.connectpro.shared.infrastructure.messaging.strategy.EmailStrategy
import dev.jefy.connectpro.user.domain.vo.Email

interface EmailService {
    @Async
    fun sendEmail(email: Email, strategy: EmailStrategy)
}