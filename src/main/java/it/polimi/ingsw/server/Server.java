package it.polimi.ingsw.server;

import it.polimi.ingsw.controller.MasterController;
import it.polimi.ingsw.exceptions.FullCardDeckException;
import it.polimi.ingsw.exceptions.NegativeResAmountException;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.MultiGame;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.SoloGame;
import it.polimi.ingsw.network.RemoteView;
import it.polimi.ingsw.network.updates.InitialResourcesUpdate;
import it.polimi.ingsw.network.updates.ServerUpdate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Server implements Runnable {

    private MasterController masterController;
    private static final List<Connection> connections = new ArrayList<>();
    private final Map<String,Connection> lobbyPlayers = new HashMap<>();
    private final List<Map<String,Connection> > games = new ArrayList<>();
    private int playerAmount = 0;
    private boolean logEnabled;

    @Override
    public abstract void run();
    public abstract void login(String nickname, Connection connection);

    protected int getPlayerAmount() {
        return playerAmount;
    }

    protected Map<String, Connection> getLobbyPlayers() {
        return lobbyPlayers;
    }

    public void enableLogging(boolean enable) {
        this.logEnabled = enable;
    }

    public synchronized void addToLobby(String nickname, Connection connection){
        lobbyPlayers.put(nickname, connection);
        if(playerAmount == lobbyPlayers.size()){
            setupGame(playerAmount, nickname);
        }
    }

    private void printLog(String text) {
        if (logEnabled) {
            System.out.println(text);
        }
    }

    public synchronized void setGameSize(int maxPlayers){
        this.playerAmount = maxPlayers;
        printLog("[SERVER] Number of players: " + maxPlayers);
        if(maxPlayers == 1){
            String nickname = new ArrayList<>(lobbyPlayers.keySet()).get(0);
            setupGame(maxPlayers, nickname);
        }
    }

    protected boolean checkUsernameExist(String nickname){
        for (Map<String, Connection> map : games) {
            for (String nick : map.keySet()) {
                if ((nick).equalsIgnoreCase(nickname))
                    return true;
            }
        }
        for (String nick : lobbyPlayers.keySet()){
            if(nick.equalsIgnoreCase(nickname))
                return true;
        }
        return false;
    }

    public synchronized void setupGame(int gameSize, String nickname){
        printLog("[SERVER] LOADING GAME...");
        for (String n : lobbyPlayers.keySet()){
            printLog("[SERVER] Player: " + n);
        }
        games.add(new HashMap<>(lobbyPlayers));
        if(gameSize == 1){
            setupSoloGame(nickname);
        } else {
            setupMultiGame();
        }
        lobbyPlayers.clear();
        this.playerAmount = 0;

    }

    public void setupSoloGame(String nickname){
        Player player = new Player(nickname);
        try {
            Game game = new SoloGame(player);
            MasterController masterController = new MasterController(game);
            List<String> keys = new ArrayList<>(lobbyPlayers.keySet());
            Connection connection = lobbyPlayers.get(keys.get(0));
            RemoteView remoteView = new RemoteView(connection, keys.get(0));
            connection.setRemoteView(remoteView);
            if(logEnabled) remoteView.enableLogging(true);
            game.addObserver(remoteView);
            remoteView.addController(masterController.getRequestController());
            masterController.getSetupController().setupGame(keys, playerAmount);
            printLog("[SERVER] Starting solo game for player " + nickname);
            sendInitialResources(0, nickname);
        } catch (FullCardDeckException e) {
            e.printStackTrace();
        }
    }

    public void setupMultiGame(){
        try {
            //Create Game
            Game game = new MultiGame();
            MasterController masterController = new MasterController(game);
            List<String> keys = new ArrayList<>(lobbyPlayers.keySet());
            List<RemoteView> remoteViews = new ArrayList<>();
            List<Connection> connections = new ArrayList<>();
            for(int i = 0; i< playerAmount; i++){
                connections.add(lobbyPlayers.get(keys.get(i)));
                RemoteView remoteView = new RemoteView(connections.get(i), keys.get(i));
                if(logEnabled) remoteView.enableLogging(true);
                connections.get(i).setRemoteView(remoteView);
                remoteViews.add(remoteView);
                game.addObserver(remoteView);
                remoteView.addController(masterController.getRequestController());
            }
            masterController.getSetupController().setupGame(keys, playerAmount);
            printLog("[SERVER] Have fun :)");
            game = masterController.getGame();
            printLog("[SERVER] The first player is : "+game.getActivePlayer().getNickname());
            try {
                game.nextTurn();
            } catch (NegativeResAmountException e) {
                e.printStackTrace();
            }
            sendInitialResources(1, game.getActivePlayer().getNickname());
        /*
        //Moves to next player without checking turn's ending conditions
        int index = game.getPlayersList().indexOf(game.getActivePlayer());
        game.setActivePlayer(game.getPlayersList().get((index + 1) % game.getPlayerAmount()));

        //Nothing needs to be done if in Solo Mode
        for (int i = 1; i < gameSize; i++) {
            String activePlayer = game.getActivePlayer().getNickname();
            sendInitialResources(i, activePlayer);
            //Moves to next player without checking turn's ending conditions
            index = game.getPlayersList().indexOf(game.getActivePlayer());
            game.setActivePlayer(game.getPlayersList().get((index + 1) % game.getPlayerAmount()));
        }
        */
            //activeGames.put(activeGames.size(), connections
            //masterController.getSetupController().startGame();
        } catch (FullCardDeckException e) {
            e.printStackTrace();
        }
    }

    public void sendInitialResources(int numPlayer, String activePlayer){
        ServerUpdate msg = new InitialResourcesUpdate(activePlayer, numPlayer);
        lobbyPlayers.get(activePlayer).sendMessage(msg);
    }

    protected synchronized void registerConnection(Connection connection) {
        connections.add(connection);
    }

    protected synchronized void deregisterConnection(Connection connection) {
        connections.remove(connection);
    }

    //TODO: testing only
    public void setMasterController(MasterController masterController) {
        this.masterController = masterController;
    }

    public MasterController getMasterController() {
        return masterController;
    }
}