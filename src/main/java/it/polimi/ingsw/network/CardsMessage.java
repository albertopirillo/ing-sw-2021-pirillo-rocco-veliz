package it.polimi.ingsw.network;

import it.polimi.ingsw.model.LeaderCard;

import java.util.List;

public class CardsMessage extends Message {
    private List<LeaderCard> cards;

    public CardsMessage(List<LeaderCard> cards){
        this.cards = cards;
    }

    public List<LeaderCard> getCards(){
        return cards;
    }
}
