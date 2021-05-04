package it.polimi.ingsw.view;

import it.polimi.ingsw.controller.MasterController;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.network.Request;
import it.polimi.ingsw.server.Connection;
import it.polimi.ingsw.server.Server;
import it.polimi.ingsw.utils.ModelObserver;

public abstract class View implements ModelObserver {
    protected MasterController masterController;
    private final Server server;
    private final Game game;
    protected Connection connection;
    protected int playerId;

    public View(Server server, Game game, Connection connection, int playerId){
        this.server = server;
        this.game = game;
        this.connection = connection;
        this.playerId = playerId;
    }

    public void addController(MasterController masterController){ this.masterController = masterController; }

    public void processRequest(Request request){
        //System.out.println("[REMOTEVIEW] Messaggio ricevuto from player "+playerId);
        this.masterController.processRequest(request);
    }

    public abstract void showGameState(Game game);
    public abstract void gameStateChange(Game game);
    public abstract void notifyInitResources(Game game, int numPlater);
    public abstract void notifyInitLeaderCards(Game game);
}