package it.polimi.ingsw.model;

public class ChangeWhiteMarbles extends LeaderAbility {

    private final ResourceType resourceType;

    public void activate(Player player) { player.changeResourceStrategy(new ResourceStrategy(this)); }

    public ChangeWhiteMarbles(ResourceType resType) {
        this.resourceType = resType;
    }

    public ResourceType getResourceType() {
        return this.resourceType;
    }

}