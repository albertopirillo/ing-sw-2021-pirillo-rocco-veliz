package it.polimi.ingsw.model;

public abstract class Card {

    private final int victoryPoints;

    public Card(int victoryPoints) {
        this.victoryPoints = victoryPoints;
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }

}