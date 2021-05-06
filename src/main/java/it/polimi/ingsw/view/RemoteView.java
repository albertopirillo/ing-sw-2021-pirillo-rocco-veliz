package it.polimi.ingsw.view;

import it.polimi.ingsw.model.FaithTrack;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.LeaderCard;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.network.*;
import it.polimi.ingsw.server.Connection;
import it.polimi.ingsw.server.Server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RemoteView extends View {

    public RemoteView(Server server, Game game, Connection connection, int playerId){
        super(server, game, connection, playerId);
    }

    public void notifyInitResources(Game game, int numPlayer){
        ServerUpdate msg = new InitialResourcesMessage(game.getActivePlayer().getNickname(), false, numPlayer);
        connection.sendMessage(msg);
    }

    public void notifyInitLeaderCards(Game game){
        //Prepare message with initial Leader Cards
        Player activePlayer = game.getActivePlayer();
        List<LeaderCard> cards = new ArrayList<>(activePlayer.getLeaderCards());
        ServerUpdate msg = new LeaderCardsUpdate(activePlayer.getNickname(), false, cards);
        //msg.setType(MessageType.INITIAL_CARDS);
        //msg.setText("prova");
        connection.sendMessage(msg);
    }

    public void processRequest(Request request){
        System.out.println("[REMOTEVIEW] Messaggio ricevuto from player " + playerId);
        super.processRequest(request);
    }

    public void showFaithTrack(){
        Map<String, FaithTrack> faithTrackInfoMap = new HashMap<>();
        List<Player> players = game.getPlayersList();
        for(Player player: players){
            faithTrackInfoMap.put(player.getNickname(), player.getPersonalBoard().getFaithTrack());
        }
        FaithTrackUpdate faithTrackMsg = new FaithTrackUpdate(game.getActivePlayer().getNickname(),true, faithTrackInfoMap);
        connection.sendMessage(faithTrackMsg);
    }

    public void showGameState(Game game){
        //Preparare messaggio con board compelta da inviare al cliente
        //connection.sendMessage(null);
        ServerUpdate msg = new TestUpdate(game.getActivePlayer().getNickname(), false, "Simulazione turno di gioco");
        //msg.setType(MessageType.PLAYER_MOVE);
        connection.sendMessage(msg);
    }

    public void gameStateChange(Game game){
        System.out.println("[REMOTE VIEW] Gioco in corso, turno di " + playerId);
        showGameState(game);
    }

    public void notifyGameOver(String winner) {
        //TODO: ...
    }
}
