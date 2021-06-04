package it.polimi.ingsw.server;

import it.polimi.ingsw.network.updates.ServerUpdate;

public class LocalConnection extends Connection{

    public LocalConnection(Server server) {
        super(server);
    }

    @Override
    public void run() {

    }

    @Override
    public void sendMessage(ServerUpdate message) {

    }

    @Override
    public void closeConnection() {

    }

    @Override
    public void close() {

    }
}
