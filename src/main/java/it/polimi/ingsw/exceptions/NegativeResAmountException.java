package it.polimi.ingsw.exceptions;

public class NegativeResAmountException extends Exception {

    //Default constructor with default message
    public NegativeResAmountException() {
        super("Resource amount can't be negative ");
    }

    //Custom constructor to set a custom message
    public NegativeResAmountException(String customMessage) {
        super(customMessage);
    }
}
