package it.polimi.ingsw.client;

import it.polimi.ingsw.client.gui.JavaFXMain;
import it.polimi.ingsw.network.Processable;
import it.polimi.ingsw.network.messages.LoginMessage;
import it.polimi.ingsw.network.updates.ServerUpdate;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;

public class Client implements Runnable{

    private static Socket socket; //TODO: static??
    private final String serverIP;
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
        this.serverIP = "127.0.0.1";
        this.userInterface = new ClientCLI(this);
    }

    /**
     * Creates a new Client instance
     * @param port  the port used to communicate with the server
     * @param gui   true if gui should be started, false if using cli
     */
    public Client(boolean gui, int port, String serverIP) {
        this.port = port;
        this.serverIP = serverIP;
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

    /**
     * Gets the Socket to communicate with the Server
     * @return the Socket
     */
    public static Socket getSocket() {
        return socket;
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
                System.out.println("\n[CLIENT] Quitting game.");
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
            socket = new Socket(serverIP, port);
            System.out.println("[CLIENT] Connected to " + serverIP);
            socketOut = new ObjectOutputStream(socket.getOutputStream());
            socketIn = new ObjectInputStream(socket.getInputStream());
            synchronized (JavaFXMain.lock) {
                //GUI can be shown now, notify JavaFXMain
                JavaFXMain.lock.notifyAll();
            }
        } catch (ConnectException e) {
            tryToReconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void tryToReconnect() {
        System.out.println("[CLIENT] Server is unreachable, retrying in 5 seconds...");
        try {
            Thread.sleep(5000);
            startConnection();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized void sendMessage(Processable message){
        try {
            //System.out.println("[CLIENT] Sending request " + message.getClass().getSimpleName());
            socketOut.writeObject(message);
            socketOut.flush();
            socketOut.reset();
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