package dev.jefy.connectpro.engagement.applicaion.exception;


/**
 * @author Jôph Yamba
 */
public class ServiceNotExistOrValidException extends RuntimeException {
    public ServiceNotExistOrValidException(){
        super("Service Not Exist Or Valid");
    }
}
