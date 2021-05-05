package it.polimi.ingsw.network.updates;

import it.polimi.ingsw.client.PlayerInterface;
import it.polimi.ingsw.model.LeaderCard;

import java.io.Serializable;
import java.util.List;

public class InitialLeaderUpdate extends ServerUpdate implements Serializable {
    private final List<LeaderCard> cards;

    public InitialLeaderUpdate(String activePlayer, List<LeaderCard> cards) {
        super(activePlayer);
        this.cards = cards;
    }

    public List<LeaderCard> getCards(){
        return cards;
    }

    @Override
    public void update(PlayerInterface playerInterface) {
        if(super.getActivePlayer().equals(playerInterface.getNickname())) {
            playerInterface.viewInitialsLeadersCards(cards);
        }
    }

}
