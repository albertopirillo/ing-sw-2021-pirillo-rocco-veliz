package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.FullCardDeckException;
import it.polimi.ingsw.exceptions.NegativeResAmountException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class SoloGameTest {

    @Test
    public void BuildTest() throws FullCardDeckException {
        SoloGame game = new SoloGame(new Player("a"));
        assertEquals(0, game.getBlackCrossPosition());
        assertEquals(6, game.getSoloTokens().size());
    }

    @Test
    public void shuffleSoloTokens() throws FullCardDeckException {
        SoloGame game = new SoloGame(new Player("a"));
        List<SoloActionToken> before = game.getSoloTokens();
        assertEquals(before, game.getSoloTokens());
        game.shuffleSoloTokens();
        List<SoloActionToken> after = game.getSoloTokens();
        assertNotEquals(before, after);
    }

    @Test
    public void nextTurn() throws FullCardDeckException, NegativeResAmountException {
        SoloGame game = new SoloGame(true);
        SoloActionToken firstToken = game.getSoloTokens().get(0);
        SoloActionToken secondToken = game.getSoloTokens().get(1);
        game.nextTurn();
        assertEquals(firstToken, game.getSoloTokens().get(5));
        assertEquals(secondToken, game.getSoloTokens().get(0));
    }

    @Test
    public void lastTurnTest() {
        //TODO: implement here
    }
}