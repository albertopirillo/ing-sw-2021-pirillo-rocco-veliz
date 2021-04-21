package it.polimi.ingsw.network;

import it.polimi.ingsw.controller.MasterController;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements Runnable {

    private int port = 8080;
    private final ServerSocket serverSocket;
    private MasterController masterController;
    private static final List<Connection> connections = new ArrayList<>();
    private final ExecutorService executor = Executors.newFixedThreadPool(128);


    public Server() throws IOException {
        this.serverSocket = new ServerSocket(port);
    }

    public void handleMessage(Message message, Connection connection) {
        //in base al messaggio (hashmap key:connection, nickname)
        //initlobby firstplayer playeramount -> creo mastercontroller, player e game
        //da master controller parte gioco
        //tutti gli altri mess non login li rigiro al controller

        // System.out.println(message.getText()); test print
    }

    @Override
    public void run() {
        System.out.println("Server is listening on port: " + port);
        while(!Thread.currentThread().isInterrupted()){
            try {
                Socket socket = serverSocket.accept();
                System.out.println("Client connected");
                newConnection(socket);
            } catch (IOException e) {
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
}