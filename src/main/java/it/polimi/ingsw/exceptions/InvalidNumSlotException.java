package it.polimi.ingsw.exceptions;

public class InvalidNumSlotException extends Throwable {

    //Default constructor with default message
    public InvalidNumSlotException() {
        super("The slot number is invalid");
    }

}
