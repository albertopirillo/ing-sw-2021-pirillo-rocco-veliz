package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.NegativeResAmountException;

import java.io.Serializable;

/**
 * One of the two types of Leader Cards
 * Each ResLeaderCard has a Resource object associated to the card's requirements and a Leader Ability that can be activated
 */
public class ResLeaderCard extends LeaderCard implements Serializable {

    /**
     * The resources required to activate the leader card
     */
    private final Resource cost;

    /**
     * Create a ResLeaderCard
     * @param victoryPoints the victory points that each card has
     * @param specialAbility the special ability that each leader card had
     * @param cost the resources required to activate the leader card
     */
    public ResLeaderCard(int victoryPoints, LeaderAbility specialAbility, Resource cost) {
        super(victoryPoints, specialAbility);
        this.cost = cost;
    }

    public boolean canBeActivated(Player player) throws NegativeResAmountException {
        Resource playerRes = player.getAllResources();
        Resource tempStrongbox = player.getPersonalBoard().getStrongbox().queryAllTempRes();
        return (playerRes.sum(tempStrongbox)).compare(this.cost);
    }

    public String toString(){
        return "\tCost: " + cost.toString() +
                "\n\tAbility" +
                getSpecialAbility().toString() +
                "\n\tVictoryPoints: " + getVictoryPoints();
    }
}