package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.InvalidKeyException;
import it.polimi.ingsw.exceptions.NegativeResAmountException;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * The concept of Market Marbles taken by the player from the Market
 */
public class Marbles {

    /**
     * Map that indicates for ever marbles' color the corresponding amount
     */
    private final Map<MarblesColor, Integer> marbles;

    /**
     * Create a empty map of marbles
     */
    public Marbles() {
        marbles = new HashMap<>();
    }

    /**
     * Create a map from a filled resource map passed in the parameter
     * @param map a map of marbles' color with the corresponding amount
     */
    public Marbles(Map<MarblesColor, Integer> map) {
        marbles = new HashMap<>(map);
    }

    /**
     * Returns the amount of tha marble color(key) if exists in the map
     * @param key the marble color
     * @return return the amount of the marble color passed in the parameter
     * @throws InvalidKeyException if the map does not contain the marble color
     */
    public int getValue(MarblesColor key) throws InvalidKeyException {
        if (!marbles.containsKey(key)) throw new InvalidKeyException();
        return marbles.get(key);
    }

    /**
     * Returns a map filled with all marbles of the market tray
     * @return returns a map filled with all marbles of the market tray
     */
    public static Map<MarblesColor, Integer> getAllMarblesMap(){
        Map<MarblesColor, Integer> allMarbles = new HashMap<>();
        allMarbles.put(MarblesColor.WHITE, 4);
        allMarbles.put(MarblesColor.BLUE, 2);
        allMarbles.put(MarblesColor.GREY, 2);
        allMarbles.put(MarblesColor.YELLOW, 2);
        allMarbles.put(MarblesColor.PURPLE, 2);
        allMarbles.put(MarblesColor.RED, 1);
        return allMarbles;
    }

    /**
     * Returns a deque filled with all marbles of the market tray
     * @return returns a deque filled with all marbles of the market tray
     */
    public static Deque<MarblesColor> getAllMarbleList(){
        Deque<MarblesColor> allMarbles = new LinkedList<>();
        allMarbles.push(MarblesColor.WHITE);
        allMarbles.push(MarblesColor.WHITE);
        allMarbles.push(MarblesColor.WHITE);
        allMarbles.push(MarblesColor.WHITE);
        allMarbles.push(MarblesColor.BLUE);
        allMarbles.push(MarblesColor.BLUE);
        allMarbles.push(MarblesColor.GREY);
        allMarbles.push(MarblesColor.GREY);
        allMarbles.push(MarblesColor.YELLOW);
        allMarbles.push(MarblesColor.YELLOW);
        allMarbles.push(MarblesColor.PURPLE);
        allMarbles.push(MarblesColor.PURPLE);
        allMarbles.push(MarblesColor.RED);
        return allMarbles;
    }

    /**
     * Add the marble's color passed in the parameter to the map<br>
     * This method is called when the player takes one marble from the market tray<br>
     * If the map contains the marble color, the amount of that marble's color increases by one<br>
     * @param key color of the marble that the player takes from the market
     * @throws InvalidKeyException if the parameter is not present in the MarbleColor enum
     */
    public void add(MarblesColor key) throws InvalidKeyException {
        if (!MarblesColor.contains(key)) throw new InvalidKeyException();
        if (!marbles.containsKey(key)) {
            marbles.put(key, 1);
        } else {
            int newValue = marbles.get(key) + 1;
            marbles.put(key, newValue);
        }
    }

    /**
     * Return a resource object from a map of marble's color
     * @return  the resources corresponding of the map
     * @throws NegativeResAmountException if the marble's color is a element of Marble Color enum
     */
    public Resource getResources() throws NegativeResAmountException {
        Resource res = new Resource();
        for(MarblesColor marble : marbles.keySet()){
            if(marble != MarblesColor.WHITE)  res.addResource(marble.getResourceType() , marbles.get(marble));
        }
        return res;
    }

    public boolean equals(Marbles other){
        return marbles.equals(other.marbles);

    }

    /**
     * Returns true if the map contains a white marble
     * @return returns true if the map contains a white marble, false otherwise
     */
    public boolean containWhiteMarbles(){
        return marbles.containsKey(MarblesColor.WHITE);
    }

    /**
     * Returns the amount of white marbles the map contains
     * @return the amount of white marbles the map contains
     */
    public int numWhiteMarbles(){
        return marbles.get(MarblesColor.WHITE);
    }

    /**
     * Returns true if the map contains a red marble
     * @return true if the map contains a red marble, false otherwise
     */
    public boolean containFaith(){
        return marbles.containsKey(MarblesColor.RED);
    }

}
