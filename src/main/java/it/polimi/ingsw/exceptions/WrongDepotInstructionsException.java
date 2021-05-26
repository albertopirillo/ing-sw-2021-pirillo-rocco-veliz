package it.polimi.ingsw.exceptions;

public class WrongDepotInstructionsException extends Throwable {

    //Default constructor with default message
    public WrongDepotInstructionsException() {
        super("The player provided instruction do not handle the correct amount of resources");
    }

    //Custom constructor to set a custom message
    public WrongDepotInstructionsException(String customMessage) {
        super(customMessage);
    }

}
