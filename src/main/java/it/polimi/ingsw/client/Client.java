package it.polimi.ingsw.client;

import it.polimi.ingsw.client.gui.JavaFXMain;
import it.polimi.ingsw.network.Processable;
import it.polimi.ingsw.network.messages.LoginMessage;
import it.polimi.ingsw.network.updates.ServerUpdate;

import java.io.EOFException;
import java.io.IOException;

public abstract class Client implements Runnable{

    private UserInterface userInterface;
    private static boolean connectionReady;

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

    @Override
    public void run() {
        userInterface.setup();
        startConnection();
        String nickname = userInterface.chooseNickname();
        Processable login = new LoginMessage(nickname, nickname);
        userInterface.setNickname(nickname);
        sendMessage(login);
        while(!Thread.currentThread().isInterrupted()) {
            ServerUpdate msg;
            try {
                msg = receiveMessage();
            } catch(EOFException e){
                System.out.println("\n[CLIENT] Quitting game.");
                break;
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
            userInterface.readUpdate(msg);
        }
    }

    public abstract void sendMessage(Processable message);
    protected abstract ServerUpdate receiveMessage() throws IOException;
    protected abstract void startConnection();
}