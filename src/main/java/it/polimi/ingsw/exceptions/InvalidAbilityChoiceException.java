package it.polimi.ingsw.exceptions;

public class InvalidAbilityChoiceException extends Throwable {

    //Default constructor with default message
    public InvalidAbilityChoiceException() {
        super("You selected an invalid leader ability");
    }

    //Custom constructor to set a custom message
    public InvalidAbilityChoiceException(String customMessage) {
        super(customMessage);
    }
}
