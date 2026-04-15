package dev.jefy.connectpro.shared.infrastructure.messaging;

/**
 * @author Jôph Yamba
 */
public class EmailDoesNotExistsException extends RuntimeException {
    public EmailDoesNotExistsException() {
        super("Email not found");
    }
}
