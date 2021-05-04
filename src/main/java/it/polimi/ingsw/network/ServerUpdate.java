package it.polimi.ingsw.network;

import it.polimi.ingsw.client.PlayerInterface;

public abstract class ServerUpdate {
    //The player nickname has to be unique for this
    private final String activePlayer;

    public ServerUpdate(String activePlayer) {
        this.activePlayer = activePlayer;
    }

    public String getActivePlayer() {
        return activePlayer;
    }

    public abstract void update(PlayerInterface playerInterface);
}
