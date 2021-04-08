package it.polimi.ingsw.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import it.polimi.ingsw.exceptions.DeckEmptyException;
import it.polimi.ingsw.exceptions.FullCardDeckException;
import it.polimi.ingsw.exceptions.InvalidKeyException;
import it.polimi.ingsw.exceptions.NegativeResAmountException;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class MarketTest {

    @Test
    public void parserTest() throws FullCardDeckException, NegativeResAmountException, InvalidKeyException, FileNotFoundException {

        Type listType = new TypeToken<ArrayList<DevelopmentCard>>(){}.getType();
        List<DevelopmentCard> devs = new Gson().fromJson(new JsonReader(new FileReader("src/main/resources/devCardsConfig.json")), listType);
        //parser JSON
        /*devs.add(new DevelopmentCard(1, new Resource().addResource(ResourceType.SHIELD, 2),
                CardColor.GREEN, 1, new ProductionPower(
                        new Resource().addResource(ResourceType.COIN,1),
                new Resource().addResource(ResourceType.FAITH, 1))));
        devs.add(new DevelopmentCard(3, new Resource().addResource(ResourceType.SERVANT, 3),
                CardColor.PURPLE, 1, new ProductionPower(
                new Resource().addResource(ResourceType.COIN,1),
                new Resource(1,0,1,1))));
        devs.add(new DevelopmentCard(3, new Resource().addResource(ResourceType.COIN, 3),
                CardColor.BLUE, 1, new ProductionPower(
                new Resource().addResource(ResourceType.STONE,2),
                new Resource(0,1,1,1))));
        devs.add(new DevelopmentCard(3, new Resource().addResource(ResourceType.STONE, 3),
                CardColor.YELLOW, 1, new ProductionPower(
                new Resource().addResource(ResourceType.SHIELD,2),
                new Resource(1,1,0,1))));
        devs.add(new DevelopmentCard(4, new Resource(0,2,2,0),
                CardColor.GREEN, 1, new ProductionPower(
                new Resource(1,0,0,1),
                new Resource(0,2,0,0).addResource(ResourceType.FAITH,1))));
        devs.add(new DevelopmentCard(4, new Resource(2,0,0,2),
                CardColor.PURPLE, 1, new ProductionPower(
                new Resource(0,1,1,0),
                new Resource().addResource(ResourceType.STONE,2).addResource(ResourceType.FAITH,1))));
        devs.add(new DevelopmentCard(4, new Resource(0,2,0,2),
                CardColor.BLUE, 1, new ProductionPower(
                new Resource(1,0,1,0),
                new Resource().addResource(ResourceType.SERVANT,2).addResource(ResourceType.FAITH,1))));
        devs.add(new DevelopmentCard(4, new Resource(2,0,2,0),
                CardColor.YELLOW, 1, new ProductionPower(
                new Resource(0,1,0,1),
                new Resource().addResource(ResourceType.SHIELD,2).addResource(ResourceType.FAITH,1))));
        devs.add(new DevelopmentCard(5, new Resource(0,0,4,0),
                CardColor.GREEN, 2, new ProductionPower(
                new Resource(1,0,0,0),
                new Resource().addResource(ResourceType.FAITH,2))));
        devs.add(new DevelopmentCard(5, new Resource(0,0,0,4),
                CardColor.PURPLE, 2, new ProductionPower(
                new Resource(0,1,0,0),
                new Resource().addResource(ResourceType.FAITH,2))));
        devs.add(new DevelopmentCard(5, new Resource(0,4,0,0),
                CardColor.BLUE, 2, new ProductionPower(
                new Resource(0,0,0,1),
                new Resource().addResource(ResourceType.FAITH,2))));
        devs.add(new DevelopmentCard(1, new Resource(0,0,0,2),
                CardColor.PURPLE, 1, new ProductionPower(
                new Resource(1,0,0,0),
                new Resource().addResource(ResourceType.FAITH,1))));
        devs.add(new DevelopmentCard(5, new Resource(4,0,0,0),
                CardColor.YELLOW, 2, new ProductionPower(
                new Resource(0,0,1,0),
                new Resource().addResource(ResourceType.FAITH,2))));
        devs.add(new DevelopmentCard(6, new Resource(0,0,3,2),
                CardColor.GREEN, 2, new ProductionPower(
                new Resource(0,0,1,1),
                new Resource().addResource(ResourceType.STONE,3))));
        devs.add(new DevelopmentCard(6, new Resource(0,2,0,3),
                CardColor.PURPLE, 2, new ProductionPower(
                new Resource(0,1,0,1),
                new Resource().addResource(ResourceType.SHIELD,3))));
        devs.add(new DevelopmentCard(6, new Resource(2,3,0,0),
                CardColor.BLUE, 2, new ProductionPower(
                new Resource(1,1,0,0),
                new Resource().addResource(ResourceType.SERVANT,3))));
        devs.add(new DevelopmentCard(6, new Resource(3,0,2,0),
                CardColor.YELLOW, 2, new ProductionPower(
                new Resource(1,0,1,0),
                new Resource().addResource(ResourceType.COIN,3))));
        devs.add(new DevelopmentCard(7, new Resource(0,0,5,0),
                CardColor.GREEN, 2, new ProductionPower(
                new Resource(0,2,0,0),
                new Resource().addResource(ResourceType.STONE,2).addResource(ResourceType.FAITH,2))));
        devs.add(new DevelopmentCard(7, new Resource(0,0,0,5),
                CardColor.PURPLE, 2, new ProductionPower(
                new Resource(2,0,0,0),
                new Resource().addResource(ResourceType.COIN,2).addResource(ResourceType.FAITH,2))));
        devs.add(new DevelopmentCard(7, new Resource(0,5,0,0),
                CardColor.BLUE, 2, new ProductionPower(
                new Resource(0,0,0,2),
                new Resource().addResource(ResourceType.SHIELD,2).addResource(ResourceType.FAITH,2))));
        devs.add(new DevelopmentCard(7, new Resource(5,0,0,0),
                CardColor.YELLOW, 2, new ProductionPower(
                new Resource(0,0,2,0),
                new Resource().addResource(ResourceType.SERVANT,2).addResource(ResourceType.FAITH,2))));
        devs.add(new DevelopmentCard(8, new Resource(0,3,3,0),
                CardColor.GREEN, 2, new ProductionPower(
                new Resource(0,1,0,0),
                new Resource().addResource(ResourceType.SHIELD,2).addResource(ResourceType.FAITH,1))));
        devs.add(new DevelopmentCard(1, new Resource(0,2,0,0),
                CardColor.BLUE, 1, new ProductionPower(
                new Resource(0,0,1,0),
                new Resource().addResource(ResourceType.FAITH,1))));
        devs.add(new DevelopmentCard(8, new Resource(0,0,3,3),
                CardColor.PURPLE, 2, new ProductionPower(
                new Resource(1,0,0,0),
                new Resource().addResource(ResourceType.SERVANT,2).addResource(ResourceType.FAITH,1))));
        devs.add(new DevelopmentCard(8, new Resource(3,3,0,0),
                CardColor.BLUE, 2, new ProductionPower(
                new Resource(0,0,0,1),
                new Resource().addResource(ResourceType.STONE,2).addResource(ResourceType.FAITH,1))));
        devs.add(new DevelopmentCard(8, new Resource(3,0,0,3),
                CardColor.YELLOW, 2, new ProductionPower(
                new Resource(0,0,1,0),
                new Resource().addResource(ResourceType.COIN,2).addResource(ResourceType.FAITH,1))));
        devs.add(new DevelopmentCard(9, new Resource(0,0,6,0),
                CardColor.GREEN, 3, new ProductionPower(
                new Resource(0,2,0,0),
                new Resource().addResource(ResourceType.STONE,3).addResource(ResourceType.FAITH,2))));
        devs.add(new DevelopmentCard(9, new Resource(0,0,0,6),
                CardColor.PURPLE, 3, new ProductionPower(
                new Resource(2,0,0,0),
                new Resource().addResource(ResourceType.COIN,3).addResource(ResourceType.FAITH,2))));
        devs.add(new DevelopmentCard(9, new Resource(0,6,0,0),
                CardColor.BLUE, 3, new ProductionPower(
                new Resource(0,0,0,2),
                new Resource().addResource(ResourceType.SHIELD,3).addResource(ResourceType.FAITH,2))));
        devs.add(new DevelopmentCard(9, new Resource(6,0,0,0),
                CardColor.YELLOW, 3, new ProductionPower(
                new Resource(0,0,2,0),
                new Resource().addResource(ResourceType.SERVANT,3).addResource(ResourceType.FAITH,2))));
        devs.add(new DevelopmentCard(10, new Resource(0,0,5,2),
                CardColor.GREEN, 3, new ProductionPower(
                new Resource(0,1,0,1),
                new Resource(2,0,2,0).addResource(ResourceType.FAITH,1))));
        devs.add(new DevelopmentCard(10, new Resource(0,2,0,5),
                CardColor.PURPLE, 3, new ProductionPower(
                new Resource(1,0,1,0),
                new Resource(0,2,0,2).addResource(ResourceType.FAITH,1))));
        devs.add(new DevelopmentCard(10, new Resource(2,5,0,0),
                CardColor.BLUE, 3, new ProductionPower(
                new Resource(0,1,1,0),
                new Resource(2,0,0,2).addResource(ResourceType.FAITH,1))));
        devs.add(new DevelopmentCard(1, new Resource(2,0,0,0),
                CardColor.YELLOW, 1, new ProductionPower(
                new Resource(0,0,0,1),
                new Resource().addResource(ResourceType.FAITH,1))));
        devs.add(new DevelopmentCard(10, new Resource(5,0,0,2),
                CardColor.YELLOW, 3, new ProductionPower(
                new Resource(1,0,0,1),
                new Resource(0,2,2,0).addResource(ResourceType.FAITH,1))));
        devs.add(new DevelopmentCard(11, new Resource(0,0,7,0),
                CardColor.GREEN, 3, new ProductionPower(
                new Resource(0,0,0,1),
                new Resource().addResource(ResourceType.COIN,1).addResource(ResourceType.FAITH,1))));
        devs.add(new DevelopmentCard(11, new Resource(0,0,0,7),
                CardColor.PURPLE, 3, new ProductionPower(
                new Resource(0,1,0,0),
                new Resource().addResource(ResourceType.STONE,1).addResource(ResourceType.FAITH,3))));
        devs.add(new DevelopmentCard(11, new Resource(0,7,0,0),
                CardColor.BLUE, 3, new ProductionPower(
                new Resource(1,0,0,0),
                new Resource().addResource(ResourceType.SHIELD,1).addResource(ResourceType.FAITH,3))));
        devs.add(new DevelopmentCard(11, new Resource(7,0,0,0),
                CardColor.YELLOW, 3, new ProductionPower(
                new Resource(0,0,1,0),
                new Resource().addResource(ResourceType.SERVANT,1).addResource(ResourceType.FAITH,3))));
        devs.add(new DevelopmentCard(12, new Resource(0,4,4,0),
                CardColor.GREEN, 3, new ProductionPower(
                new Resource(1,0,0,0),
                new Resource().addResource(ResourceType.COIN,3).addResource(ResourceType.SHIELD,1))));
        devs.add(new DevelopmentCard(12, new Resource(0,0,4,4),
                CardColor.PURPLE, 3, new ProductionPower(
                new Resource(0,1,0,0),
                new Resource().addResource(ResourceType.STONE,3).addResource(ResourceType.SERVANT,1))));
        devs.add(new DevelopmentCard(12, new Resource(4,4,0,0),
                CardColor.BLUE, 3, new ProductionPower(
                new Resource(0,0,0,1),
                new Resource().addResource(ResourceType.COIN,1).addResource(ResourceType.SHIELD,3))));
        devs.add(new DevelopmentCard(12, new Resource(4,0,0,4),
                CardColor.YELLOW, 3, new ProductionPower(
                new Resource(0,0,1,0),
                new Resource().addResource(ResourceType.SERVANT,3).addResource(ResourceType.STONE,1))));
        devs.add(new DevelopmentCard(2, new Resource(1,0,1,1),
                CardColor.GREEN, 1, new ProductionPower(
                new Resource(1,0,0,0),
                new Resource().addResource(ResourceType.SERVANT,1))));
        devs.add(new DevelopmentCard(2, new Resource(0,1,1,1),
                CardColor.PURPLE, 1, new ProductionPower(
                new Resource(0,1,0,0),
                new Resource().addResource(ResourceType.SHIELD,1))));
        devs.add(new DevelopmentCard(2, new Resource(1,1,0,1),
                CardColor.BLUE, 1, new ProductionPower(
                new Resource(0,0,0,1),
                new Resource().addResource(ResourceType.STONE,1))));
        devs.add(new DevelopmentCard(2, new Resource(1,1,1,0),
                CardColor.YELLOW, 1, new ProductionPower(
                new Resource(0,0,1,0),
                new Resource().addResource(ResourceType.COIN,1))));
        devs.add(new DevelopmentCard(3, new Resource(0,0,3,0),
                CardColor.GREEN, 1, new ProductionPower(
                new Resource(0,0,0,2),
                new Resource(1,1,1,0))));*/

        assertEquals(48, devs.size());
        //test total cards
        assertEquals(16, devs.stream().filter(e -> e.getLevel()==1).count());
        assertEquals(16, devs.stream().filter(e -> e.getLevel()==2).count());
        assertEquals(16, devs.stream().filter(e -> e.getLevel()==3).count());
        assertEquals(12, devs.stream().filter(e -> e.getType()==CardColor.BLUE).count());
        assertEquals(12, devs.stream().filter(e -> e.getType()==CardColor.GREEN).count());
        assertEquals(12, devs.stream().filter(e -> e.getType()==CardColor.PURPLE).count());
        assertEquals(12, devs.stream().filter(e -> e.getType()==CardColor.YELLOW).count());
        //test cardDeck
        assertEquals(4, devs.stream().filter(e -> e.getLevel()==3 && e.getType()==CardColor.GREEN).count());
        assertEquals(4, devs.stream().filter(e -> e.getLevel()==3 && e.getType()==CardColor.BLUE).count());
        assertEquals(4, devs.stream().filter(e -> e.getLevel()==3 && e.getType()==CardColor.YELLOW).count());
        assertEquals(4, devs.stream().filter(e -> e.getLevel()==3 && e.getType()==CardColor.PURPLE).count());
        assertEquals(4, devs.stream().filter(e -> e.getLevel()==2 && e.getType()==CardColor.GREEN).count());
        assertEquals(4, devs.stream().filter(e -> e.getLevel()==2 && e.getType()==CardColor.BLUE).count());
        assertEquals(4, devs.stream().filter(e -> e.getLevel()==2 && e.getType()==CardColor.YELLOW).count());
        assertEquals(4, devs.stream().filter(e -> e.getLevel()==2 && e.getType()==CardColor.PURPLE).count());
        assertEquals(4, devs.stream().filter(e -> e.getLevel()==1 && e.getType()==CardColor.GREEN).count());
        assertEquals(4, devs.stream().filter(e -> e.getLevel()==1 && e.getType()==CardColor.BLUE).count());
        assertEquals(4, devs.stream().filter(e -> e.getLevel()==1 && e.getType()==CardColor.YELLOW).count());
        assertEquals(4, devs.stream().filter(e -> e.getLevel()==1 && e.getType()==CardColor.PURPLE).count());

    }

    @Test
    public void getAvailableCards() throws FullCardDeckException {

        Market market = new Market();
        List<DevelopmentCard> devCards = market.getAvailableCards();
        assertEquals(devCards.size(), 12);
        assertEquals(1, devCards.stream().filter(e -> e.getLevel()==3 && e.getType()==CardColor.GREEN).count());
        assertEquals(1, devCards.stream().filter(e -> e.getLevel()==3 && e.getType()==CardColor.BLUE).count());
        assertEquals(1, devCards.stream().filter(e -> e.getLevel()==3 && e.getType()==CardColor.YELLOW).count());
        assertEquals(1, devCards.stream().filter(e -> e.getLevel()==3 && e.getType()==CardColor.PURPLE).count());
        assertEquals(1, devCards.stream().filter(e -> e.getLevel()==2 && e.getType()==CardColor.GREEN).count());
        assertEquals(1, devCards.stream().filter(e -> e.getLevel()==2 && e.getType()==CardColor.BLUE).count());
        assertEquals(1, devCards.stream().filter(e -> e.getLevel()==2 && e.getType()==CardColor.YELLOW).count());
        assertEquals(1, devCards.stream().filter(e -> e.getLevel()==2 && e.getType()==CardColor.PURPLE).count());
        assertEquals(1, devCards.stream().filter(e -> e.getLevel()==1 && e.getType()==CardColor.GREEN).count());
        assertEquals(1, devCards.stream().filter(e -> e.getLevel()==1 && e.getType()==CardColor.BLUE).count());
        assertEquals(1, devCards.stream().filter(e -> e.getLevel()==1 && e.getType()==CardColor.YELLOW).count());
        assertEquals(1, devCards.stream().filter(e -> e.getLevel()==1 && e.getType()==CardColor.PURPLE).count());
    }

    @Test
    public void getCard() throws DeckEmptyException, FullCardDeckException {
        Market market = new Market();
        List<DevelopmentCard> devCards = market.getAvailableCards();
        int k=0;
        for(int i=3; i>0; i--){
            assertTrue(market.getCard(i,CardColor.GREEN).equals(devCards.get(k)));
            k++;
            assertTrue(market.getCard(i,CardColor.BLUE).equals(devCards.get(k)));
            k++;
            assertTrue(market.getCard(i,CardColor.YELLOW).equals(devCards.get(k)));
            k++;
            assertTrue(market.getCard(i,CardColor.PURPLE).equals(devCards.get(k)));
            k++;
        }
    }

    @Test
    public void buyCards() throws DeckEmptyException, FullCardDeckException {
        Market market = new Market();
        List<DevelopmentCard> devCards = market.getAvailableCards();
        assertTrue(devCards.get(0).equals(market.buyCards(3, CardColor.GREEN)));
        assertFalse(devCards.get(0).equals(market.buyCards(3, CardColor.GREEN)));
        devCards = market.getAvailableCards();
        assertTrue(devCards.get(0).equals(market.buyCards(3, CardColor.GREEN)));
        assertFalse(devCards.get(0).equals(market.buyCards(3, CardColor.GREEN)));
        assertThrows(DeckEmptyException.class, () -> market.buyCards(3, CardColor.GREEN));
        assertThrows(DeckEmptyException.class, () -> market.getCard(3, CardColor.GREEN));
    }

    @Test
    void endgame() {
    }
}