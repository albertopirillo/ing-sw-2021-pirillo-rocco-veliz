package it.polimi.ingsw.server;

import it.polimi.ingsw.network.Processable;
import it.polimi.ingsw.network.updates.ServerUpdate;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * <p>Implementation of the Connection that uses TCP</p>
 * <p>The connection between Client and Server is established with Sockets</p>
 */
public class SocketConnection extends Connection {

    private final Socket socket;
    private ObjectInputStream socketIn;
    private ObjectOutputStream socketOut;
    private final Object outLock = new Object();
    private final Object inLock = new Object();

    /**
     * Creates a new instance of a Connection
     * @param socket the Socket of the Client that will be used
     * @param server the Server that will be part of the connection
     */
    public SocketConnection(Socket socket, Server server) {
        super(server);
        this.socket = socket;
        try {
            synchronized (inLock) {
                this.socketIn = new ObjectInputStream(socket.getInputStream());
            }

            synchronized (outLock) {
                this.socketOut = new ObjectOutputStream(socket.getOutputStream());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("[SERVER] Client connection established.");
    }

    @Override
    public void sendMessage(ServerUpdate message) {
        if (isActive()) {
            try {
                synchronized (outLock) {
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

    private synchronized void closeConnection(){
        if (isActive()) {
            try {
                socket.close();
            } catch (IOException e){
                System.out.println(e.getMessage());
            }
            setInactive();
        }

    }

    /**
     * Used to shutdown a connection when a player quits the game
     */
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
                    request.process(getServer(), this);
                }
            } catch (IOException | ClassNotFoundException e) {
                System.out.println(e.getMessage());
                close();
            }
        }
    }
}
