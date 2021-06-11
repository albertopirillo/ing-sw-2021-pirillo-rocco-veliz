package it.polimi.ingsw.client;

import it.polimi.ingsw.client.gui.JavaFXMain;
import it.polimi.ingsw.network.Processable;
import it.polimi.ingsw.network.messages.LoginMessage;
import it.polimi.ingsw.network.updates.ServerUpdate;
import it.polimi.ingsw.server.LocalConnection;

/**
 * <p>Implementation of the Client that doesnt use TCP to play locally</p>
 * <p>Used only in local game to play without the need of connecting to a remote server</p>
 * <p>Sends and receives messages using a reference to a Connection object</p>
 */
public class LocalClient extends Client {

    private LocalConnection connection;

    /**
     * Creates a new Distance of a Client
     * @param gui true if gui should be started, false if using cli
     */
    public LocalClient(boolean gui) {
        if (gui) {
            JavaFXMain.startGUI();
            waitForGUI();
            this.setUserInterface(new ClientGUI(this, JavaFXMain.getMainController()));
        }
        else {
            this.setUserInterface(new ClientCLI(this));
        }
    }

    /**
     * Gets the associated connection object
     * @return the connection object
     */
    public LocalConnection getConnection() {
        return connection;
    }

    /**
     * Sets the associated connection object
     * @param connection the connection object to set
     */
    public void setConnection(LocalConnection connection) {
        this.connection = connection;
    }

    @Override
    public void run() {
        getUserInterface().setup();
        startConnection();
        String nickname = getUserInterface().chooseNickname();
        Processable login = new LoginMessage(nickname, nickname);
        getUserInterface().setNickname(nickname);
        sendMessage(login);
    }

    @Override
    public synchronized void sendMessage(Processable message) {
        //System.out.println("\n[CONNECTION] Received request " + message.getClass().getSimpleName());
        new Thread(() -> message.process(connection.getServer(), connection)).start();
    }

    @Override
    protected ServerUpdate receiveMessage() {
        return null;
    }

    @Override
    protected void startConnection() {
        synchronized (JavaFXMain.lock) {
            //GUI can be show now, notify JavaFXMain
            setConnectionReady(true);
            JavaFXMain.lock.notifyAll();
        }
    }
}
