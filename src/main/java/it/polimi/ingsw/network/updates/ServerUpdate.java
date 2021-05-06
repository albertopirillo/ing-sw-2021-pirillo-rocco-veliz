package it.polimi.ingsw.network.updates;

import it.polimi.ingsw.client.PlayerInterface;

import java.io.Serializable;

public abstract class ServerUpdate implements Serializable {
    //The player nickname has to be unique for this
    private final String activePlayer;

    protected String errorMsg = "";

    public ServerUpdate(String activePlayer) {
        this.activePlayer = activePlayer;
    }

    public String getActivePlayer() {
        return activePlayer;
    }

    public void setErrorMsg(String errorMsg) {
         this.errorMsg = errorMsg;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public abstract void update(PlayerInterface playerInterface);
}
