package it.polimi.ingsw.server;

import it.polimi.ingsw.client.LocalClient;

public class LocalServer extends Server {

    private final LocalClient client;

    public LocalServer(LocalClient client) {
        this.client = client;
    }

    @Override
    public void run() {
        newConnection();
    }

    private void newConnection() {
        Connection connection = new LocalConnection(this, client);
        registerConnection(connection);
        new Thread(connection).start();
    }
}
