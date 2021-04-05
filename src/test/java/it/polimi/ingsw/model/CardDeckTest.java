package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.DeckEmptyException;
import it.polimi.ingsw.exceptions.FullCardDeckException;
import it.polimi.ingsw.exceptions.InvalidKeyException;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.Deque;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class CardDeckTest {

    @Test
    public void addCard() throws FullCardDeckException {
        CardDeck deck = new CardDeck();
        assertEquals(deck.getNumbersOfCards(), 0);
        //stub
        deck.addCard(new DevelopmentCard(new Resource(), CardColor.BLUE, 1));
        deck.addCard(new DevelopmentCard(new Resource(), CardColor.GREEN, 2));
        deck.addCard(new DevelopmentCard(new Resource(), CardColor.YELLOW, 3));
        assertEquals(deck.getNumbersOfCards(), 3);
    }

    @RepeatedTest(10)
    public void shuffleTest() throws FullCardDeckException {
        CardDeck deck = new CardDeck();
        //stub
        Resource res1 = new Resource(1,2,3,4);
        Resource res2 = new Resource(4,3,2,1);
        Resource res3 = new Resource(2,4,1,3);
        DevelopmentCard dev1 = new DevelopmentCard(res1, CardColor.BLUE, 2);
        DevelopmentCard dev2 = new DevelopmentCard(res2, CardColor.BLUE, 2);
        DevelopmentCard dev3 = new DevelopmentCard(res3, CardColor.BLUE, 2);
        deck.addCard(dev1);
        deck.addCard(dev2);
        deck.addCard(dev3);
        assertEquals(deck.getNumbersOfCards(), 3);
        DevelopmentCard devCard = deck.getCard();
        assertEquals(deck.getNumbersOfCards(), 2);
        DevelopmentCard devCard1 = deck.getCard();
        assertEquals(deck.getNumbersOfCards(), 1);
        boolean bool = devCard.equals(dev2) || devCard.equals(dev3) || devCard1.equals(dev3);
        assertTrue(bool);
    }

    @Test
    public void LevelTest() throws FullCardDeckException, DeckEmptyException {
        CardDeck deck = new CardDeck();
        assertThrows(DeckEmptyException.class, () -> deck.getLevel());
        DevelopmentCard devCard = new DevelopmentCard(new Resource(), CardColor.BLUE, 2);
        deck.addCard(devCard);
        assertEquals(deck.getLevel(), 2);
    }

    @Test
    public void getCard() throws FullCardDeckException {
        CardDeck deck = new CardDeck();
        assertNull(deck.getCard());
        Resource res = new Resource(1,2,3,4);
        deck.addCard(new DevelopmentCard( res, CardColor.BLUE, 1));
        DevelopmentCard card = deck.getCard();
        //return the card
        assertEquals(card.getType(), CardColor.BLUE);
        assertEquals(card.getLevel(), 1);
        assertTrue(card.getResource().equals(res.getAllRes()));
        //remove the card
        assertEquals(deck.getNumbersOfCards(),0);
    }

    @Test
    public void emptyTest() throws DeckEmptyException, FullCardDeckException {
        CardDeck deck = new CardDeck();
        assertNull(deck.getCard());//return null
        Resource res1 = new Resource(1,2,3,4);
        Resource res2 = new Resource(4,3,2,1);
        Resource res3 = new Resource(2,4,1,3);
        DevelopmentCard dev1 = new DevelopmentCard(res1, CardColor.BLUE, 2);
        DevelopmentCard dev2 = new DevelopmentCard(res2, CardColor.BLUE, 2);
        DevelopmentCard dev3 = new DevelopmentCard(res3, CardColor.BLUE, 2);
        deck.addCard(dev1);
        deck.addCard(dev2);
        deck.addCard(dev3);
        deck.getCard();
        deck.getCard();
        deck.getCard();
        assertNull(deck.getCard());//return null
    }

}