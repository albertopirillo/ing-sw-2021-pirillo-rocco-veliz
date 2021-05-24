package it.polimi.ingsw.client;

import it.polimi.ingsw.client.gui.JavaFXMain;
import it.polimi.ingsw.network.Processable;
import it.polimi.ingsw.network.messages.LoginMessage;
import it.polimi.ingsw.network.updates.ServerUpdate;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client implements Runnable{

    private static Socket socket; //TODO: static??
    private final String ip;
    private final int port;
    private ObjectInputStream socketIn;
    private ObjectOutputStream socketOut;
    private final UserInterface userInterface;

    /**
     * <p>Constructor used in testing</p>
     * <p>Puts everything at its default value</p>
     * <p>localhost:8080, CLI</p>
     */
    public Client() {
        this.port = 8080;
        this.ip = "127.0.0.1";
        this.userInterface = new ClientCLI(this);
    }

    /**
     * Creates a new Client instance
     * @param port  the port used to communicate with the server
     * @param gui   true if gui should be started, false if using cli
     */
    public Client(int port, boolean gui) {
        this.port = port;
        this.ip = "127.0.0.1";
        if (gui) {
            JavaFXMain.startGUI();
            waitForGUI();
            this.userInterface = new ClientGUI(this, JavaFXMain.getMainController());
        }
        else {
            this.userInterface = new ClientCLI(this);
        }
    }

    /**
     * Gets the UserInterface
     * @return a UserInterface representing the corresponding Client
     */
    public UserInterface getUserInterface() {
        return userInterface;
    }

    private static void waitForGUI() {
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
                System.out.println("\nQuitting game.");
                break;
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
            userInterface.readUpdate(msg);
        }
    }

    private void startConnection(){
        try {
            socket = new Socket(ip, port);
            System.out.println("Connected to localhost");
            socketOut = new ObjectOutputStream(socket.getOutputStream());
            socketIn = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void sendMessage(Processable message){
        try {
            socketOut.reset();
            //System.out.println("[CLIENT] Sending request " + message.getClass().getSimpleName());
            socketOut.writeObject(message);
            socketOut.flush();
        } catch (IOException e){
            System.err.println(e.getMessage());
        }
    }

    private ServerUpdate receiveMessage() throws IOException{
        ServerUpdate message = null;
        try{
            message = (ServerUpdate) socketIn.readObject();
            //System.out.println("[CLIENT] Receiving message " + message.getClass().getSimpleName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return message;
    }
}