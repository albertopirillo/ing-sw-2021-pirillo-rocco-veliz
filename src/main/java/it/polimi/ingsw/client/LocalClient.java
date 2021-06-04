package it.polimi.ingsw.client;

import it.polimi.ingsw.client.gui.JavaFXMain;
import it.polimi.ingsw.network.Processable;
import it.polimi.ingsw.network.updates.ServerUpdate;
import it.polimi.ingsw.server.Connection;

import java.io.IOException;

public class LocalClient extends Client {

    private Connection connection;

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

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    //Only one is needed between send and receive

    @Override
    public synchronized void sendMessage(Processable message) {
        //connection.receiveMessage();
    }

    @Override
    protected ServerUpdate receiveMessage() throws IOException {
        return null;
    }

    @Override
    protected void startConnection() {

    }
}
