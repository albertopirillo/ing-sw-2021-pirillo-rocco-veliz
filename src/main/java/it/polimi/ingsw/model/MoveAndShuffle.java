package it.polimi.ingsw.model;

public class MoveAndShuffle extends SoloActionToken {

    public MoveAndShuffle(SoloGame game) {
        super(game);
    }

    @Override
    public void reveal() {
        SoloGame game = this.getGame();
        game.moveBlackCross(1);
        game.shuffleSoloTokens();
    }
}