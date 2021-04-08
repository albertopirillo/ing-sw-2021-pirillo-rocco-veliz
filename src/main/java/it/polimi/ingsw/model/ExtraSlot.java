package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.InvalidLayerNumberException;

import java.util.*;

public class ExtraSlot extends LeaderAbility {

    private ResourceType resource;

    public ExtraSlot(ResourceType resource) {
        this.resource = resource;
    }

    public void activate(Player player) {
        try {
            //to pass the resource type al depotDecorator
            new ConcreteDepotDecorator(player.getPersonalBoard().getDepot());
        } catch (InvalidLayerNumberException e) {
            e.printStackTrace();
        }
    }

    public ResourceType getResource() {
        return resource;
    }
}