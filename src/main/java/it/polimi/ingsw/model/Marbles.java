package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.IllegalKeyException;
import it.polimi.ingsw.exceptions.NegativeResAmountException;

import java.util.*;

public class Marbles {
    private Map<MarblesColor, Integer> marbles;

    public Marbles() {
        marbles = new HashMap<>();
    }

    public int getValue(MarblesColor key) throws IllegalKeyException {
        if (!marbles.containsKey(key)) throw new IllegalKeyException();
        return marbles.get(key);
    }

    public void addAll(Map<MarblesColor, Integer> marbleMap){
        marbles.putAll(marbleMap);
    }

    public void add(MarblesColor key) throws IllegalKeyException {
        if (!MarblesColor.contains(key)) throw new IllegalKeyException();
        if (!marbles.containsKey(key)) {
            marbles.put(key, 1);
        } else {
            int newValue = marbles.get(key) + 1;
            marbles.put(key, newValue);
        }
    }

    public Resource getResources() throws NegativeResAmountException, IllegalKeyException {
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

}
