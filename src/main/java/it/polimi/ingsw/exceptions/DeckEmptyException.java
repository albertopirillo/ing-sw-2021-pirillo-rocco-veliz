package it.polimi.ingsw.exceptions;

public class DeckEmptyException extends Throwable {

    //Default constructor with default message
    public DeckEmptyException() {
        super("The deck is empty");
    }

    //Custom constructor to set a custom message
    public DeckEmptyException(String customMessage) {
        super(customMessage);
    }
}
