package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.IllegalKeyException;
import it.polimi.ingsw.exceptions.NegativeResourceAmountException;

import java.util.*;

public class Resource {

    private final Map<ResourceType, Integer> map = new HashMap<>();

    public Resource() {
        for (ResourceType res: ResourceType.values()) {
            map.put(res, 0);
        }
    }

    public int getValue(ResourceType key) throws IllegalKeyException {
        if (!map.containsKey(key)) throw new IllegalKeyException();
        return map.get(key);
    }

    public void setValue(ResourceType key, int value) throws IllegalKeyException, NegativeResourceAmountException {
        if (value < 0) throw new NegativeResourceAmountException();
        if (!map.containsKey(key)) throw new IllegalKeyException();
        map.put(key, value);
    }

    public void increaseValue(ResourceType key, int addend) throws IllegalKeyException, NegativeResourceAmountException {
        if (addend < 0) throw new NegativeResourceAmountException();
        if (!map.containsKey(key)) throw new IllegalKeyException();
        map.put(key, map.get(key) + addend);
    }
}