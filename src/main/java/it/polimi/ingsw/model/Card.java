package it.polimi.ingsw.model;

import java.io.Serializable;

/**
 * <p>The most generic concept of Card</p>
 * <p>There are two card's type: Leader Card and Development Card</p>
 */
public abstract class Card implements Serializable {

    /**
     * The card's victory points
     * <p>All cards have victory points</p>
     */
    private final int victoryPoints;

    public Card(int victoryPoints) {
        this.victoryPoints = victoryPoints;
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }

}