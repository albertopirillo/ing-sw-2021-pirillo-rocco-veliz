package it.polimi.ingsw.exceptions;

public class TooManyLeaderAbilitiesException extends Throwable {

    //Default constructor with default message
    public TooManyLeaderAbilitiesException() {
        super("The player cannot have more then two leader abilities active");
    }

    //Custom constructor to set a custom message
    public TooManyLeaderAbilitiesException(String customMessage) {
        super(customMessage);
    }
}
