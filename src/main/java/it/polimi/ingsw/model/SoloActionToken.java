package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.InvalidKeyException;
import it.polimi.ingsw.exceptions.NegativeResAmountException;

public abstract class SoloActionToken {

    private final SoloGame game;

    public SoloActionToken(SoloGame game) {
        this.game = game;
    }

    public SoloGame getGame() {
        return game;
    }

    public abstract void reveal() throws NegativeResAmountException, InvalidKeyException;
}