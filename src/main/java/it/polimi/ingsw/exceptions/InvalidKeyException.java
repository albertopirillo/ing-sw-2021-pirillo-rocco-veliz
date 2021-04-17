package it.polimi.ingsw.exceptions;

public class InvalidKeyException extends Exception {

    //Default constructor with default message
    public InvalidKeyException() {
        super("The requested value does not exist");
    }

    //Custom constructor to set a custom message
    public InvalidKeyException(String customMessage) {
        super(customMessage);
    }
}
