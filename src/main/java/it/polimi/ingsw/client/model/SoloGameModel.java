package it.polimi.ingsw.client.model;

import it.polimi.ingsw.model.DevelopmentCard;
import it.polimi.ingsw.model.SoloActionToken;

import java.util.List;

public class SoloGameModel {
    private SoloActionToken nextToken;
    private List<DevelopmentCard> discardedCards;

    public SoloActionToken getNextToken() {
        return nextToken;
    }

    public List<DevelopmentCard> getDiscardedCards() {
        return discardedCards;
    }
}
