package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.DeckEmptyException;
import it.polimi.ingsw.exceptions.FullCardDeckException;
import it.polimi.ingsw.exceptions.NotEnoughResException;

import java.util.*;

public class Market {

    private CardDeck[][] cards;
    private final MarketTray marketTray;

    public Market(List<DevelopmentCard> devCards) throws FullCardDeckException {
        cards = new CardDeck[3][4];
        for(int i=0; i<3; i++){
            for (int j=0; j<4; j++){
                this.cards[i][j] = new CardDeck();
            }
        }
        initCards(devCards);
        marketTray = new MarketTray();
    }

    public MarketTray getMarketTray() {
        return this.marketTray;
    }
    
    //initialize cards
    private void initCards(List<DevelopmentCard> devCards) throws FullCardDeckException {
        //List<DevelopmentCard> devCards = new ArrayList<>();
        //parserJSON... devCards contain all DevCards
        for (DevelopmentCard devCard : devCards) {
            this.cards[3-devCard.getLevel()][devCard.getType().getNumberColumn()].addCard(devCard);
        }
    }

    //return the list of cards on the top, can return null for decks empty;
    public List<DevelopmentCard> getAvailableCards(){
        List<DevelopmentCard> availableCards = new ArrayList<>();
        for(int i=0; i<3; i++){
            for (int j=0; j<4; j++){
                availableCards.add(this.cards[i][j].getCard());
            }
        }
        return availableCards;
    }
    //card can be bought check is handled by the player
    public DevelopmentCard buyCards(int level, CardColor color) throws DeckEmptyException {
        DevelopmentCard devCard =  cards[3-level][color.getNumberColumn()].getCard();
        if(devCard!=null) return devCard;
        throw new DeckEmptyException();
    }

    public void endgame() {

    }

}