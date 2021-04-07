package it.polimi.ingsw.model;

import java.util.*;

public class ExtraSlot extends LeaderAbility {

    private ResourceType resource;

    public ExtraSlot(ResourceType resource) {
        this.resource = resource;
    }

    public ExtraSlot activate() {
        return this;
    }

    public ResourceType getResource() {
        return resource;
    }
}