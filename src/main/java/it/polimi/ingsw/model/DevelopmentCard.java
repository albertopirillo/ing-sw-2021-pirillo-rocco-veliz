package it.polimi.ingsw.model;

import java.util.*;

public class DevelopmentCard extends Card {

    private Resource cost;
    private CardColor type;
    private int level;

    public DevelopmentCard(/*int victoryPoints,*/ Resource cost, CardColor type, int level) {
        //super(victoryPoints);
        this.cost = cost;
        this.type = type;
        this.level = level;
    }

    //check if the card(this) can be bought
    public boolean canBeBought(Resource playerResource){
        return playerResource.compare(this.cost);
    }
    //get level
    public int getLevel(){
        return this.level;
    }

    //get cost
    public Map<ResourceType, Integer> getResource(){
        return cost.getAllRes();
    }

    //get type
    public CardColor getType(){
        return this.type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DevelopmentCard)) return false;
        DevelopmentCard that = (DevelopmentCard) o;
        return getLevel() == that.getLevel() && that.cost.getAllRes().equals(this.cost.getAllRes()) && getType().equals(that.getType());
    }

   /* @Override
    public int hashCode() {
        return Objects.hash(cost, getType(), getLevel());
    }*/
}