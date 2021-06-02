package it.polimi.ingsw.model;

/**
 * Action Token implementation that moves the black cross by 2 spaces
 */
public class MoveBlackCrossToken extends SoloActionToken {

    /**
     * Constructs a new MoveBlackCross Action Token
     * @param game the associated game
     */
    public MoveBlackCrossToken(SoloGame game) {
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

    @Override
    public String getID() {
        return "move2";
    }
}