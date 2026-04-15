package dev.jefy.connectpro.marketplace.applicaion.exception;


/**
 * @author Jôph Yamba
 */
public class JobPostNotExistOrValidException extends RuntimeException {
    public JobPostNotExistOrValidException() {
        super("Job Post Not Exist Or Valid");
    }
}
