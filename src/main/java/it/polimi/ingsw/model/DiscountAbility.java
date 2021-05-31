package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.TooManyLeaderAbilitiesException;

import java.io.Serializable;

/**
 * Leader ability that gives the player a discount when he buys from the market
 */
public class DiscountAbility extends LeaderAbility implements Serializable {

    private final ResourceType resource;
    private final int amount;

    /**
     * Constructs a new object
     * @param resource the resource to apply the discount to
     * @param amount the value of the discount
     */
    public DiscountAbility(ResourceType resource, int amount) {
        this.resource = resource;
        this.amount = amount;
    }

    /**
     * Gets the resource to apply the discount to
     * @return the resource type
     */
    public ResourceType getResource() {
        return resource;
    }

    /**
     * Gets the value of the discount
     * @return an int representing the value
     */
    public int getAmount() {
        return amount;
    }

    @Override
    public void activate(Player player) throws TooManyLeaderAbilitiesException {
        player.addDevCardsStrategy(this);
    }

    @Override
    public String toString(){
        return "\n\t\tType: " + "DISCOUNT" +
                "\n\t\tResource: " + resource.toString();
    }
}