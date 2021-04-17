package it.polimi.ingsw.exceptions;

public class AlreadyInAnotherLayerException extends Throwable {

    //Default constructor with default message
    public AlreadyInAnotherLayerException() {
        super("You can't place the same type of Resource in two different depot's layers");
    }

    //Custom constructor to set a custom message
    public AlreadyInAnotherLayerException(String customMessage) {
        super(customMessage);
    }
}
