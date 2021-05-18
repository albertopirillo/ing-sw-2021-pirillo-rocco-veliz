package it.polimi.ingsw.network.updates;

import it.polimi.ingsw.client.UserInterface;

import java.io.Serializable;

public abstract class ServerUpdate implements Serializable {
    //The player nickname has to be unique for this
    private final String activePlayer;

    public ServerUpdate(String activePlayer) {
        this.activePlayer = activePlayer;
    }

    public String getActivePlayer() {
        return activePlayer;
    }

    public abstract void update(UserInterface userInterface);
}
