package it.polimi.ingsw.client;

import it.polimi.ingsw.client.gui.JavaFXMain;
import it.polimi.ingsw.network.Processable;
import it.polimi.ingsw.network.updates.ServerUpdate;

import java.io.IOException;

/**
 * <p>Generic abstract implementation of a Client</p>
 * <p>Creates the connection with a Server</p>
 * <p>Handles sending and receiving messages</p>
 */
public abstract class Client implements Runnable{

    private UserInterface userInterface;
    private static boolean connectionReady;

    /**
     * <p>Sets up the corresponding UserInterface</p>
     * <p>Asks the player to choose a nickname</p>
     * <p>Keeps the Client listening for messages</p>
     */
    @Override
    public abstract void run();

    /**
     * Sends a message to the Server
     * @param message the message to be sent
     */
    public abstract void sendMessage(Processable message);

    /**
     * Keeps the client listening for updates from the Server
     * @return the ServerUpdate when it is received
     * @throws IOException if something goes wrong with the network
     */
    protected abstract ServerUpdate receiveMessage() throws IOException;

    /**
     * <p>Starts the connection with the server</p>
     * <p>If the Client is using a GUI, puts it in stand-by until the server is reachable </p>
     */
    protected abstract void startConnection();

    /**
     * Whether the connection has already being established or not
     * @return true if it was established, false otherwise
     */
    public static boolean isConnectionReady() {
        return connectionReady;
    }

    /**
     * <p>Sets the connectionReady flag</p>
     * <p>Setting this to true activates the GUI at the beginning</p>
     * @param ready the value to set
     */
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

    /**
     * Sets the user interface that the player will use
     * @param userInterface the user interface to set
     */
    public void setUserInterface(UserInterface userInterface) {
        this.userInterface = userInterface;
    }

    /**
     * If the Client is using a GUI, waits for it to be ready before initializing the Client
     */
    protected static void waitForGUI() {
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