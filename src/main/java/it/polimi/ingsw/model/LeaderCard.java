package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.NotEnoughResException;

import java.util.*;

public abstract class LeaderCard extends Card {

    private final LeaderAbility specialAbility;

    private boolean isActive;

    //json initialization
    public LeaderCard(int victoryPoints, LeaderAbility specialAbility) {
        super(victoryPoints);
        this.isActive = false;
        this.specialAbility = specialAbility;
    }

    public boolean isActive() {
        return this.isActive;
    }

    public void activate() {
        this.isActive = true;
    }

    public LeaderAbility getSpecialAbility() {
        return specialAbility;
    }

    /*Inherited from superclass
    public int getVictoryPoints() {
        return super.getVictoryPoints();
    }*/

    public boolean canBeActivated(List<DevelopmentCard> playerCards) {
        return false;
    }
    public boolean canBeActivated(Resource playerResource) throws NotEnoughResException {
        return true;
    }
}