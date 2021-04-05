package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.DeckEmptyException;
import it.polimi.ingsw.exceptions.FullCardDeckException;

import java.util.*;

public class CardDeck {

    private Deque<DevelopmentCard> cards;

    public CardDeck() {
        this.cards = new LinkedList<>();
    }

    //get level of deck's cards
    public int getLevel() throws DeckEmptyException {
        if (cards.isEmpty()) throw new DeckEmptyException();
        return getCard().getLevel();
    }

    //get type of deck's cards
    public CardColor getType() throws DeckEmptyException {
        if (cards.isEmpty()) throw new DeckEmptyException();
        return getCard().getType();
    }

    //add card to deck
    public void addCard(DevelopmentCard card) throws FullCardDeckException {
        if(getNumbersOfCards()>=3) throw new FullCardDeckException();
        cards.add(card);
        if(getNumbersOfCards()==3) shuffle();
    }

    //shuffle when the deck is full
    private void shuffle(){
        DevelopmentCard devCard = cards.getFirst();//only for testing
        DevelopmentCard devCard1 = cards.getLast();//only for testing
        Collections.shuffle((List<DevelopmentCard>) this.cards);
        boolean notShuffle = cards.getFirst().equals(devCard) && cards.getLast().equals(devCard1);//only for testing
        if(notShuffle) shuffle();//check that the deque isn't the same, only for testing
    }

    //return the number of cards, 0...3
    public int getNumbersOfCards(){
        return cards.size();
    }

    //pop card from the deck
    public DevelopmentCard getCard(){
        return this.cards.poll();
    }
}