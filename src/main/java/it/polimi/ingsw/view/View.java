package it.polimi.ingsw.view;

import it.polimi.ingsw.controller.MasterController;
import it.polimi.ingsw.network.requests.Request;
import it.polimi.ingsw.server.Connection;
import it.polimi.ingsw.server.Server;

public abstract class View implements ModelObserver {
    protected MasterController masterController;
    protected final Server server;
    protected Connection connection;
    protected String player;

    public View(Server server, Connection connection, String player){
        this.server = server;
        this.connection = connection;
        this.player = player;
    }

    public String getPlayer(){
        return player;
    }

    public MasterController getMasterController() {
        return masterController;
    }

    public void addController(MasterController masterController){ this.masterController = masterController; }

    public void processRequest(Request request){
        this.masterController.processRequest(request);
    }
}