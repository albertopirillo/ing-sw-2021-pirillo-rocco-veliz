package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.NotEnoughResException;

public class ResLeaderCard extends LeaderCard {

    private Resource cost;

    //json initialization
    public ResLeaderCard(int victoryPoints, LeaderAbility specialAbility, Resource cost) {
        super(victoryPoints, specialAbility);
        this.cost = cost;
    }

    public boolean canBeActivated(Resource playerResource) throws NotEnoughResException {
        if(playerResource == null)  throw new NotEnoughResException();
        return playerResource.compare(this.cost);
    }

    public Resource getCost() {
        return cost;
    }

}