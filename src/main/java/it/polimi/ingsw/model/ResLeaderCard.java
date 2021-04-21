package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.InvalidKeyException;
import it.polimi.ingsw.exceptions.NegativeResAmountException;

public class ResLeaderCard extends LeaderCard {

    private final Resource cost;

    //json initialization
    public ResLeaderCard(int victoryPoints, LeaderAbility specialAbility, Resource cost) {
        super(victoryPoints, specialAbility);
        this.cost = cost;
    }

    public boolean canBeActivated(Player player) throws NegativeResAmountException, InvalidKeyException {
        return player.getAllResources().compare(cost);
    }

    public Resource getCost() {
        return cost;
    }
}