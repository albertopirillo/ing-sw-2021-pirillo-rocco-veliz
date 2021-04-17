package it.polimi.ingsw.exceptions;

public class InvalidLayerNumberException extends Throwable {

    //Default constructor with default message
    public InvalidLayerNumberException() {
        super("The requested depot's layer does not exist");
    }

    //Custom constructor to set a custom message
    public InvalidLayerNumberException(String customMessage) {
        super(customMessage);
    }
}
