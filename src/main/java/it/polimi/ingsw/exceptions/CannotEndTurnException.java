package it.polimi.ingsw.exceptions;

public class CannotEndTurnException extends Throwable {

    //Custom constructor to set a custom message
    public CannotEndTurnException(String customMessage) {
        super(customMessage);
    }
}

