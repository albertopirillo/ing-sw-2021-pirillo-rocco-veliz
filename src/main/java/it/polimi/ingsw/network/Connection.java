package it.polimi.ingsw.network;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Connection {

    public Connection() {
    }

    private Socket socket;

    private Server server;

    private ObjectInputStream socketIn;

    private ObjectOutputStream socketOut;

    private boolean isActive;

    public void sendMessage(Message message) {
        // TODO implement here
    }

    public void close() {
        // TODO implement here
    }

    public void closeConnection() {
        // TODO implement here
    }

}