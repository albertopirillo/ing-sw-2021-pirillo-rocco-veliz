package it.polimi.ingsw.server;

import it.polimi.ingsw.client.LocalClient;

/**
 * <p>Implementation of the Server that doesnt use TCP to play locally</p>
 * <p>Used only in local game to play without the need of a real connection</p>
 */
public class LocalServer extends Server {

    private final LocalClient client;

    /**
     * Creates a new instance of a Server
     * @param client the Client to communicate with
     */
    public LocalServer(LocalClient client) {
        this.client = client;
        this.enableLogging(false);
    }

    @Override
    public void login(String nickname, Connection connection) {
        /* Local game, no need to check:
         * - If the username already exists
         * - If the a game is already being created
         * - If the lobby is empty
         */
        addToLobby(nickname, connection);
        setGameSize(1);
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
