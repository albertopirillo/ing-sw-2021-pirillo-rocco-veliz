package it.polimi.ingsw.server;

import it.polimi.ingsw.controller.MasterController;
import it.polimi.ingsw.exceptions.FullCardDeckException;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.MultiGame;
import it.polimi.ingsw.network.Message;
import it.polimi.ingsw.network.MessageType;
import it.polimi.ingsw.network.Processable;
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

    private int port = 8080;
    private ServerSocket serverSocket;
    private MasterController masterController;
    private static final List<Connection> connections = new ArrayList<>();
    private Map<String, Connection> lobbyPlayers = new HashMap<>();
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
        System.out.println("[SERVER] Number of players :"+maxPlayers);
    }
    public void lobby(String nickname, Connection connection){
        //TODO Handle if nickname already exists
        System.out.println("[SERVER] New player "+nickname+" added");
        if(lobbyPlayers.isEmpty()){
            addToLobby(nickname, connection);
            Message msg = new Message();
            msg.setType(MessageType.LOBBY_SETUP);
            connection.sendMessage(msg);
        }else{
            addToLobby(nickname,connection);
        }
        if(gameSize == lobbyPlayers.size()){
            setupGame();
        }

    }

    public void setupGame(){
        System.out.println("[SERVER] LOADING GAME...");
        for (String nickname : lobbyPlayers.keySet()){
            System.out.println("[SERVER] Player: " + nickname);
        }
        //Create Game
        Game game = null;
        try {
            game = new MultiGame();
        } catch (FullCardDeckException e) {
            e.printStackTrace();
        }
        MasterController masterController = new MasterController(game);
        List<String> keys = new ArrayList<>(lobbyPlayers.keySet());
        List<View> views = new ArrayList<>();
        List<Connection> connections = new ArrayList<>();
        for(int i=0; i<gameSize; i++){
            connections.add(lobbyPlayers.get(keys.get(i)));
            View view = new RemoteView(this, game, connections.get(i), i);
            connections.get(i).setView(view);
            views.add(view);
            //game.addListener(view)
            view.addController(masterController);
        }
        masterController.getSetupController().setupGame(keys, gameSize);
        System.out.println("[SERVER] Buon divertimento :)");
        game = masterController.getGame();
        System.out.println("[SERVER] Il primo giocatore è : "+game.getActivePlayer().getNickname());
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

        //activeGames.put(activeGames.size(), connections
        lobbyPlayers.clear();
        gameSize = 0;
        //masterController.getSetupController().startGame();
    }

    public void sendInitialResources(int numPlayer, String activePlayer){
        Message msg = new Message(numPlayer);
        msg.setActivePlayer(activePlayer);
        msg.setType(MessageType.INITIAL_RESOURCE);
        lobbyPlayers.get(activePlayer).sendMessage(msg);
    }

    //This method will call handleMessage() if a message was received
    //or RequestController.processRequest() if a request was received
    public void handleInput(Processable processable, Connection connection) {
        processable.process(this, connection);
    }

    public void handleMessage(Message message, Connection connection) {
        //TODO: handle the messages
        //in base al messaggio (hashmap key:connection, nickname)
        //initlobby firstplayer playeramount -> creo mastercontroller, player e game
        //da master controller parte gioco
        //tutti gli altri mess non login li rigiro al controller
        switch (message.getType()){
            case LOGIN: lobby(message.getText(), connection);
                break;
            case LOBBY_SETUP: setGameSize(Integer.parseInt(message.getText()));
                break;
        }
        // System.out.println(message.getText()); test print
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