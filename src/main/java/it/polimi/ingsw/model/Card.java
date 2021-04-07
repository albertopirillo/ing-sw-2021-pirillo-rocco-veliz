package it.polimi.ingsw.model;

import java.util.*;

public abstract class Card {

    private int victoryPoints;

    public Card(int victoryPoints) {
        this.victoryPoints = victoryPoints;
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }

}