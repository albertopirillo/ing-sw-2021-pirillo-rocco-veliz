package it.polimi.ingsw.model;

import java.util.*;

public class PopeFavor extends Card {

    private boolean faceUp;

    public PopeFavor(int victoryPoints, boolean faceUp) {
        super(victoryPoints);
        this.faceUp = faceUp;
    }

    public boolean isFaceUp() {
        return faceUp;
    }
}