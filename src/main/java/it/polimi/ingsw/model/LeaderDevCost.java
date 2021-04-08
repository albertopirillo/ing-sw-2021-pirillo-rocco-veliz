package it.polimi.ingsw.model;

import java.util.*;

public class LeaderDevCost {

    private CardColor color;
    private int level;
    private int amount;

    public LeaderDevCost(CardColor color, int level, int amount) {
        this.color = color;
        this.level = level;
        this.amount = amount;
    }
    public LeaderDevCost(CardColor color, int amount) {
        this.color = color;
        this.level = amount;
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