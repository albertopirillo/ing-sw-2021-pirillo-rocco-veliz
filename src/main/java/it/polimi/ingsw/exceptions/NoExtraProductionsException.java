package it.polimi.ingsw.exceptions;

public class NoExtraProductionsException extends Throwable {

    //Default constructor with default message
    public NoExtraProductionsException() {
        super("The player has no extra production ability active yet");
    }

    //Custom constructor to set a custom message
    public NoExtraProductionsException(String customMessage) {
        super(customMessage);
    }
}
