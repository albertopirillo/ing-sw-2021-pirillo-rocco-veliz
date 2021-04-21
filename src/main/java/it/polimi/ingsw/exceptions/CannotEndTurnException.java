package it.polimi.ingsw.exceptions;

public class CannotEndTurnException extends Throwable {

    //Default constructor with default message
    public CannotEndTurnException() {
        super("You cannot end your turn yet");
    }

    //Custom constructor to set a custom message
    public CannotEndTurnException(String customMessage) {
        super(customMessage);
    }
}

