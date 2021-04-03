package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.InvalidKeyException;
import it.polimi.ingsw.exceptions.NegativeResAmountException;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.util.*;

public class Resource {

    private final Map<ResourceType, Integer> map;

    //default constructor: creates an empty map
    public Resource() {
        map = new HashMap<>();
    }

    //creates a Resource object from a given map
    public Resource(Map<ResourceType, Integer> map) {
        this.map = new HashMap<>(map);
    }

    //creates a map with the basic resources, useful e.g. for depot
    public Resource(int stoneAmount, int coinAmount, int shieldAmount, int servantAmount) {
        map = new HashMap<>();
        map.put(ResourceType.STONE, stoneAmount);
        map.put(ResourceType.COIN, coinAmount);
        map.put(ResourceType.SHIELD, shieldAmount);
        map.put(ResourceType.SERVANT, servantAmount);
    }

    public int getValue(ResourceType key) throws InvalidKeyException {
        if (!map.containsKey(key)) throw new InvalidKeyException();
        return map.get(key);
    }

    //Use this method only to add a new key (ResourceType)
    public void addResource(ResourceType key, int value) throws InvalidKeyException, NegativeResAmountException {
        if (value < 0) throw new NegativeResAmountException();
        if (!ResourceType.contains(key)) throw new InvalidKeyException();
        if (map.containsKey(key)) throw new KeyAlreadyExistsException();
        map.put(key, value);
    }

    //Use this method both to add or subtract to an already existing resource
    public void modifyValue(ResourceType key, int value) throws InvalidKeyException, NegativeResAmountException {
        if (!map.containsKey(key)) throw new InvalidKeyException();
        int newValue = map.get(key) + value;
        if (newValue < 0) throw new NegativeResAmountException();
        map.put(key, newValue);
    }

    //Returns a copy of this.map field (it's a new object and not a reference)
    public Map<ResourceType, Integer> getAllRes() {
        return new HashMap<>(map);
    }

    //Returns true if this has more resources than that
    public boolean compare (Resource that) {
        for (ResourceType key: that.map.keySet()) {
            if ((this.map.get(key) == null) || (this.map.get(key) < that.map.get(key))) return false;
        }
        return true;
    }
}