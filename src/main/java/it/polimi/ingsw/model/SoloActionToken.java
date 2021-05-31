package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.InvalidKeyException;
import it.polimi.ingsw.exceptions.NegativeResAmountException;

import java.io.Serializable;

public abstract class SoloActionToken implements Serializable {

    private transient final SoloGame game;

    public SoloActionToken(SoloGame game) {
        this.game = game;
    }

    public SoloGame getGame() {
        return game;
    }

    public abstract void reveal() throws NegativeResAmountException, InvalidKeyException;

    public abstract String getID();
}