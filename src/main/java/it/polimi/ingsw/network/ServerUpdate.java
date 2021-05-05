package it.polimi.ingsw.network;

import it.polimi.ingsw.client.PlayerInterface;

public abstract class ServerUpdate {
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
}
