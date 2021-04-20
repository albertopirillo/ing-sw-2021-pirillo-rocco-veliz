package it.polimi.ingsw.exceptions;

public class InvalidNumSlotException extends Throwable {

    //Default constructor with default message
    public InvalidNumSlotException() {
        super("The slot number is invalid");
    }

    //Custom constructor to set a custom message
    public InvalidNumSlotException(String customMessage) {
        super(customMessage);
    }

}
