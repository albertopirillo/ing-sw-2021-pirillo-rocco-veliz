package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.InvalidLayerNumberException;

import java.io.Serializable;

/**
 * Leader ability that gives the player another slot in the depot
 */
public class ExtraSlotAbility extends LeaderAbility implements Serializable {

    private final ResourceType resource;

    /**
     * Constructs a new object
     * @param resource the resource type that can be inserted in that slot
     */
    public ExtraSlotAbility(ResourceType resource) {
        this.resource = resource;
    }

    /**
     * Gets the resource that can be inserted in the new slot
     * @return the corresponding resource type
     */
    public ResourceType getResource() {
        return resource;
    }

    /**
     * Activates the leader ability, executing its effects
     * @param player the player that activated the ability
     * @throws InvalidLayerNumberException if the player already has 5 layers in the depot
     */
    @Override
    public void activate(Player player) throws InvalidLayerNumberException {
        Depot oldDepot = player.getPersonalBoard().getDepot();
        player.getPersonalBoard().upgradeDepot(new ConcreteDepotDecorator(oldDepot, this));
    }

    @Override
    public String toString(){
        return "\n\t\tType: " + "EXTRA SLOT" +
                "\n\t\tResource: " + resource.toString();
    }
}