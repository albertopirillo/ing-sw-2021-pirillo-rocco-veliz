package it.polimi.ingsw.exceptions;

public class DevSlotEmptyException extends Throwable {

    //Default constructor with default message
    public DevSlotEmptyException() {
        super("The slot is empty or the slot number is invalid");
    }

    //Custom constructor to set a custom message
    public DevSlotEmptyException(String customMessage) {
        super(customMessage);
    }
}
