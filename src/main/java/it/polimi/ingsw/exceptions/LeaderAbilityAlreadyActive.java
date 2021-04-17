package it.polimi.ingsw.exceptions;

public class LeaderAbilityAlreadyActive extends Throwable {

    //Default constructor with default message
    public LeaderAbilityAlreadyActive() {
        super("The selected leader ability has already been activated");
    }

    //Custom constructor to set a custom message
    public LeaderAbilityAlreadyActive(String customMessage) {
        super(customMessage);
    }
}
