package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.NotEnoughResException;

import java.util.*;

public class DevLeaderCard extends LeaderCard {

    private CardColor color;
    private int level;
    private int amount;

    public DevLeaderCard(int victoryPoints, LeaderAbility specialAbility, CardColor color, int level, int amount) {
        super(victoryPoints, specialAbility);
        this.color = color;
        this.level = level;
        this.amount = amount;
    }

    public boolean canBeActivated(List<DevelopmentCard> playerCards) {
        return playerCards.stream().filter(e -> e.getType() == color && e.getLevel() == level).count() >= amount;

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