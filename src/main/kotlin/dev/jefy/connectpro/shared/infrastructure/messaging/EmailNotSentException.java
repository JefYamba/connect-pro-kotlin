package dev.jefy.connectpro.shared.infrastructure.messaging;

/**
 * @author Jôph Yamba
 */
public class EmailNotSentException extends RuntimeException {
    public EmailNotSentException() {
        super("Failed to send email. Please try again later. ");
    }
}
