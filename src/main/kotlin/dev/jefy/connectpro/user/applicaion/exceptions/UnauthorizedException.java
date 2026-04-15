package dev.jefy.connectpro.user.applicaion.exceptions;


/**
 * @author Jôph Yamba
 */
public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String message) {
        super(message);
    }
}
