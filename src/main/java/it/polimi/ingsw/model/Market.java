package it.polimi.ingsw.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.exceptions.DeckEmptyException;
import it.polimi.ingsw.exceptions.FullCardDeckException;

import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The Class that represents the Market
 */
public class Market {

    /**
     * The matrix 4x3 that contains the twelve card decks of Development Cards
     */
    private final CardDeck[][] cards;
    /**
     * The market tray that contains all marbles
     */
    private final MarketTray marketTray;

    /**
     * Initialize the Market filling the cards' matrix and the market tray
     */
    public Market() throws FullCardDeckException {
        cards = new CardDeck[3][4];
        for(int i=0; i<3; i++){
            for (int j=0; j<4; j++){
                this.cards[i][j] = new CardDeck();
            }
        }
        initCards();
        shuffleCards();
        marketTray = new MarketTray();
    }
    public Market(boolean noRandom) throws FullCardDeckException {
        cards = new CardDeck[3][4];
        for(int i=0; i<3; i++){
            for (int j=0; j<4; j++){
                this.cards[i][j] = new CardDeck();
            }
        }
        initCards();
        marketTray = new MarketTray(noRandom);
    }
    
    //Initialize cards from json file
    private void initCards() throws FullCardDeckException {
        //ParserJSON... devCards contain all DevCards
        Reader reader = new InputStreamReader(Objects.requireNonNull(getClass().getResourceAsStream("/json/DevCardsConfig.json")));
        Type listType = new TypeToken<ArrayList<DevelopmentCard>>(){}.getType();
        List<DevelopmentCard> devCards = new Gson().fromJson(reader, listType);
        for (DevelopmentCard devCard : devCards) {
            this.cards[3-devCard.getLevel()][devCard.getType().getNumberColumn()].addCard(devCard);
        }
    }
    private void shuffleCards(){
        for(int i=0; i<3; i++){
            for (int j=0; j<4; j++){
                this.cards[i][j].shuffle();
            }
        }
    }

    /**
     * Returns the list of cards at the top for each card deck in the cards' matrix
     * @return a list composed of the head element for each deck in the cards' matrix or of null element if the deck is empty
     */
    public List<DevelopmentCard> getAvailableCards(){
        List<DevelopmentCard> availableCards = new ArrayList<>();
        for(int i=0; i<3; i++){
            for (int j=0; j<4; j++){
                availableCards.add(this.cards[i][j].getCard());
            }
        }
        return availableCards;
    }

    /**
     * Returns, and not remove, the Development card specified by the parameter passed: level and color
     * @param level the level of the card(or deck)
     * @param color the color of the card(or deck)
     * @return the Development card at the top of the deck that matches the parameters in the cards' matrix
     * @throws DeckEmptyException if the deck that matches the parameters is empty
     */
    public DevelopmentCard getCard(int level, CardColor color) throws DeckEmptyException {
        DevelopmentCard devCard =  cards[3-level][color.getNumberColumn()].getCard();
        if(devCard!=null) return devCard;
        throw new DeckEmptyException();
    }

    /**
     * Returns and remove the Development card specified by the parameter passed: level and color
     * @param level the level of the card(or deck)
     * @param color the color of the card(or deck)
     * @return the Development card at the top of the deck that matches the parameters in the cards' matrix
     * @throws DeckEmptyException if the deck that matches the parameters is empty
     */
    public DevelopmentCard buyCards(int level, CardColor color) throws DeckEmptyException {
        DevelopmentCard devCard =  cards[3-level][color.getNumberColumn()].removeCard();
        if(devCard!=null) return devCard;
        throw new DeckEmptyException();
    }

    /**
     * Check if the deck that matches the parameter is empty
     * @param level the level of the card(or deck)
     * @param color the color of the card(or deck)
     * @return true if the deck is empty, false otherwise
     */
    public boolean isDeckEmpty(int level, CardColor color) {
        DevelopmentCard devCard = cards[3-level][color.getNumberColumn()].getCard();
        return devCard == null;
    }

    public MarketTray getMarketTray() {
        return this.marketTray;
    }
}