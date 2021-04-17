package it.polimi.ingsw.exceptions;

public class NoLeaderAbilitiesException extends Throwable {

    //Default constructor with default message
    public NoLeaderAbilitiesException() {
        super("The player has no leader ability of that type already active ");
    }

    //Custom constructor to set a custom message
    public NoLeaderAbilitiesException(String customMessage) {
        super(customMessage);
    }
}
