package it.polimi.ingsw.network.updates;

import it.polimi.ingsw.client.PlayerInterface;
import it.polimi.ingsw.model.MarblesColor;

public class MarketTrayUpdate extends ServerUpdate {
    private final int[][] marketTray;
    private final MarblesColor remainingMarble;

    public MarketTrayUpdate(String activePlayer, boolean lastUpdate, int[][] marketTray, MarblesColor remainingMarble) {
        super(activePlayer, lastUpdate);
        this.marketTray = marketTray;
        this.remainingMarble = remainingMarble;
    }

    public int[][] getMarketTray() {
        return marketTray;
    }

    public MarblesColor getRemainingMarble() {
        return remainingMarble;
    }

    @Override
    public void update(PlayerInterface playerInterface) {
        playerInterface.updateMarketTray(this);
    }
}
