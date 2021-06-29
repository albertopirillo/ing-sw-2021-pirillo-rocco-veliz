package it.polimi.ingsw.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.exceptions.DeckEmptyException;
import it.polimi.ingsw.exceptions.FullCardDeckException;
import org.junit.jupiter.api.Test;

import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class MarketTest {

    @Test
    public void parserTest() {

        Type listType = new TypeToken<ArrayList<DevelopmentCard>>(){}.getType();
        Reader reader = new InputStreamReader(Objects.requireNonNull(getClass().getResourceAsStream("/json/DevCardsConfig.json")));
        List<DevelopmentCard> devs = new Gson().fromJson(reader, listType);

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
            assertEquals(market.getCard(i, CardColor.GREEN), devCards.get(k));
            k++;
            assertEquals(market.getCard(i, CardColor.BLUE), devCards.get(k));
            k++;
            assertEquals(market.getCard(i, CardColor.YELLOW), devCards.get(k));
            k++;
            assertEquals(market.getCard(i, CardColor.PURPLE), devCards.get(k));
            k++;
        }
    }

    @Test
    public void buyCards() throws DeckEmptyException, FullCardDeckException {
        Market market = new Market();
        List<DevelopmentCard> devCards = market.getAvailableCards();
        assertEquals(devCards.get(0), market.buyCards(3, CardColor.GREEN));
        assertNotEquals(devCards.get(0), market.buyCards(3, CardColor.GREEN));
        devCards = market.getAvailableCards();
        assertEquals(devCards.get(0), market.buyCards(3, CardColor.GREEN));
        assertNotEquals(devCards.get(0), market.buyCards(3, CardColor.GREEN));
        assertThrows(DeckEmptyException.class, () -> market.buyCards(3, CardColor.GREEN));
        assertThrows(DeckEmptyException.class, () -> market.getCard(3, CardColor.GREEN));
    }

}