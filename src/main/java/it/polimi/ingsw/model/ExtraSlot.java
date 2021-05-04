package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.InvalidLayerNumberException;

import java.io.Serializable;

public class ExtraSlot extends LeaderAbility implements Serializable {

    private final ResourceType resource;

    public ExtraSlot(ResourceType resource) {
        this.resource = resource;
    }

    public ResourceType getResource() {
        return resource;
    }

    @Override
    public LeaderAbilityType getLeaderAbilityType() {
        return LeaderAbilityType.EXTRA;
    }

    public void activate(Player player) throws InvalidLayerNumberException {
            Depot oldDepot = player.getPersonalBoard().getDepot();
            player.getPersonalBoard().upgradeDepot(new ConcreteDepotDecorator(oldDepot, this));
    }
}