package it.polimi.ingsw.server;

import it.polimi.ingsw.controller.MasterController;
import it.polimi.ingsw.exceptions.FullCardDeckException;
import it.polimi.ingsw.exceptions.InvalidKeyException;
import it.polimi.ingsw.exceptions.NegativeResAmountException;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.MultiGame;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.SoloGame;
import it.polimi.ingsw.network.messages.LoginMessage;
import it.polimi.ingsw.network.updates.InitialResourcesUpdate;
import it.polimi.ingsw.network.updates.ServerUpdate;
import it.polimi.ingsw.view.RemoteView;
import it.polimi.ingsw.view.View;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements Runnable {

    private final int port = 8080;
    private final ServerSocket serverSocket;
    private MasterController masterController;
    private static final List<Connection> connections = new ArrayList<>();
    private final Map<String, Connection> lobbyPlayers = new HashMap<>();
    private final ExecutorService executor = Executors.newFixedThreadPool(128);
    private String firstPlayer;
    private int gameSize = 0;

    public Server() throws IOException {
        this.serverSocket = new ServerSocket(port);
    }

    public void addToLobby(String nickname, Connection connection){
        lobbyPlayers.put(nickname, connection);
    }

    public void setGameSize(int maxPlayers){
        this.gameSize = maxPlayers;
        System.out.println("[SERVER] Number of players: " + maxPlayers);
        if(maxPlayers == 1){
            String nickname = new ArrayList<>(lobbyPlayers.keySet()).get(0);
            setupGame(maxPlayers, nickname);
        }
    }

    public void login(String nickname, Connection connection){
        //TODO Handle if nickname already exists
        System.out.println("[SERVER] New player " + nickname + " added");
        if(lobbyPlayers.isEmpty()){
            addToLobby(nickname, connection);
            ServerUpdate msg = new LoginMessage(nickname, nickname);
            connection.sendMessage(msg);
        } else {
            addToLobby(nickname, connection);
        }
        if(gameSize == lobbyPlayers.size()){
            setupGame(gameSize, nickname);
        }

    }

    public void setupGame(int gameSize, String nickname){
        System.out.println("[SERVER] LOADING GAME...");
        for (String n : lobbyPlayers.keySet()){
            System.out.println("[SERVER] Player: " + n);
        }
        if(gameSize == 1){
            setupSoloGame(nickname);
        } else {
            setupMultiGame();
        }
    }

    public void setupSoloGame(String nickname){
        Player player = new Player(nickname);
        try {
            Game game = new SoloGame(player);
            MasterController masterController = new MasterController(game);
            List<String> keys = new ArrayList<>(lobbyPlayers.keySet());
            Connection connection = lobbyPlayers.get(keys.get(0));
            View view = new RemoteView(this, /*game,*/ connections.get(0), keys.get(0));
            connection.setView(view);
            game.addObserver(view);
            view.addController(masterController);
            masterController.getSetupController().setupGame(keys, gameSize);
            System.out.println("[SERVER] Buon divertimento " + nickname);
            game = masterController.getGame();
            try {
                game.nextTurn();
            } catch (NegativeResAmountException | InvalidKeyException e) {
                e.printStackTrace();
            }
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
            List<View> views = new ArrayList<>();
            List<Connection> connections = new ArrayList<>();
            for(int i=0; i<gameSize; i++){
                connections.add(lobbyPlayers.get(keys.get(i)));
                View view = new RemoteView(this, /*game,*/ connections.get(i), keys.get(i));
                connections.get(i).setView(view);
                views.add(view);
                game.addObserver(view);
                view.addController(masterController);
            }
            masterController.getSetupController().setupGame(keys, gameSize);
            System.out.println("[SERVER] Buon divertimento :)");
            game = masterController.getGame();
            System.out.println("[SERVER] Il primo giocatore è : "+game.getActivePlayer().getNickname());
            try {
                game.nextTurn();
            } catch (NegativeResAmountException | InvalidKeyException e) {
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
            lobbyPlayers.clear();
            gameSize = 0;
            //masterController.getSetupController().startGame();
        } catch (FullCardDeckException e) {
            e.printStackTrace();
        }
    }

    public void sendInitialResources(int numPlayer, String activePlayer){
        ServerUpdate msg = new InitialResourcesUpdate(activePlayer, numPlayer);
        lobbyPlayers.get(activePlayer).sendMessage(msg);
    }

    @Override
    public void run() {
        System.out.println("[SERVER] Server is listening on port: " + port);
        while(!Thread.currentThread().isInterrupted()){
            try {
                Socket socket = serverSocket.accept();
                System.out.println("[SERVER] New client connected");
                newConnection(socket);
            } catch (IOException e) {
                System.out.println("Connection Error");
                e.printStackTrace();
            }
        }
    }

    private void newConnection(Socket socket) {
        Connection connection = new Connection(socket, this);
        registerConnection(connection);
        executor.submit(connection);
    }

    private synchronized void registerConnection(Connection connection) {
        connections.add(connection);
    }

    private synchronized void deregisterConnection(Connection connection) {
        connections.remove(connection);
    }

    public void closeClientConnection(Connection connection) {
        deregisterConnection(connection);
    }
    //TODO: testing only
    public void setMasterController(MasterController masterController) {
        this.masterController = masterController;
    }

    public MasterController getMasterController() {
        return masterController;
    }
}