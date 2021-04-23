package it.polimi.ingsw.network;

import it.polimi.ingsw.controller.MasterController;

import java.io.Serializable;

public abstract class Request implements Serializable, Processable {

    public Request() {

    }

    public void process(Server server) {
        server.getMasterController().getRequestController().processRequest(this);
    }

    public abstract void activateRequest(MasterController masterController);
}