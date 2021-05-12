package it.polimi.ingsw.model;

public class MoveBlackCross extends SoloActionToken {

    public MoveBlackCross(SoloGame game) {
        super(game);
    }

    @Override
    public void reveal() {
        System.out.println("[SOLO_ACTION_TOKEN] Move black cross +2");
        this.getGame().moveBlackCross(2);
    }

    @Override
    public String toString() {
        return "Move the black cross by 2 spaces";
    }
}