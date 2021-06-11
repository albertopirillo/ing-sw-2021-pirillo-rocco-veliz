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

/**
 * <p>Generic abstract implementation of a Server</p>
 * <p>Handles all the lobbies for all the games</p>
 * <p>Sets up games, creating controllers and observers</p>
 */
public abstract class Server implements Runnable {

    private static final List<Connection> connections = new ArrayList<>();
    private final Map<String,Connection> lobbyPlayers = new HashMap<>();
    private final List<Map<String,Connection> > games = new ArrayList<>();
    private int playerAmount = 0;
    private boolean logEnabled;

    /**
     * Established a new connection between the Server and the Client
     */
    @Override
    public abstract void run();

    /**
     * Logs a player in, checking its username
     * @param nickname the nickname of the player
     * @param connection the connection between the Server and the Client
     */
    public abstract void login(String nickname, Connection connection);

    /**
     * Gets the player amount of the game that is being created
     * @return the player amount
     */
    protected int getPlayerAmount() {
        return playerAmount;
    }

    /**
     * Gets the mapping between nicknames and connections
     * @return a map with nicknames as keys and connections as values
     */
    protected Map<String, Connection> getLobbyPlayers() {
        return lobbyPlayers;
    }

    /**
     * Whether the log should log its operations or not
     * @param enable true to enable logging, false otherwise
     */
    public void enableLogging(boolean enable) {
        this.logEnabled = enable;
    }

    /**
     * Adds a player to the lobby list
     * @param nickname the nickname of the player
     * @param connection the connection associated with the player
     */
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

    /**
     * Sets the number of players of the game that is being created
     * @param maxPlayers the game size
     */
    public synchronized void setGameSize(int maxPlayers){
        this.playerAmount = maxPlayers;
        printLog("[SERVER] Number of players: " + maxPlayers);
        if(maxPlayers == 1){
            String nickname = new ArrayList<>(lobbyPlayers.keySet()).get(0);
            setupGame(maxPlayers, nickname);
        }
    }

    /**
     * Checks if the new inserted nickname is unique
     * @param nickname the nickname to check
     * @return true if the username was already present, false otherwise
     */
    public boolean checkUsernameExist(String nickname){
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

    private synchronized void setupGame(int gameSize, String nickname){
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

    private void setupSoloGame(String nickname){
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

    private void setupMultiGame(){
        try {
            //Create Game
            Game game = new MultiGame();
            MasterController masterController = new MasterController(game);
            List<String> keys = new ArrayList<>(lobbyPlayers.keySet());
            List<Connection> connections = new ArrayList<>();
            for(int i = 0; i< playerAmount; i++){
                connections.add(lobbyPlayers.get(keys.get(i)));
                RemoteView remoteView = new RemoteView(connections.get(i), keys.get(i));
                if(logEnabled) remoteView.enableLogging(true);
                connections.get(i).setRemoteView(remoteView);
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
        } catch (FullCardDeckException e) {
            e.printStackTrace();
        }
    }

    private void sendInitialResources(int numPlayer, String activePlayer){
        ServerUpdate msg = new InitialResourcesUpdate(activePlayer, numPlayer);
        lobbyPlayers.get(activePlayer).sendMessage(msg);
    }

    /**
     * Adds a connection to the internal list
     * @param connection the connection to add
     */
    protected synchronized void registerConnection(Connection connection) {
        connections.add(connection);
    }
}