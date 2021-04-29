package it.polimi.ingsw.view;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.network.Request;
import it.polimi.ingsw.server.Connection;
import it.polimi.ingsw.server.Server;

public class RemoteView extends View {

    public RemoteView(Server server, Game game, Connection connection, int playerId){
        super(server, game, connection, playerId);
    }

    public void processRequest(Request request){
        super.processRequest(request);
    }
}
