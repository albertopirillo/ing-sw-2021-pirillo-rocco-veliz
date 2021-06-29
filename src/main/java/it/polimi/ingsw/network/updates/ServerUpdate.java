package it.polimi.ingsw.network.updates;

import it.polimi.ingsw.client.UserInterface;

import java.io.Serializable;

/**
 * The concept of game update<br>
 * Each game update that the server sends to the client has a specified goal and must be to processed(viewed) by the client UI
 */
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
