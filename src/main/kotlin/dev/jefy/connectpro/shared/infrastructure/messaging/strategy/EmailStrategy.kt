package dev.jefy.connectpro.shared.infrastructure.messaging.strategy;

import org.jspecify.annotations.NullMarked;

/**
 * @author Jôph Yamba
 */
@NullMarked
public interface EmailStrategy {
    String getMessage();
    String getSubject();
}
