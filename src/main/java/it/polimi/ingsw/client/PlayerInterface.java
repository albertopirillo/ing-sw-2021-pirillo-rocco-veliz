package it.polimi.ingsw.client;

import it.polimi.ingsw.network.ServerUpdate;
import it.polimi.ingsw.network.StorageUpdate;

public abstract class PlayerInterface {
    private String nickname;

    public String getNickname() {
        return nickname;
    }

    public void readUpdate(ServerUpdate updateMessage) {
        updateMessage.update(this);
    }

    public abstract void updateStorages(StorageUpdate update);
    public abstract void updateLeaderCards();
    public abstract void updateMarket();
    public abstract void updateMarketTray();
    //more...
}
