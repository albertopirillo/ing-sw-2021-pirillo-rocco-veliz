package it.polimi.ingsw.model;

import java.io.Serializable;

/**
 * One type of Leader card's requirements
 * A specified amount of specified type of Development cards that the player must have
 */
public class LeaderDevCost implements Serializable {

    /**
     * Card color of the cards that the player must have
     */
    private final CardColor color;
    /**
     * Card level of the cards that the player must have
     */
    private int level;
    /**
     * The amount of cards with the above color and level that the player must have
     */
    private final int amount;

    /**
     * Create a LeaderDevCost object
     * @param color the cards' color
     * @param level the cards' level
     * @param amount the amount of cards
     */
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