package it.polimi.ingsw.model;

import java.util.*;

public class Discount extends LeaderAbility {

    private final ResourceType resource;

    private final int amount;

    public Discount(ResourceType resource, int amount) {
        this.resource = resource;
        this.amount = amount;
    }

    public ResourceType getResource() {
        return resource;
    }

    public int getAmount() {
        return amount;
    }
}