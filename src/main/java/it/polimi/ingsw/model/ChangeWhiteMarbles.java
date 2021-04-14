package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.TooManyLeaderAbilitiesException;

public class ChangeWhiteMarbles extends LeaderAbility {

    private final ResourceType resourceType;

    public ChangeWhiteMarbles(ResourceType resType) {
        this.resourceType = resType;
    }

    public ResourceType getResourceType() {
        return this.resourceType;
    }

    public void activate(Player player) throws TooManyLeaderAbilitiesException {
        player.addResourceStrategy(this);
    }

}