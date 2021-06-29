package it.polimi.ingsw.exceptions;

public class LeaderAbilityAlreadyActive extends Throwable {

    //Custom constructor to set a custom message
    public LeaderAbilityAlreadyActive(String customMessage) {
        super(customMessage);
    }
}
