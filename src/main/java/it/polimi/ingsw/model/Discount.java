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

    public void activate(Player player) throws TooManyLeaderAbilitiesException {
        player.addDevCardsStrategy(this);
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("\n\t\tType: ").append("DISCOUNT");
        sb.append("\n\t\tResource: ").append(resource.toString());
        return sb.toString();
    }


}