package it.polimi.ingsw.exceptions;

public class NotEnoughResException extends Throwable {

    //Default constructor with default message
    public NotEnoughResException() {
        super("The player hasn't got enough Resource to complete the action");
    }

    //Custom constructor to set a custom message
    public NotEnoughResException(String customMessage) {
        super(customMessage);
    }
}
