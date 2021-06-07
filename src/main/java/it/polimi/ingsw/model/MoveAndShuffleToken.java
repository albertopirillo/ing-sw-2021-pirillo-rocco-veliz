package it.polimi.ingsw.model;

/**
 * Action Token implementation that moves the black cross by 1 space and shuffles all the tokens
 */
public class MoveAndShuffleToken extends SoloActionToken {

    /**
     * Constructs a new MoveAndShuffle Action Token
     * @param game the associated game
     */
    public MoveAndShuffleToken(SoloGame game) {
        super(game);
    }

    @Override
    public void reveal() {
        //System.out.println("[SOLO_ACTION_TOKEN] Move black cross +1 and shuffle");
        SoloGame game = this.getGame();
        game.moveBlackCross(1);
        game.shuffleSoloTokens();
    }

    @Override
    public String toString() {
        return "Move the black cross by 1 space and shuffle the tokens";
    }

    @Override
    public String getID() {
        return "move&shuffle";
    }
}