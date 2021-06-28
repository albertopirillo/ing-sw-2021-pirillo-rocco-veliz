package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.InvalidKeyException;

import java.util.Collections;
import java.util.Deque;
import java.util.List;

/**
 * The market tray object, the market tray contains the marbles
 */
public class MarketTray {

    /**
     * The marbles' matrix
     */
    private final MarblesColor[][] marketMarbles;
    /**
     * The single marble on the slide
     */
    private MarblesColor remainingMarble;

    /**
     * Initialize the marbles' matrix randomly
     */
    @SuppressWarnings("unchecked")
    public MarketTray() {
        marketMarbles = new MarblesColor[3][4];
        Deque<MarblesColor> marbleList = Marbles.getAllMarbleList();
        Collections.shuffle((List<MarblesColor>) marbleList);
        remainingMarble = marbleList.poll();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                marketMarbles[i][j]= marbleList.poll();
            }
        }
    }

    //  PURPLE  PURPLE  YELLOW  YELLOW
    //  GREY  GREY  BLUE  BLUE
    //  WHITE  WHITE  WHITE  WHITE
    //  Remaining marble = RED
    public MarketTray(boolean noRandom) {
        marketMarbles = new MarblesColor[3][4];
        Deque<MarblesColor> marbleList = Marbles.getAllMarbleList();
        //Collections.shuffle((List<MarblesColor>) marbleList);
        remainingMarble = marbleList.poll();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                marketMarbles[i][j]= marbleList.poll();
            }
        }
    }

    /**
     * Inserts the marble from the slide on the top right corner of the market tray in the column/row passed in the parameter
     * @param position the column/row chosen by the player, from 0 to 3 for columns and from 4 to 6 for rows
     * @return returns a Marble Object that contains the marbles' map corresponding at the column/row chosen
     * @throws IllegalArgumentException if the position parameter is negative or more bigger than 6
     */
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

    /**
     * Returns a map from all twelve marbles
     * @return a Marbles Object that contains a map with all twelve marbles
     */
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

    public MarblesColor[][] getMarketMarbles() {
        return marketMarbles;
    }
    //only for testing
    public MarblesColor getMarble(int i, int j) { return marketMarbles[i][j]; }

    //only for testing
    public MarblesColor getRemainingMarble() { return remainingMarble; }

}
