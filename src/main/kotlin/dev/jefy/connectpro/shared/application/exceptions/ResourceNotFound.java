package dev.jefy.connectpro.shared.application.exceptions;

/**
 * @author Jôph Yamba
 */
public class ResourceNotFound extends RuntimeException {
    public ResourceNotFound(String message) {
        super(message);
    }
}
