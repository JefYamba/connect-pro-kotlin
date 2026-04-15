package dev.jefy.connectpro.portfolio.applicaion.exceptions;


/**
 * @author Jôph Yamba
 */
public class PortfolioAlreadyExistsException extends RuntimeException {
    public PortfolioAlreadyExistsException(String message) {
        super(message);
    }
}

