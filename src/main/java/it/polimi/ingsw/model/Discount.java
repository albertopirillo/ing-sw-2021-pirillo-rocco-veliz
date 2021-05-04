package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.TooManyLeaderAbilitiesException;

import java.io.Serializable;

public class Discount extends LeaderAbility implements Serializable {

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

    @Override
    public LeaderAbilityType getLeaderAbilityType() {
        return LeaderAbilityType.DISCOUNT;
    }

    public void activate(Player player) throws TooManyLeaderAbilitiesException {
        player.addDevCardsStrategy(this);
    }

}