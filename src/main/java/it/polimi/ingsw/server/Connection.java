package it.polimi.ingsw.server;

import it.polimi.ingsw.network.Processable;
import it.polimi.ingsw.network.RemoteView;
import it.polimi.ingsw.network.updates.ServerUpdate;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Connection implements Runnable {

    private final Server server;
    private final Socket socket;
    private ObjectInputStream socketIn;
    private ObjectOutputStream socketOut;
    private boolean active;
    private RemoteView remoteView;
    //private ConnectionListener lister -> lister = player's remote view
    private final Object outLock = new Object();
    private final Object inLock = new Object();


    public Connection(Socket socket, Server server){
        this.socket = socket;
        this.server = server;
        this.active = true;
        System.out.println("[SERVER] Client connection established.");

        try {
            synchronized (inLock) {
                this.socketIn = new ObjectInputStream(socket.getInputStream());
            }

            synchronized (outLock) {
                this.socketOut = new ObjectOutputStream(socket.getOutputStream());
            }
        } catch (IOException e) {
            // System.out.println(e.getMessage());  Test print
        }

    }

    public void setRemoteView(RemoteView remoteView){ this.remoteView = remoteView; }
    public RemoteView getRemoteView(){ return this.remoteView; }
    private synchronized boolean isActive(){
        return active;
    }

    public void sendMessage(ServerUpdate message) {
        if (isActive()) {
            try {
                synchronized (outLock) {
                    socketOut.reset();
                    socketOut.writeObject(message);
                    System.out.println("[CONNECTION] Sent message " + message.getClass().getSimpleName());
                    socketOut.flush();
                    socketOut.reset();
                }
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
                closeConnection();
                Thread.currentThread().interrupt();
            }
        }
    }

    public synchronized void closeConnection(){
        if (isActive()) {
            try {
                socket.close();
            } catch (IOException e){
                System.out.println(e.getMessage());
            }

            active = false;
        }

    }

    public void close(){
        closeConnection();
        System.out.println("Unregistering client...");
        Thread.currentThread().interrupt();
        System.out.println("Done!");
    }

    @Override
    public void run() {
        while (isActive()) {
            try {
                synchronized (inLock) {
                    Processable request = (Processable) socketIn.readObject();
                    System.out.println("\n[CONNECTION] Received request " + request.getClass().getSimpleName());
                    request.process(server, this);
                }
            } catch (IOException | ClassNotFoundException e) {
                System.out.println(e.getMessage());
                close();
            }
        }
    }
}