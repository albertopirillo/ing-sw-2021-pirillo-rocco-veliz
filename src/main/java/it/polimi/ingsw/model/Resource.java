package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.IllegalKeyException;
import it.polimi.ingsw.exceptions.NegativeResAmountException;

import java.util.*;

public class Resource {

    private final Map<ResourceType, Integer> map;

    //default constructor: creates an empty map
    public Resource() {
        map = new HashMap<>();
    }

    //creates a map with the basic resources, useful e.g. for depot
    public Resource(int stoneAmount, int coinAmount, int shieldAmount, int servantAmount) {
        map = new HashMap<>();
        map.put(ResourceType.STONE, stoneAmount);
        map.put(ResourceType.COIN, coinAmount);
        map.put(ResourceType.SHIELD, shieldAmount);
        map.put(ResourceType.SERVANT, servantAmount);
    }

    public int getValue(ResourceType key) throws IllegalKeyException {
        if (!map.containsKey(key)) throw new IllegalKeyException();
        return map.get(key);
    }

    public void setValue(ResourceType key, int value) throws IllegalKeyException, NegativeResAmountException {
        if (value < 0) throw new NegativeResAmountException();
        if (!ResourceType.contains(key)) throw new IllegalKeyException();
        map.put(key, value);
    }

    public void increaseValue(ResourceType key, int addend) throws IllegalKeyException{
        if (!map.containsKey(key)) throw new IllegalKeyException();
        int newValue = map.get(key) + addend;
        map.put(key, newValue);
    }

    public void decreaseValue(ResourceType key, int sub) throws IllegalKeyException, NegativeResAmountException {
        if (!map.containsKey(key)) throw new IllegalKeyException();
        int newValue = map.get(key) - sub;
        if (newValue < 0) throw new NegativeResAmountException();
        map.put(key, newValue);
    }
}