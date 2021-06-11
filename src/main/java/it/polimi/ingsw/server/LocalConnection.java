package it.polimi.ingsw.server;

import it.polimi.ingsw.client.LocalClient;
import it.polimi.ingsw.network.updates.ServerUpdate;

/**
 * <p>Implementation of the Connection that doesnt use TCP</p>
 * <p>The connection between Client and Server is established with local references</p>
 */
public class LocalConnection extends Connection{

    private final LocalClient client;

    /**
     * Creates a new instance of a Connection
     * @param server the Server that will be part of the connection
     * @param client the Client that will be part of the connection
     */
    public LocalConnection(LocalServer server, LocalClient client) {
        super(server);
        this.client = client;
    }

    @Override
    public void run() {
        //Nothing to be done here
    }

    @Override
    public void sendMessage(ServerUpdate message) {
        //System.out.println("[CLIENT] Receiving message " + message.getClass().getSimpleName());
        client.getUserInterface().readUpdate(message);
    }

    @Override
    public void close() {
        System.out.println("Shutting down...");
        Thread.currentThread().interrupt();
    }
}
