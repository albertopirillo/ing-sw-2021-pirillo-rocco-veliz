package it.polimi.ingsw.model;

import java.util.*;

public abstract class LeaderCard extends Card {

    private final LeaderAbility specialAbility;

    //json initialization
    public LeaderCard(int victoryPoints, LeaderAbility specialAbility) {
        super(victoryPoints);
        this.specialAbility = specialAbility;
    }

    public LeaderAbility getSpecialAbility() {
        return specialAbility;
    }

    /*Inherited from superclass
    public int getVictoryPoints() {
        return super.getVictoryPoints();
    }*/
}