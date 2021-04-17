package it.polimi.ingsw.exceptions;

public class InvalidResourceException extends Throwable {

    //Default constructor with default message
    public InvalidResourceException() {
        super("The selected resource cannot be inserted into that layer");
    }

    //Custom constructor to set a custom message
    public InvalidResourceException(String customMessage) {
        super(customMessage);
    }
}
