package it.polimi.ingsw.network.updates;

import it.polimi.ingsw.client.PlayerInterface;
import it.polimi.ingsw.model.DevelopmentCard;

import java.util.List;

public class DiscardedCardsUpdate extends ServerUpdate {

    private final List<DevelopmentCard> cardList;

    public DiscardedCardsUpdate(String activePlayer, List<DevelopmentCard> cardList) {
        super(activePlayer);
        this.cardList = cardList;
    }

    public List<DevelopmentCard> getCardList() {
        return cardList;
    }

    @Override
    public void update(PlayerInterface playerInterface) {
        playerInterface.updateDiscardedCards(this);
    }
}