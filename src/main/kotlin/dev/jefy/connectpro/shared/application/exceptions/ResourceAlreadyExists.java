package dev.jefy.connectpro.shared.application.exceptions;

/**
 * @author Jôph Yamba
 */
public class ResourceAlreadyExists extends RuntimeException {
    public ResourceAlreadyExists(String message) {
        super(message);
    }
}
