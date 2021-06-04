package it.polimi.ingsw.client;

import it.polimi.ingsw.client.gui.JavaFXMain;
import it.polimi.ingsw.network.Processable;
import it.polimi.ingsw.network.messages.LoginMessage;
import it.polimi.ingsw.network.updates.ServerUpdate;
import it.polimi.ingsw.server.LocalConnection;

public class LocalClient extends Client {

    private final LocalConnection connection;

    public LocalClient(boolean gui, LocalConnection connection) {
        this.connection = connection;
        if (gui) {
            JavaFXMain.startGUI();
            waitForGUI();
            this.setUserInterface(new ClientGUI(this, JavaFXMain.getMainController()));
        }
        else {
            this.setUserInterface(new ClientCLI(this));
        }
    }

    public LocalConnection getConnection() {
        return connection;
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
        System.out.println("\n[CONNECTION] Received request " + message.getClass().getSimpleName());
        message.process(connection.getServer(), connection);
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
