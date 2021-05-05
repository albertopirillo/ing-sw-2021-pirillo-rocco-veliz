package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.TooManyLeaderAbilitiesException;

import java.io.Serializable;

public class ChangeWhiteMarbles extends LeaderAbility implements Serializable {

    private final ResourceType resourceType;

    public ChangeWhiteMarbles(ResourceType resType) {
        this.resourceType = resType;
    }

    public ResourceType getResourceType() {
        return this.resourceType;
    }

    @Override
    public LeaderAbilityType getLeaderAbilityType() {
        return LeaderAbilityType.CHANGE;
    }

    public void activate(Player player) throws TooManyLeaderAbilitiesException {
        player.addResourceStrategy(this);
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("\n\t\tType: ").append(getLeaderAbilityType().toString());
        sb.append("\n\t\tResource: ").append(resourceType.toString());
        return sb.toString();
    }

}