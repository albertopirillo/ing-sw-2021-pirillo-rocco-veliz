package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.DevSlotEmptyException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class DevelopmentSlot implements Serializable {

    private final Deque<DevelopmentCard> cards;

    private transient boolean isProductionActive; //TODO: might be useless at all

    public DevelopmentSlot() {
        this.cards = new LinkedList<>();
    }

    public List<DevelopmentCard> getCards(){
        return new ArrayList<>(cards);
    }

    public void addCard(DevelopmentCard card){
        cards.push(card);
    }

    public int numberOfElements(){
        return cards.size();
    }

    public int getLevelSlot() throws DevSlotEmptyException {
        if(cards.isEmpty()) throw new DevSlotEmptyException();
        return cards.peek().getLevel();
    }

    public DevelopmentCard getTopCard() throws DevSlotEmptyException {
        if(cards.isEmpty())
            throw new DevSlotEmptyException();
        return cards.peek();
    }

    public boolean isProductionActive() {
        return isProductionActive;
    }

    public void setProductionActive(boolean productionActive) {
        isProductionActive = productionActive;
    }

    public boolean canBeAdded(DevelopmentCard card) throws DevSlotEmptyException {
        return cards.isEmpty() && card.getLevel()==1 || cards.size()<3 && getLevelSlot() + 1 == card.getLevel();
    }
}