package it.polimi.ingsw.view;

import it.polimi.ingsw.controller.MasterController;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.network.Request;
import it.polimi.ingsw.server.Connection;
import it.polimi.ingsw.server.Server;

public abstract class View {
    private MasterController masterController;
    private Server server;
    private Game game;
    protected Connection connection;
    protected int playerId;

    public View(Server sever, Game game, Connection connection, int playerId){
        this.server = server;
        this.game = game;
        this.connection = connection;
        this.playerId = playerId;
    }

    public void addController(MasterController masterController){ this.masterController = masterController; }

    public void processRequest(Request request){
        System.out.println("[REMOTEVIEW] Messaggio ricevuto from player "+playerId);
        this.masterController.processRequest(request);
    }

}
