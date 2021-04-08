package it.polimi.ingsw.model;

public class Discount extends LeaderAbility {

    private final ResourceType resource;
    private final int amount;

    public Discount(ResourceType resource, int amount) {
        this.resource = resource;
        this.amount = amount;
    }

    public void activate(Player player) {
        player.changeDevCardsStrategy(new DevCardsStrategy(this));
    }

    public ResourceType getResource() {
        return resource;
    }

    public int getAmount() {
        return amount;
    }
}