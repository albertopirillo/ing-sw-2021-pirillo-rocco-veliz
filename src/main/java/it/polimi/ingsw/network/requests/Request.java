package it.polimi.ingsw.network.requests;

import it.polimi.ingsw.controller.MasterController;
import it.polimi.ingsw.network.Processable;
import it.polimi.ingsw.server.Connection;
import it.polimi.ingsw.server.Server;

import java.io.Serializable;

public abstract class Request implements Serializable, Processable {
    //testing
    private String text;

    public Request() {

    }
    public void setText(String text) {
        this.text = text;
    }

    public void process(Server server, Connection connection) {
        //server.getMasterController().getRequestController().processRequest(this);
        connection.getRemoteView().processRequest(this);
    }

    public abstract void activateRequest(MasterController masterController);
}