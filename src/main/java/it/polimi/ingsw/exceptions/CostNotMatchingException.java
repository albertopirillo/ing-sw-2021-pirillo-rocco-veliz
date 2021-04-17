package it.polimi.ingsw.exceptions;

public class CostNotMatchingException extends Throwable {

    //Default constructor with default message
    public CostNotMatchingException() {
        super("You did not provide enough resources to complete the action");
    }

    //Custom constructor to set a custom message
    public CostNotMatchingException(String customMessage) {
        super(customMessage);
    }
}
