package dev.jefy.connectpro.shared.infrastructure.messaging;


import org.jspecify.annotations.NullMarked;
import org.springframework.scheduling.annotation.Async;

import dev.jefy.connectpro.shared.infrastructure.messaging.strategy.EmailStrategy;
import dev.jefy.connectpro.user.domain.vo.Email;

/**
 * @author Jôph Yamba
 */
@NullMarked
public interface EmailService {
    @Async
    void sendEmail(Email email, EmailStrategy strategy);
}
