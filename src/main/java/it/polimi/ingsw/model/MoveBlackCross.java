package it.polimi.ingsw.model;

public class MoveBlackCross extends SoloActionToken {

    public MoveBlackCross(SoloGame game) {
        super(game);
    }

    @Override
    public void reveal() {
        this.getGame().moveBlackCross(2);
    }
}