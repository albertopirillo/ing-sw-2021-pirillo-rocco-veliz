package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.InvalidKeyException;
import it.polimi.ingsw.exceptions.NegativeResAmountException;

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

    public abstract boolean canBeActivated(Player player) throws NegativeResAmountException, InvalidKeyException;
}