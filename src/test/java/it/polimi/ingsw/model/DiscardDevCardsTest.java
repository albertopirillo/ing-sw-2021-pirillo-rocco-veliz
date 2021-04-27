package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.FullCardDeckException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DiscardDevCardsTest {

    @Test
    void revealTest() throws FullCardDeckException {
        SoloGame game = new SoloGame(new Player("a"));
        SoloActionToken token = new DiscardDevCards(game, CardColor.GREEN);
        Market market = game.getMarket();
        assertFalse(market.isDeckEmpty(1, CardColor.GREEN));
        token.reveal();
        assertFalse(market.isDeckEmpty(1, CardColor.GREEN));
        token.reveal();
        assertTrue(market.isDeckEmpty(1, CardColor.GREEN));
        assertFalse(market.isDeckEmpty(2, CardColor.GREEN));
        token.reveal();
        assertFalse(market.isDeckEmpty(2, CardColor.GREEN));
        token.reveal();
        assertTrue(market.isDeckEmpty(2, CardColor.GREEN));
        assertFalse(market.isDeckEmpty(3, CardColor.GREEN));
    }

    @Test
    public void countTest() throws FullCardDeckException {
        SoloGame game = new SoloGame(new Player("a"));
        SoloActionToken token = new DiscardDevCards(game, CardColor.BLUE);
        Market market = game.getMarket();
        assertEquals(12, market.getAvailableCards().size());
        token.reveal();
        assertEquals(12, market.getAvailableCards().size());
        token.reveal();
        assertTrue(market.isDeckEmpty(1, CardColor.BLUE));
        assertEquals(11, market.getAvailableCards().size());
    }

    @Test
    public void sideEffectsTest() throws FullCardDeckException {
        SoloGame game = new SoloGame(new Player("a"));
        SoloActionToken token = new DiscardDevCards(game, CardColor.YELLOW);
        Market market = game.getMarket();
        token.reveal();
        token.reveal();
        assertTrue(market.isDeckEmpty(1, CardColor.YELLOW));
        assertFalse(market.isDeckEmpty(1, CardColor.BLUE));
        assertFalse(market.isDeckEmpty(1, CardColor.GREEN));
        assertFalse(market.isDeckEmpty(1, CardColor.PURPLE));
    }
}