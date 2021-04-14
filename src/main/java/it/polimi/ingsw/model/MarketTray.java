package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.InvalidKeyException;

import java.util.*;

public class MarketTray {

    private final MarblesColor[][] marketMarbles;
    private MarblesColor remainingMarble;

    public MarketTray() {
        marketMarbles = new MarblesColor[3][4];
        Deque<MarblesColor> marbleList = Marbles.getAllMarbleList(Marbles.getAllMarblesMap());
        Collections.shuffle((List<MarblesColor>) marbleList);
        remainingMarble = marbleList.poll();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                marketMarbles[i][j]= marbleList.poll();
            }
        }
    }

    //position  from 0 to 3 for columns and 4 to 6 for rows
    //return Marbles Object, similar to Resource
    public Marbles insertMarble(int position) throws IllegalArgumentException, InvalidKeyException {
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
        }else{
            tmp = marketMarbles[6-position][0];
            for(int i=0; i<3; i++) {
                marketMarbles[6-position][i] = marketMarbles[6-position][i+1];
            }
            marketMarbles[6-position][3] = remainingMarble;
        }
        remainingMarble = tmp;
    }
    //only for testing
    public Marbles getMarblesMap() throws InvalidKeyException {
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
    public MarblesColor getMarble(int i, int j) { return marketMarbles[i][j]; }

    //only for testing
    public MarblesColor getRemainingMarble() { return remainingMarble; }
}