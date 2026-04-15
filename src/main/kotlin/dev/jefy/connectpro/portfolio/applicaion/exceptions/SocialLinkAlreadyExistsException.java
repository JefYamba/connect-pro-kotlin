package dev.jefy.connectpro.portfolio.applicaion.exceptions;


/**
 * @author Jôph Yamba
 */
public class SocialLinkAlreadyExistsException extends RuntimeException {
    public SocialLinkAlreadyExistsException(String message) {
        super(message);
    }
}
