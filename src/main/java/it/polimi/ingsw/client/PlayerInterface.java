package it.polimi.ingsw.client;

import it.polimi.ingsw.network.*;

public abstract class PlayerInterface {
    private String nickname;

    public String getNickname() {
        return nickname;
    }

    public void readUpdate(ServerUpdate updateMessage) {
        updateMessage.update(this);
    }

    public abstract void updateStorages(StorageUpdate update);
    public abstract void updateLeaderCards(LeaderUpdate update);
    public abstract void updateDevSlots(DevSlotsUpdate update);
    public abstract void displayError(ErrorUpdate update);
    public abstract void updateMarket();
    public abstract void updateMarketTray();
    //more...
}
