package it.polimi.ingsw.network.updates;

import it.polimi.ingsw.client.UserInterface;

public class EndOfInitialUpdate extends ServerUpdate {
    private final StorageUpdate storageUpdate;
    private final LeaderUpdate leaderUpdate;

    public EndOfInitialUpdate(String activePlayer, StorageUpdate storageUpdate, LeaderUpdate leaderUpdate) {
        super(activePlayer);
        this.storageUpdate = storageUpdate;
        this.leaderUpdate = leaderUpdate;
    }

    public StorageUpdate getStorageUpdate() {
        return storageUpdate;
    }

    public LeaderUpdate getLeaderUpdate() {
        return leaderUpdate;
    }

    @Override
    public void update(UserInterface userInterface) {
        userInterface.startMainGame(this);
    }
}
