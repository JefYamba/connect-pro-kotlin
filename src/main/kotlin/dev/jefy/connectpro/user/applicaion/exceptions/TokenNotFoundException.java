package dev.jefy.connectpro.user.applicaion.exceptions;

/**
 * @author Jôph Yamba
 */
public class TokenNotFoundException extends RuntimeException {
    public TokenNotFoundException() {
        super("invalid token");
    }
}


