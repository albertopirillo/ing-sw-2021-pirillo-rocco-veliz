package it.polimi.ingsw.client.model;

import it.polimi.ingsw.model.DevelopmentCard;
import it.polimi.ingsw.model.SoloActionToken;
import it.polimi.ingsw.network.updates.ActionTokenUpdate;
import it.polimi.ingsw.network.updates.DiscardedCardsUpdate;

import java.util.List;

public class SoloGameModel {
    private final ClientModel clientModel;

    private SoloActionToken nextToken;
    private List<DevelopmentCard> discardedCards;

    public SoloGameModel(ClientModel clientModel) {
        this.clientModel = clientModel;
    }

    public SoloActionToken getNextToken() {
        return nextToken;
    }

    public List<DevelopmentCard> getDiscardedCards() {
        return discardedCards;
    }

    public void saveSoloTokens(ActionTokenUpdate update) {
        this.nextToken = update.getNextToken();
    }

    public void saveDiscardedCards(DiscardedCardsUpdate update) {
        this.discardedCards = update.getCardList();
    }

    public ActionTokenUpdate buildActionTokenUpdate() {
        String nickname = clientModel.getNickname();
        return new ActionTokenUpdate(nickname, nextToken);
    }

    public DiscardedCardsUpdate buildDiscardedCardsUpdate() {
        String nickname = clientModel.getNickname();
        return new DiscardedCardsUpdate(nickname, discardedCards);
    }
}
