package it.polimi.ingsw.view;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.LeaderCard;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.network.CardsMessage;
import it.polimi.ingsw.network.Message;
import it.polimi.ingsw.network.MessageType;
import it.polimi.ingsw.network.Request;
import it.polimi.ingsw.server.Connection;
import it.polimi.ingsw.server.Server;

import java.util.ArrayList;
import java.util.List;

public class RemoteView extends View {

    public RemoteView(Server server, Game game, Connection connection, int playerId){
        super(server, game, connection, playerId);
    }

    public void notifyInitResources(Game game, int numPlayer){
        Message msg = new Message(numPlayer);
        msg.setActivePlayer(game.getActivePlayer().getNickname());
        msg.setType(MessageType.INITIAL_RESOURCE);
        connection.sendMessage(msg);
    }

    public void notifyInitLeaderCards(Game game){
        //Prepare message with initial Leader Cards
        Player activePlayer = game.getActivePlayer();
        List<LeaderCard> cards = new ArrayList<>(activePlayer.getLeaderCards());
        Message msg = new CardsMessage(cards);
        msg.setType(MessageType.INITIAL_CARDS);
        msg.setActivePlayer(activePlayer.getNickname());
        msg.setText("prova");
        connection.sendMessage(msg);
    }

    public void processRequest(Request request){
        System.out.println("[REMOTEVIEW] Messaggio ricevuto from player "+playerId);
        super.processRequest(request);
    }

    public void showGameState(Game game){
        //Preparare messaggio con board compelta da inviare al cliente
        //connection.sendMessage(null);
        Message msg = new Message("Simulazione turno di gioco");
        msg.setType(MessageType.PLAYER_MOVE);
        msg.setActivePlayer(game.getActivePlayer().getNickname());
        connection.sendMessage(msg);
    }
    public  void gameStateChange(Game game){
        System.out.println("[REMOTE VIEW] Gioco in corso, turno di "+playerId);
        showGameState(game);
    }
    public void notifyGameOver(String winner) {
        //TODO: ...
    }
}
