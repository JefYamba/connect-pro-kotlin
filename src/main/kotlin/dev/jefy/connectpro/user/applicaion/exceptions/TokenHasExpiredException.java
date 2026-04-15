package dev.jefy.connectpro.user.applicaion.exceptions;

/**
 * @author Jôph Yamba
 */
public class TokenHasExpiredException extends RuntimeException {
    public TokenHasExpiredException() {
        super("Activation token has expired. A new token has been send to the same value address");
    }
}

