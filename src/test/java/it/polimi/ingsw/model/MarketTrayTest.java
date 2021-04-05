package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.InvalidKeyException;
//import org.junit.Test;

//import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

public class MarketTrayTest {

    @Test
    public void initMarketTray() throws InvalidKeyException {
        MarketTray marketTray = new MarketTray();
        Marbles initMap = new Marbles(Marbles.getAllMarblesMap());
        Marbles actualMap = marketTray.getMarblesMap();
        assertTrue(actualMap.equals(initMap));
        for(int i=0; i<3; i++){
            for (int j=0; j<4; j++){
                assertTrue(MarblesColor.contains(marketTray.getMarble(i,j)));
            }
        }
        assertTrue(MarblesColor.contains(marketTray.getRemainingMarble()));
    }

    @Test
    public void insertMarble() throws InvalidKeyException {
        MarketTray marketTray = new MarketTray();
        Marbles mar = new Marbles();
        mar.add(marketTray.getMarble(0, 1));
        mar.add(marketTray.getMarble(1, 1));
        mar.add(marketTray.getMarble(2, 1));
        Marbles mar1 = marketTray.insertMarble(1);
        assertTrue(mar1.equals(mar));
        //testing method private update
        Marbles initMap = new Marbles(Marbles.getAllMarblesMap());
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
    public void getMarblesMap() throws InvalidKeyException {
        MarketTray marketTray = new MarketTray();
        Marbles initMap = new Marbles(Marbles.getAllMarblesMap());
        assertTrue(initMap.equals(marketTray.getMarblesMap()));
    }

}