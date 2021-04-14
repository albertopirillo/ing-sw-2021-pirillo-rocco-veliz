package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.InvalidLayerNumberException;

public class ExtraSlot extends LeaderAbility {

    private final ResourceType resource;

    public ExtraSlot(ResourceType resource) {
        this.resource = resource;
    }

    public ResourceType getResource() {
        return resource;
    }

    public void activate(Player player) throws InvalidLayerNumberException {
            Depot oldDepot = player.getPersonalBoard().getDepot();
            player.getPersonalBoard().upgradeDepot(new ConcreteDepotDecorator(oldDepot, this));
    }
}