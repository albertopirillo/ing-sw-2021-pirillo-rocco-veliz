package it.polimi.ingsw.exceptions;

public class NotEnoughSpaceException extends Throwable {

    //Default constructor with default message
    public NotEnoughSpaceException() {
        super("The selected depot's layer cannot contain that amount of resources");
    }

    //Custom constructor to set a custom message
    public NotEnoughSpaceException(String customMessage) {
        super(customMessage);
    }
}
