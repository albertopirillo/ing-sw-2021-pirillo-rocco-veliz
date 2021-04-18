package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.DeckEmptyException;
import it.polimi.ingsw.exceptions.FullCardDeckException;

import java.util.*;

public class CardDeck {

    private final Deque<DevelopmentCard> cards;

    public CardDeck() {
        this.cards = new LinkedList<>();
    }

    //Get level of deck's cards
    public int getLevel() throws DeckEmptyException {
        if (cards.isEmpty()) throw new DeckEmptyException();
        return getCard().getLevel();
    }

    //Get type of deck's cards
    public CardColor getType() throws DeckEmptyException {
        if (cards.isEmpty()) throw new DeckEmptyException();
        return getCard().getType();
    }

    //Add card to deck
    public void addCard(DevelopmentCard card) throws FullCardDeckException {
        if(getNumbersOfCards()>=4) throw new FullCardDeckException();
        cards.add(card);
        //if(getNumbersOfCards()==4) shuffle();
    }
    //Shuffle when the deck is full
    public void shuffle(){
        Collections.shuffle((List<DevelopmentCard>) this.cards);
    }

    //Return the number of cards, 0...3
    public int getNumbersOfCards(){
        return cards.size();
    }

    //Pop card from the deck
    public DevelopmentCard removeCard(){
        return this.cards.poll();
    }

    public DevelopmentCard getCard() {
        return this.cards.peek();
    }
}