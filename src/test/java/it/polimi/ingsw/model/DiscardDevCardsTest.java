package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.FullCardDeckException;
import it.polimi.ingsw.exceptions.InvalidKeyException;
import it.polimi.ingsw.exceptions.NegativeResAmountException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DiscardDevCardsTest {

    @Test
    void revealTest() throws FullCardDeckException, NegativeResAmountException, InvalidKeyException {
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
    public void countTest() throws FullCardDeckException, NegativeResAmountException, InvalidKeyException {
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
    public void sideEffectsTest() throws FullCardDeckException, NegativeResAmountException, InvalidKeyException {
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

    @Test
    public void discardButLoseTest() throws FullCardDeckException, NegativeResAmountException, InvalidKeyException {
        SoloGame game = new SoloGame(new Player("a"));
        SoloActionToken token1 = new DiscardDevCards(game, CardColor.PURPLE);
        SoloActionToken token2 = new DiscardDevCards(game, CardColor.PURPLE);

        //Discard level 1 cards
        token1.reveal();
        token2.reveal();
        //Discard level 2 cards
        token2.reveal();
        token1.reveal();
        //Discard two level 3 cards
        token1.reveal();
        //Discard last two level3, game is over
        token2.reveal();
    }
}