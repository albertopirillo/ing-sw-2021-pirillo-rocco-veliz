package it.polimi.ingsw.network.updates;

import it.polimi.ingsw.client.UserInterface;

public class EndOfInitialUpdate extends ServerUpdate {
    private final StorageUpdate storageUpdate;
    private final LeaderUpdate leaderUpdate;
    private final MarketTrayUpdate marketTrayUpdate;
    private final MarketUpdate marketUpdate;

    public EndOfInitialUpdate(String activePlayer, StorageUpdate storageUpdate, LeaderUpdate leaderUpdate, MarketTrayUpdate marketTrayUpdate, MarketUpdate marketUpdate) {
        super(activePlayer);
        this.storageUpdate = storageUpdate;
        this.leaderUpdate = leaderUpdate;
        this.marketTrayUpdate = marketTrayUpdate;
        this.marketUpdate = marketUpdate;
    }

    public StorageUpdate getStorageUpdate() {
        return storageUpdate;
    }

    public LeaderUpdate getLeaderUpdate() {
        return leaderUpdate;
    }

    public MarketTrayUpdate getMarketTrayUpdate() {
        return marketTrayUpdate;
    }

    public MarketUpdate getMarketUpdate() {
        return marketUpdate;
    }

    @Override
    public void update(UserInterface userInterface) {
        userInterface.startMainGame(this);
    }
}
