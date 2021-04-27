package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.FullCardDeckException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MoveAndShuffleTest {

    @Test
    public void revealTest() throws FullCardDeckException {
        SoloGame game = new SoloGame(new Player("a"));
        SoloActionToken token = new MoveAndShuffle(game);
        assertEquals(0, game.getBlackCrossPosition());
        token.reveal();
        assertEquals(1, game.getBlackCrossPosition());
        token.reveal();
        assertEquals(2, game.getBlackCrossPosition());
    }

}