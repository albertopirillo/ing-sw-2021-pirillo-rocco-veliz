package it.polimi.ingsw.model;

import java.io.Serializable;

public abstract class Card implements Serializable {

    private final int victoryPoints;

    public Card(int victoryPoints) {
        this.victoryPoints = victoryPoints;
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }

}