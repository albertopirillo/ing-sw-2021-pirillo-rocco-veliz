package it.polimi.ingsw.model;

public class MoveAndShuffle extends SoloActionToken {

    public MoveAndShuffle(SoloGame game) {
        super(game);
    }

    @Override
    public void reveal() {
        System.out.println("[SOLO_ACTION_TOKEN] Move black cross +1 and shuffle");
        SoloGame game = this.getGame();
        game.moveBlackCross(1);
        game.shuffleSoloTokens();
    }
}