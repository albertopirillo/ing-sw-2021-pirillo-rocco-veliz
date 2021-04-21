package it.polimi.ingsw.model;

public class LeaderDevCost {

    private final CardColor color;
    private int level;
    private final int amount;

    public LeaderDevCost(CardColor color, int level, int amount) {
        this.color = color;
        this.level = level;
        this.amount = amount;
    }

    public LeaderDevCost(CardColor color, int amount) {
        this.color = color;
        this.amount = amount;
    }

    public CardColor getColor() {
        return color;
    }

    public int getLevel() {
        return level;
    }

    public int getAmount() {
        return amount;
    }

}