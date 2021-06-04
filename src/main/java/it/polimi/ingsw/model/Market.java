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

public class Market {

    private final CardDeck[][] cards;
    private final MarketTray marketTray;

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
    
    //Initialize cards
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

    //return the list of cards on the top, can return null for decks empty;
    public List<DevelopmentCard> getAvailableCards(){
        List<DevelopmentCard> availableCards = new ArrayList<>();
        for(int i=0; i<3; i++){
            for (int j=0; j<4; j++){
                //if (this.cards[i][j].getCard() != null)
                    availableCards.add(this.cards[i][j].getCard());
            }
        }
        return availableCards;
    }

    //returns the requested card, without removing it from the market
    public DevelopmentCard getCard(int level, CardColor color) throws DeckEmptyException {
        DevelopmentCard devCard =  cards[3-level][color.getNumberColumn()].getCard();
        if(devCard!=null) return devCard;
        throw new DeckEmptyException();
    }

    //card can be bought check is handled by the player
    public DevelopmentCard buyCards(int level, CardColor color) throws DeckEmptyException {
        DevelopmentCard devCard =  cards[3-level][color.getNumberColumn()].removeCard();
        if(devCard!=null) return devCard;
        throw new DeckEmptyException();
    }

    public boolean isDeckEmpty(int level, CardColor color) {
        DevelopmentCard devCard = cards[3-level][color.getNumberColumn()].getCard();
        return devCard == null;
    }

    public MarketTray getMarketTray() {
        return this.marketTray;
    }
}