package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.InvalidKeyException;
import it.polimi.ingsw.exceptions.NegativeResAmountException;

import java.util.*;

public class Marbles {
    private Map<MarblesColor, Integer> marbles;

    public Marbles() {
        marbles = new HashMap<>();
    }

    //create a Marbles from a map
    public Marbles(Map<MarblesColor, Integer> map) {
        marbles = new HashMap<>(map);
    }

    public int getValue(MarblesColor key) throws InvalidKeyException {
        if (!marbles.containsKey(key)) throw new InvalidKeyException();
        return marbles.get(key);
    }

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

    public void add(MarblesColor key) throws InvalidKeyException {
        if (!MarblesColor.contains(key)) throw new InvalidKeyException();
        if (!marbles.containsKey(key)) {
            marbles.put(key, 1);
        } else {
            int newValue = marbles.get(key) + 1;
            marbles.put(key, newValue);
        }
    }

    public Resource getResources() throws NegativeResAmountException, InvalidKeyException {
        Resource res = new Resource();
        Iterator iterator = marbles.keySet().iterator();
        while (iterator.hasNext()){
            MarblesColor marble = (MarblesColor) iterator.next();
            if(marble != MarblesColor.WHITE)  res.addResource(marble.getResourceType() , marbles.get(marble));
        }
        return res;
    }

    public boolean equals(Marbles other){
        return marbles.equals(other.marbles);

    }

    public boolean containFaith(){
        return marbles.containsKey(MarblesColor.RED);
    }

}
