package it.polimi.ingsw.view;

import it.polimi.ingsw.controller.MasterController;
import it.polimi.ingsw.model.ClientError;
import it.polimi.ingsw.model.Game;
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

    public void addController(MasterController masterController){ this.masterController = masterController; }

    public void processRequest(Request request){
        this.masterController.processRequest(request);
    }

    public abstract void gameStateChange(Game game);
    public abstract void showFaithTrack(Game game);
    public abstract void showLeaderCards(Game game, String errorMsg);
    public abstract void notifyInitResources(Game game, int numPlayer);
    public abstract void notifyInitLeaderCards(Game game);
    public abstract void showClientError(Game game, ClientError clientError);
}