package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.InvalidKeyException;
import it.polimi.ingsw.exceptions.NegativeResAmountException;

import java.util.*;

public class Marbles {
    private final Map<MarblesColor, Integer> marbles;

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

    public static Deque<MarblesColor> getAllMarbleList(Map<MarblesColor, Integer> map){
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
        for(MarblesColor marble : marbles.keySet()){
            if(marble != MarblesColor.WHITE)  res.addResource(marble.getResourceType() , marbles.get(marble));
        }
        return res;
    }

    public boolean equals(Marbles other){
        return marbles.equals(other.marbles);

    }

    public boolean containWhiteMarbles(){
        return marbles.containsKey(MarblesColor.WHITE);
    }
    public boolean containFaith(){
        return marbles.containsKey(MarblesColor.RED);
    }

}
