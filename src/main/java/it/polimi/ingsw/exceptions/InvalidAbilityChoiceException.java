package it.polimi.ingsw.exceptions;

public class InvalidAbilityChoiceException extends Throwable {

    //Default constructor with default message
    public InvalidAbilityChoiceException() {
        super("You selected an invalid leader ability");
    }

}
