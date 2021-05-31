package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.TooManyLeaderAbilitiesException;

import java.io.Serializable;

/**
 * LeaderAbility that transforms white marbles into marbles of another color
 */
public class ChangeWhiteMarblesAbility extends LeaderAbility implements Serializable {

    private final ResourceType resourceType;

    /**
     * Constructs a new object
     * @param resType the resource type to transform the marbles into
     */
    public ChangeWhiteMarblesAbility(ResourceType resType) {
        this.resourceType = resType;
    }

    /**
     * Gets the resource type to transform the marbles into
     * @return the resource type
     */
    public ResourceType getResourceType() {
        return this.resourceType;
    }

    public void activate(Player player) throws TooManyLeaderAbilitiesException {
        player.addResourceStrategy(this);
    }

    @Override
    public String toString(){
        return "\n\t\tType: " + "CHANGE" +
                "\n\t\tResource: " + resourceType.toString();
    }
}