package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.InvalidKeyException;
import it.polimi.ingsw.exceptions.NegativeResAmountException;

import java.io.Serializable;

public class ResLeaderCard extends LeaderCard implements Serializable {

    private final Resource cost;

    //json initialization
    public ResLeaderCard(int id, String img, int victoryPoints, LeaderAbility specialAbility, Resource cost) {
        super(id, img, victoryPoints, specialAbility);
        this.cost = cost;
    }

    public ResLeaderCard(int victoryPoints, LeaderAbility specialAbility, Resource cost) {
        super(victoryPoints, specialAbility);
        this.cost = cost;
    }

    @Override
    public LeaderCardType getLeaderCardType() {
        return LeaderCardType.RES;
    }

    public boolean canBeActivated(Player player) throws NegativeResAmountException, InvalidKeyException {
        return player.getAllResources().compare(cost);
    }

    public Resource getCost() {
        return cost;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("\tCost: ").append(cost.toString());
        sb.append("\n\tAbility");
        sb.append(getSpecialAbility().toString());
        sb.append("\n\tVictoryPoints: ").append(getVictoryPoints());
        return sb.toString();
    }
}