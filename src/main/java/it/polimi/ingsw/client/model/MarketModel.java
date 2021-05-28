package it.polimi.ingsw.client.model;

import it.polimi.ingsw.model.DevelopmentCard;
import it.polimi.ingsw.model.MarblesColor;
import it.polimi.ingsw.model.ResourceType;

import java.util.List;

public class MarketModel {
    private MarblesColor[][] marketTray;
    private MarblesColor remainingMarble;

    private List<DevelopmentCard> devCardList;

    private int numWhiteMarbles;
    private List<ResourceType> tempMarbles;

}
