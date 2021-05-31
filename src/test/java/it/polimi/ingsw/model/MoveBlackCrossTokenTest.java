package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.FullCardDeckException;
import it.polimi.ingsw.exceptions.InvalidKeyException;
import it.polimi.ingsw.exceptions.NegativeResAmountException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MoveBlackCrossTokenTest {

    @Test
    void reveal() throws FullCardDeckException, NegativeResAmountException, InvalidKeyException {
        SoloGame game = new SoloGame(new Player("a"));
        SoloActionToken token = new MoveBlackCrossToken(game);
        assertEquals(0, game.getBlackCrossPosition());
        token.reveal();
        assertEquals(2, game.getBlackCrossPosition());
        token.reveal();
        assertEquals(4, game.getBlackCrossPosition());
    }
}