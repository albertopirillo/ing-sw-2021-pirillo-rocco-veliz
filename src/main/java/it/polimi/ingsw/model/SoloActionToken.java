package it.polimi.ingsw.model;

public abstract class SoloActionToken {

    private final SoloGame game;

    public SoloActionToken(SoloGame game) {
        this.game = game;
    }

    public SoloGame getGame() {
        return game;
    }

    public abstract void reveal();
}