package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.FullCardDeckException;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class MarketTest {

    /*@Test
    public void parserTest() throws FullCardDeckException {

        Market market = new Market();
        List<DevelopmentCard> devs = market.getAvailableCards();
        //remove null elements to test the deck empty, is optional
        //put 11 cards(of 12 cards) so one slot is empty -> so an element of list is null
        devs.removeIf(Objects::isNull);
        DevelopmentCard dev1 = new DevelopmentCard(new Resource(1,1,3,2), CardColor.GREEN, 3);
        DevelopmentCard dev2 = new DevelopmentCard(new Resource(4,4,2,1), CardColor.BLUE, 3);
        DevelopmentCard dev3 = new DevelopmentCard(new Resource(1,2,4,1), CardColor.YELLOW, 3);

        //System.out.println(devs.get(0));
    }*/

    /*@Test
    public void getAvailableCards() throws FullCardDeckException {
        List<DevelopmentCard> devCards = new ArrayList<>();
        devCards.add(new DevelopmentCard(new Resource(1,1,3,2), CardColor.GREEN, 3));
        devCards.add(new DevelopmentCard(new Resource(4,4,2,1), CardColor.BLUE, 3));
        devCards.add(new DevelopmentCard(new Resource(1,2,4,1), CardColor.YELLOW, 3));
        //devCards.add(new DevelopmentCard(new Resource(2,2,1,4), CardColor.PURPLE, 3));

        devCards.add(new DevelopmentCard(new Resource(3,4,3,2), CardColor.GREEN, 2));
        devCards.add(new DevelopmentCard(new Resource(2,2,2,1), CardColor.BLUE, 2));
        devCards.add(new DevelopmentCard(new Resource(2,1,3,4), CardColor.YELLOW, 2));
        devCards.add(new DevelopmentCard(new Resource(3,2,2,3), CardColor.PURPLE, 2));

        devCards.add(new DevelopmentCard(new Resource(4,2,1,2), CardColor.GREEN, 1));
        devCards.add(new DevelopmentCard(new Resource(1,1,3,2), CardColor.BLUE, 1));
        devCards.add(new DevelopmentCard(new Resource(3,1,3,4), CardColor.YELLOW, 1));
        devCards.add(new DevelopmentCard(new Resource(1,2,4,2), CardColor.PURPLE, 1));

        devCards.add(new DevelopmentCard(new Resource(4,2,1,1), CardColor.GREEN, 1));
        devCards.add(new DevelopmentCard(new Resource(1,1,3,1), CardColor.BLUE, 1));
        devCards.add(new DevelopmentCard(new Resource(3,1,3,1), CardColor.YELLOW, 1));
        devCards.add(new DevelopmentCard(new Resource(1,2,4,1), CardColor.PURPLE, 1));

        Market market = new Market(devCards);
        List<DevelopmentCard> devs = market.getAvailableCards();
        //remove null elements to test the deck empty, is optional
        //put 11 cards(of 12 cards) so one slot is empty -> so an element of list is null
        devs.removeIf(Objects::isNull);
        assertEquals(devs.size(), 11);
        assertEquals(devs.get(7), devCards.get(7));
        assertEquals(devs.get(8), devCards.get(8));
        assertEquals(devs.get(9), devCards.get(9));
        assertEquals(devs.get(10), devCards.get(10));
    }*/

    @Test
    public void buyCards() {
    }

    @Test
    void endgame() {
    }
}