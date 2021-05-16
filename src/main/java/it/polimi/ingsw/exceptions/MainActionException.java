package it.polimi.ingsw.exceptions;

public class MainActionException extends Throwable {

    //Default constructor with default message
    public MainActionException() {
        super("You already performed an action this turn");
    }

    //Custom constructor to set a custom message
    public MainActionException(String customMessage) {
        super(customMessage);
    }
}
