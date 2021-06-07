package it.polimi.ingsw.server;

import it.polimi.ingsw.client.LocalClient;

public class LocalServer extends Server {

    private final LocalClient client;

    public LocalServer(LocalClient client) {
        this.client = client;
        this.enableLogging(false);
    }

    @Override
    public void run() {
        newConnection();
    }

    private void newConnection() {
        LocalConnection connection = new LocalConnection(this, client);
        registerConnection(connection);
        this.client.setConnection(connection);
        new Thread(connection).start();
    }
}
