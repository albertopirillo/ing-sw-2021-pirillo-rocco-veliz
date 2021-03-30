package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.IllegalKeyException;

import java.util.*;

public class MarketTray {

    private MarblesColor[][] marketMarbles;
    private MarblesColor remainingMarble;
    private Map<MarblesColor, Integer> initMarket;


    public MarketTray() {
        marketMarbles = new MarblesColor[3][4];
        initMarket = new HashMap<>();
        initMarketTray();
        Random randomNum = new Random();
        int len = MarblesColor.values().length;
        int pick = randomNum.nextInt(len);
        MarblesColor[] init = MarblesColor.values();
        remainingMarble = init[pick];
        initMarket.put(init[pick], initMarket.get(init[pick]) - 1);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                pick = randomNum.nextInt(len);
                while (initMarket.get(init[pick]) <= 0) {
                    pick = randomNum.nextInt(len);
                }
                marketMarbles[i][j] = init[pick];
                initMarket.put(init[pick], initMarket.get(init[pick]) - 1);
            }
        }
        initMarketTray();
    }

    //create a hash map with the inital numbers of marbles
    private void initMarketTray(){
        initMarket.put(MarblesColor.WHITE, 4);
        initMarket.put(MarblesColor.BLUE, 2);
        initMarket.put(MarblesColor.GREY, 2);
        initMarket.put(MarblesColor.YELLOW, 2);
        initMarket.put(MarblesColor.PURPLE, 2);
        initMarket.put(MarblesColor.RED, 1);

    }

    //position  from 0 to 3 for columns and 4 to 6 for rows
    //return Marbles Object, similar to Resource
    public Marbles insertMarble(int position) throws IllegalArgumentException, IllegalKeyException {
        Marbles res = new Marbles();
        if(position < 0 || position > 6) throw new IllegalArgumentException();
        if(position < 4){
            for(int i=0; i<3; i++) {
                res.add(marketMarbles[i][position]);
            }
        }else{
            for(int i=0; i<4; i++) {
                res.add(marketMarbles[6-position][i]);
            }
        }
        updateMarbles(position);
        return res;
    }

    //position  from 0 to 3 for columns and 4 to 6 for rows, update the marbles matrix
    private void updateMarbles(int position) {
        MarblesColor tmp;
        if(position < 4){
            tmp = marketMarbles[0][position];
            for(int i=0; i<2; i++) {
                marketMarbles[i][position] = marketMarbles[i+1][position];
            }
            marketMarbles[2][position] = remainingMarble;
            remainingMarble = tmp;
        }else{
            tmp = marketMarbles[6-position][0];
            for(int i=0; i<3; i++) {
                marketMarbles[6-position][i] = marketMarbles[6-position][i+1];
            }
            marketMarbles[6-position][3] = remainingMarble;
            remainingMarble = tmp;
        }
    }
    //only for testing
    public Marbles getMarblesMap() throws IllegalKeyException {
        Marbles mar = new Marbles();
        for (int i=0; i<3; i++){
            for (int j=0; j<4; j++){
               mar.add(marketMarbles[i][j]);
            }
        }
        mar.add(remainingMarble);
        return mar;
    }
    //only for testing
    public Map<MarblesColor, Integer> getInitMarket() {
        return initMarket;
    }

    //only for testing
    public MarblesColor getMarble(int i, int j) { return marketMarbles[i][j]; }

    //only for testing
    public MarblesColor getRemainingMarble() { return remainingMarble; }
}