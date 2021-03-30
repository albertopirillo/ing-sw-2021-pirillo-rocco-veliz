package it.polimi.ingsw;

import it.polimi.ingsw.exceptions.IllegalKeyException;
//import org.junit.Test;

//import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.*;

import it.polimi.ingsw.model.Marbles;
import it.polimi.ingsw.model.MarblesColor;
import it.polimi.ingsw.model.MarketTray;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

public class MarketTrayTest {

    @Test
    public void initMarketTray() throws IllegalKeyException {
        MarketTray marketTray = new MarketTray();
        Marbles initMap = new Marbles();
        initMap.addAll(marketTray.getInitMarket());
        Marbles actualMap = marketTray.getMarblesMap();
        assertTrue(actualMap.equals(initMap));
        for(int i=0; i<3; i++){
            for (int j=0; j<4; j++){
                Assertions.assertTrue(MarblesColor.contains(marketTray.getMarble(i,j)));
            }
        }
        assertTrue(MarblesColor.contains(marketTray.getRemainingMarble()));
    }

    @Test
    public void insertMarble() throws IllegalKeyException {
        MarketTray marketTray = new MarketTray();
        Marbles mar = new Marbles();
        mar.add(marketTray.getMarble(0, 1));
        mar.add(marketTray.getMarble(1, 1));
        mar.add(marketTray.getMarble(2, 1));
        Marbles mar1 = marketTray.insertMarble(1);
        assertTrue(mar1.equals(mar));
        //testing method private update
        Marbles initMap = new Marbles();
        initMap.addAll(marketTray.getInitMarket());
        mar = marketTray.getMarblesMap();
        assertTrue(initMap.equals(mar));
        for(int i=0; i<3; i++){
            for (int j=0; j<4; j++){
                assertTrue(MarblesColor.contains(marketTray.getMarble(i,j)));
            }
        }
        assertTrue(MarblesColor.contains(marketTray.getRemainingMarble()));
    }

    @RepeatedTest(10)
    public void getMarblesMap() throws IllegalKeyException {
        MarketTray marketTray = new MarketTray();
        Marbles mar = new Marbles();
        mar.addAll(marketTray.getInitMarket());
        assertTrue(mar.equals(marketTray.getMarblesMap()));
    }
}