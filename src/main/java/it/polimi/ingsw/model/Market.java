package it.polimi.ingsw.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import it.polimi.ingsw.exceptions.DeckEmptyException;
import it.polimi.ingsw.exceptions.FullCardDeckException;
import it.polimi.ingsw.exceptions.NotEnoughResException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.*;

public class Market {

    private CardDeck[][] cards;
    private MarketTray marketTray;

    public Market() throws FullCardDeckException {
        cards = new CardDeck[3][4];
        for(int i=0; i<3; i++){
            for (int j=0; j<4; j++){
                this.cards[i][j] = new CardDeck();
            }
        }
        initCards();
        marketTray = new MarketTray();
    }
    
    //initialize cards
    private void initCards() throws FullCardDeckException {
        //parserJSON... devCards contain all DevCards
        JsonReader reader = null;
        try {
            reader = new JsonReader(new FileReader("src/main/resources/devCardsConfig.json"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Type listType = new TypeToken<ArrayList<DevelopmentCard>>(){}.getType();
        List<DevelopmentCard> devCards = new Gson().fromJson(reader, listType);
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