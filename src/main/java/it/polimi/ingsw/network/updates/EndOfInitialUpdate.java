package it.polimi.ingsw.network.updates;

import it.polimi.ingsw.client.UserInterface;

public class EndOfInitialUpdate extends ServerUpdate {
    private final StorageUpdate storageUpdate;
    private final LeaderUpdate leaderUpdate;
    private final MarketTrayUpdate marketTrayUpdate;

    public EndOfInitialUpdate(String activePlayer, StorageUpdate storageUpdate, LeaderUpdate leaderUpdate, MarketTrayUpdate marketTrayUpdate) {
        super(activePlayer);
        this.storageUpdate = storageUpdate;
        this.leaderUpdate = leaderUpdate;
        this.marketTrayUpdate = marketTrayUpdate;
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

    @Override
    public void update(UserInterface userInterface) {
        userInterface.startMainGame(this);
    }
}
