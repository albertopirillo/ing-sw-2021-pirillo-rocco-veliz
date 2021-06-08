package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.NegativeResAmountException;

import java.io.Serializable;

public class ResLeaderCard extends LeaderCard implements Serializable {

    private final Resource cost;

    public ResLeaderCard(int victoryPoints, LeaderAbility specialAbility, Resource cost) {
        super(victoryPoints, specialAbility);
        this.cost = cost;
    }

    public boolean canBeActivated(Player player) throws NegativeResAmountException {
        return player.getAllResources().compare(this.cost);
    }

    public String toString(){
        return "\tCost: " + cost.toString() +
                "\n\tAbility" +
                getSpecialAbility().toString() +
                "\n\tVictoryPoints: " + getVictoryPoints();
    }
}