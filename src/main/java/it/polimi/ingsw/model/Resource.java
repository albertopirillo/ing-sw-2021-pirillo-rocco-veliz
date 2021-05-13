package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.InvalidKeyException;
import it.polimi.ingsw.exceptions.NegativeResAmountException;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Resource implements Serializable {

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

    public boolean hasAllResources(){
        return map.containsKey(ResourceType.ALL);
    }

    //Use this method only to add a new key (ResourceType)
    public void addResource(ResourceType key, int value) throws InvalidKeyException, NegativeResAmountException {
        if (value < 0) throw new NegativeResAmountException();
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
    public Map<ResourceType, Integer> getMap() {
        return new HashMap<>(map);
    }

    //Returns true if this has more resources than that
    public boolean compare (Resource that) {
        for (ResourceType key: that.map.keySet()) {
            if ((this.map.get(key) == null) || (this.map.get(key) < that.map.get(key))) return false;
        }
        return true;
    }

    //Sums two Resources and returns a new object
    public Resource sum(Resource that) {
        Map<ResourceType, Integer> copy = new HashMap<>(that.map);
        this.map.forEach((k,v) -> copy.merge(k, v, Integer::sum));
        return new Resource(copy);
    }

    //Get keySet of the current Map
    public Set<ResourceType> keySet() {
        return this.map.keySet();
    }

    //Return the total amount of resources contained in the map
    public int getTotalAmount() {
        int amount = 0;
        for(ResourceType key: map.keySet()) {
            amount += map.get(key);
        }
        return amount;
    }

    public void removeResource(ResourceType res) {
        this.map.remove(res);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Resource)) return false;
        Resource resource = (Resource) o;
        return map.equals(((Resource) o).map);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(" ");
        int i = 0;
        for(ResourceType key: this.map.keySet()) {
            if (this.map.get(key) != 0) {
                sb.append(key).append("x").append(this.map.get(key));
                if (i < this.map.size() - 1) sb.append(", ");
            }
            i++;
        }
        return sb.toString();
    }
}