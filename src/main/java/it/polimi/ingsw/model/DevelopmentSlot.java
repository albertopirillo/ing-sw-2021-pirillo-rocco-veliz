package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.DevSlotEmptyException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * <p>The player's slot</p>
 * <p>Each player has three slots, each slot has maximum three Development Cards</p>
 */
public class DevelopmentSlot implements Serializable, Cloneable {
    /**
     * The deque of cards, the deque cannot contain more than 3 cards
     */
    private final Deque<DevelopmentCard> cards;

    /**
     * Create a Development Slot empty
     */
    public DevelopmentSlot() {
        this.cards = new LinkedList<>();
    }

    public List<DevelopmentCard> getCards(){
        return new ArrayList<>(cards);
    }

    /**
     * Insert the card at the head(first position) of the deque
     * @param card the Development Card
     */
    public void addCard(DevelopmentCard card){
        cards.push(card);
    }

    /**
     * Returns the number of cards that the slot has
     * @return the number of elements of the deque
     */
    public int numberOfElements(){
        return cards.size();
    }

    /**
     * Returns the level of the card at first position
     * @return the card's level of the card at the head of deque
     * @throws DevSlotEmptyException if the slot is empty
     */
    public int getLevelSlot() throws DevSlotEmptyException {
        if(cards.isEmpty()) throw new DevSlotEmptyException();
        return cards.peek().getLevel();
    }

    /**
     * Return the head card if exists
     * @return Return the card at the top of the slot
     * @throws DevSlotEmptyException if the slot is empty
     */
    public DevelopmentCard getTopCard() throws DevSlotEmptyException {
        if(cards.isEmpty())
            throw new DevSlotEmptyException();
        return cards.peek();
    }

    /**
     * Check if the card passed in the parameter can be added to the slot's deque
     * A card can be added to the slot's deque only if its level is equals to the head card's level + 1
     * @param card a Development Card
     * @return true if the card passed in the parameter can be added, false otherwise
     * @throws DevSlotEmptyException if the slot is empty
     */
    public boolean canBeAdded(DevelopmentCard card) throws DevSlotEmptyException {
        return cards.isEmpty() && card.getLevel()==1 || cards.size()<3 && getLevelSlot() + 1 == card.getLevel();
    }

    @Override
    public DevelopmentSlot clone() {
        DevelopmentSlot clone = null;
        try {
             clone = (DevelopmentSlot) super.clone();
             for(DevelopmentCard card: this.getCards()) {
                 clone.getCards().add(card.clone());
             }
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return clone;
    }
}