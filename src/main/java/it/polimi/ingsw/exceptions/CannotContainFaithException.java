package it.polimi.ingsw.exceptions;

public class CannotContainFaithException extends Throwable {

    //Default constructor with default message
    public CannotContainFaithException() {
        super("The depot cannot contain Faith Resource type");
    }

    //Custom constructor to set a custom message
    public CannotContainFaithException(String customMessage) {
        super(customMessage);
    }
}
