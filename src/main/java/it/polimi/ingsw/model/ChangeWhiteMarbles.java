package it.polimi.ingsw.model;

public class ChangeWhiteMarbles extends LeaderAbility {

    private final ResourceType resourceType;

    public ChangeWhiteMarbles(ResourceType resType) {
        this.resourceType = resType;
    }

    public ResourceType getResourceType() {
        return this.resourceType;
    }

}