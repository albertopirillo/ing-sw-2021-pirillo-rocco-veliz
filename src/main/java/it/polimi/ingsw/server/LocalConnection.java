package it.polimi.ingsw.server;

import it.polimi.ingsw.client.LocalClient;
import it.polimi.ingsw.network.updates.ServerUpdate;

public class LocalConnection extends Connection{

    private final LocalClient client;

    public LocalConnection(LocalServer server, LocalClient client) {
        super(server);
        this.client = client;
    }

    @Override
    public void run() {
        //TODO: empty?
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
