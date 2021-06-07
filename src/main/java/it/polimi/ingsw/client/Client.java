package it.polimi.ingsw.client;

import it.polimi.ingsw.client.gui.JavaFXMain;
import it.polimi.ingsw.network.Processable;
import it.polimi.ingsw.network.updates.ServerUpdate;

import java.io.IOException;

public abstract class Client implements Runnable{

    private UserInterface userInterface;
    private static boolean connectionReady;

    @Override
    public abstract void run();
    public abstract void sendMessage(Processable message);
    protected abstract ServerUpdate receiveMessage() throws IOException;
    protected abstract void startConnection();

    public static boolean isConnectionReady() {
        return connectionReady;
    }

    public static void setConnectionReady(boolean ready) {
        connectionReady = ready;
    }

    /**
     * Gets the UserInterface
     * @return a UserInterface representing the corresponding Client
     */
    public UserInterface getUserInterface() {
        return userInterface;
    }

    public void setUserInterface(UserInterface userInterface) {
        this.userInterface = userInterface;
    }

    public static void waitForGUI() {
        synchronized (JavaFXMain.lock) {
            while (JavaFXMain.getMainController() == null) {
                try {
                    System.out.println("[CLIENT] Waiting for GUI...");
                    JavaFXMain.lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("[CLIENT] GUI is ready");
    }
}