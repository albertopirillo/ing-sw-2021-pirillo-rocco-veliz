package it.polimi.ingsw.network;

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

    private final Object outLock = new Object();
    private final Object inLock = new Object();


    public Connection(Socket socket, Server server){
        this.socket = socket;
        this.server = server;
        this.active = true;

        System.out.println("Connessione stabilita con un client");

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


    private synchronized boolean isActive(){
        return active;
    }


    public void sendMessage(Message message) {
        if (isActive()) {
            try {
                synchronized (outLock) {
                    socketOut.writeObject(message);
                    socketOut.flush();
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

    private void close(){
        closeConnection();
        System.out.println("Deregistering client...");
        Thread.currentThread().interrupt();
        System.out.println("Done!");
    }



    @Override
    public void run() {
        while (isActive()) {
            try {
                synchronized (inLock) {
                    Message message = (Message) socketIn.readObject();

                    server.handleMessage(message, this);
                    sendMessage(message);
                }
            } catch (IOException | ClassNotFoundException e) {
                System.out.println(e.getMessage());

                close();
            }
        }
    }

}