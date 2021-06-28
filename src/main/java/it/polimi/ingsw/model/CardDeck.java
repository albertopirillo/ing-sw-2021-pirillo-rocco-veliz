package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.DeckEmptyException;
import it.polimi.ingsw.exceptions.FullCardDeckException;

import java.util.*;
/**
 * <p>The concept of cards deck</p>
 * <p>The market have twelve cards decks, each deck has maximum four cards</p>
 */
public class CardDeck {
    /**
     * To implement the concept of deck is used a deque of cards, the deque cannot contain more than 4 cards
     */
    private final Deque<DevelopmentCard> cards;

    public CardDeck() {
        this.cards = new LinkedList<>();
    }

    /**
     * Returns the head's card level
     * @return the first card's level
     * @throws DeckEmptyException if the deck is empty
     */
    public int getLevel() throws DeckEmptyException {
        if (cards.isEmpty()) throw new DeckEmptyException();
        return getCard().getLevel();
    }

    /**
     * Returns the head's card type
     * @return returns the first card's type or color
     * @throws DeckEmptyException if the deck is empty
     */
    public CardColor getType() throws DeckEmptyException {
        if (cards.isEmpty()) throw new DeckEmptyException();
        return getCard().getType();
    }

    //Add card to deck

    /**
     * Inserts the card passed in the parameter to the end of the Deque
     * @param card a Development Card
     * @throws FullCardDeckException if the deck is empty
     */
    public void addCard(DevelopmentCard card) throws FullCardDeckException {
        if(getNumbersOfCards()>=4) throw new FullCardDeckException();
        cards.add(card);
        //if(getNumbersOfCards()==4) shuffle();
    }

    /**
     * When the deck has exactly four cards, it is shuffled
     */
    @SuppressWarnings("unchecked")
    public void shuffle(){
        Collections.shuffle((List<DevelopmentCard>) this.cards);
    }

    /**
     * Return the number of cards that the deck has
     * @return returns the number of element that the deque or deck has
     */
    public int getNumbersOfCards(){
        return cards.size();
    }

    //Pop card from the deck

    /**
     * Remove the first card of the deque
     * @return returns the head element of the deque or null if the deck is empty
     */
    public DevelopmentCard removeCard(){
        return this.cards.poll();
    }

    /**
     * Retrieves but does not remove the first card of the deque
     * @return return the head element of the deque or null if the deck is empty
     */
    public DevelopmentCard getCard() {
        return this.cards.peek();
    }
}