package dev.jefy.connectpro.shared.infrastructure.messaging

import dev.jefy.connectpro.shared.infrastructure.messaging.strategy.EmailStrategy
import dev.jefy.connectpro.user.domain.vo.Email
import org.springframework.scheduling.annotation.Async

interface EmailService {
    @Async
    fun sendEmail(email: Email, strategy: EmailStrategy)
}