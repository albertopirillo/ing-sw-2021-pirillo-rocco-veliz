package it.polimi.ingsw.network.requests;

import it.polimi.ingsw.controller.MasterController;
import it.polimi.ingsw.network.Processable;
import it.polimi.ingsw.server.Connection;
import it.polimi.ingsw.server.Server;

import java.io.Serializable;

/**
 * The concept of player action during the turn<br>
 * Each request that the client sends to the server has the interface has a specified goal and must be to processed and activated by the server
 */
public abstract class Request implements Serializable, Processable {

    public void process(Server server, Connection connection) {
        //server.getMasterController().getRequestController().processRequest(this);
        connection.getRemoteView().processRequest(this);
    }

    public abstract void activateRequest(MasterController masterController);
}