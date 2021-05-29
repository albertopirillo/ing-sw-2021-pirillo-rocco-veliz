package it.polimi.ingsw.client.model;

import it.polimi.ingsw.model.DevelopmentCard;
import it.polimi.ingsw.model.MarblesColor;
import it.polimi.ingsw.model.ResourceType;
import it.polimi.ingsw.network.updates.MarketTrayUpdate;
import it.polimi.ingsw.network.updates.MarketUpdate;
import it.polimi.ingsw.network.updates.TempMarblesUpdate;

import java.util.List;

public class MarketModel {
    private final ClientModel clientModel;

    private MarblesColor[][] marketTray;
    private MarblesColor remainingMarble;

    private List<DevelopmentCard> devCardList;

    private int numWhiteMarbles;
    private List<ResourceType> tempMarbles;

    public MarketModel(ClientModel clientModel) {
        this.clientModel = clientModel;
    }

    public MarblesColor[][] getMarketTray() {
        return marketTray;
    }

    public MarblesColor getRemainingMarble() {
        return remainingMarble;
    }

    public List<DevelopmentCard> getDevCardList() {
        return devCardList;
    }

    public int getNumWhiteMarbles() {
        return numWhiteMarbles;
    }

    public List<ResourceType> getTempMarbles() {
        return tempMarbles;
    }

    public void saveTray(MarketTrayUpdate update) {
        this.marketTray = update.getMarketTray();
        this.remainingMarble = update.getRemainingMarble();
    }

    public void saveMarket(MarketUpdate update) {
        this.devCardList = update.getDevCardList();
    }

    public void saveTempMarbles(TempMarblesUpdate update) {
        this.numWhiteMarbles = update.getNumWhiteMarbles();
        this.tempMarbles = update.getResources();
    }


}
