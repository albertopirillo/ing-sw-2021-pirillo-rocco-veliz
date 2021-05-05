package it.polimi.ingsw.network;

import it.polimi.ingsw.client.PlayerInterface;

import java.io.Serializable;

public abstract class ServerUpdate implements Serializable {
    //The player nickname has to be unique for this
    private final String activePlayer;
    private final boolean lastUpdate;

    public ServerUpdate(String activePlayer, boolean lastUpdate) {
        this.activePlayer = activePlayer;
        this.lastUpdate = lastUpdate;
    }

    public boolean isLastUpdate() {
        return lastUpdate;
    }

    public String getActivePlayer() {
        return activePlayer;
    }

    public abstract void update(PlayerInterface playerInterface);

    public void setActivePlayer(String activePlayer){
        this.activePlayer = activePlayer;
    }
}
