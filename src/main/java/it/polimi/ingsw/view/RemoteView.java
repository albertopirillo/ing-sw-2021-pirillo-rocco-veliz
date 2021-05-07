package it.polimi.ingsw.view;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.network.requests.Request;
import it.polimi.ingsw.network.updates.*;
import it.polimi.ingsw.server.Connection;
import it.polimi.ingsw.server.Server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RemoteView extends View {

    public RemoteView(Server server, Connection connection, String player){
        super(server, connection, player);
    }

    public void processRequest(Request request){
        System.out.println("[REMOTEVIEW] Messaggio ricevuto from player " + player);
        super.processRequest(request);
    }

    public void notifyInitResources(Game game, int numPlayer){
        ServerUpdate msg = new InitialResourcesUpdate(game.getActivePlayer().getNickname(), numPlayer);
        connection.sendMessage(msg);
    }

    public void notifyInitLeaderCards(Game game){
        //Prepare message with initial Leader Cards
        Player activePlayer = game.getActivePlayer();
        List<LeaderCard> cards = new ArrayList<>(activePlayer.getLeaderCards());
        ServerUpdate msg = new InitialLeaderUpdate(activePlayer.getNickname(), cards);
        connection.sendMessage(msg);
    }

    public void showFaithTrack(Game game){
        Map<String, FaithTrack> faithTrackInfoMap = new HashMap<>();
        List<Player> players = game.getPlayersList();
        for(Player player: players){
            faithTrackInfoMap.put(player.getNickname(), player.getPersonalBoard().getFaithTrack());
        }
        FaithTrackUpdate faithTrackMsg = new FaithTrackUpdate(game.getActivePlayer().getNickname(), faithTrackInfoMap);
        connection.sendMessage(faithTrackMsg);
    }

    public void showLeaderCards(Game game){
        Map<String, List<LeaderCard>> leaderCardsMap = new HashMap<>();
        List<Player> players = game.getPlayersList();
        for(Player player: players){
            leaderCardsMap.put(player.getNickname(), player.getLeaderCards());
        }
        LeaderUpdate leaderUpdateMsg = new LeaderUpdate(game.getActivePlayer().getNickname(), leaderCardsMap);
        connection.sendMessage(leaderUpdateMsg);
    }

    public void gameStateChange(Game game){
        //Simulazione endTurnRequest and endUpdate
        //System.out.println("[REMOTE VIEW] Gioco in corso, turno di " + player);
        ServerUpdate msg = new EndOfUpdate(game.getActivePlayer().getNickname());
        connection.sendMessage(msg);
    }

    public void notifyGameOver(String winner) {
        //TODO: ...
    }

    @Override
    public void showClientError(Game game, ClientError clientError) {
        Player activePlayer = game.getActivePlayer();
        ServerUpdate msg = new ErrorUpdate(activePlayer.getNickname(), clientError);
        connection.sendMessage(msg);
    }

    public void showMarketTray(Game game){
        Player activePlayer = game.getActivePlayer();
        MarketTray marketTray = game.getMarket().getMarketTray();
        ServerUpdate msg = new MarketTrayUpdate(activePlayer.getNickname(), marketTray.getMarketMarbles(), marketTray.getRemainingMarble());
        connection.sendMessage(msg);
    }

    public void showMarket(Game game){
        Player activePlayer = game.getActivePlayer();
        Market market = game.getMarket();
        ServerUpdate msg = new MarketUpdate(activePlayer.getNickname(), market.getAvailableCards());
        connection.sendMessage(msg);
    }
}
