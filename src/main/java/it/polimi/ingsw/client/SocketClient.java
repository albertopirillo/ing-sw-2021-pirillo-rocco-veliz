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

public class SocketClient extends Client {
    private final String serverIP;
    private final int port;
    private ObjectInputStream socketIn;
    private ObjectOutputStream socketOut;

    /**
     * <p>Constructor used in testing</p>
     * <p>Puts everything at its default value</p>
     * <p>localhost:8080, CLI</p>
     */
    public SocketClient() {
        this.port = 8080;
        this.serverIP = "127.0.0.1";
        this.setUserInterface(new ClientCLI(this));
    }

    /**
     * Creates a new Client instance
     * @param port  the port used to communicate with the server
     * @param gui   true if gui should be started, false if using cli
     */
    public SocketClient(boolean gui, int port, String serverIP) {
        this.port = port;
        this.serverIP = serverIP;
        if (gui) {
            JavaFXMain.startGUI();
            waitForGUI();
            this.setUserInterface(new ClientGUI(this, JavaFXMain.getMainController()));
        }
        else {
            this.setUserInterface(new ClientCLI(this));
        }
    }

    @Override
    public void run() {
        getUserInterface().setup();
        startConnection();
        String nickname = getUserInterface().chooseNickname();
        Processable login = new LoginMessage(nickname, nickname);
        getUserInterface().setNickname(nickname);
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
            getUserInterface().readUpdate(msg);
        }
    }

    @Override
    protected void startConnection(){
        try {
            Socket socket = new Socket(serverIP, port);
            System.out.println("[CLIENT] Connected to " + serverIP);
            socketOut = new ObjectOutputStream(socket.getOutputStream());
            socketIn = new ObjectInputStream(socket.getInputStream());
            synchronized (JavaFXMain.lock) {
                //GUI can be show now, notify JavaFXMain
                setConnectionReady(true);
                JavaFXMain.lock.notifyAll();
            }
        } catch (ConnectException e) {
            tryToReconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
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

    @Override
    public ServerUpdate receiveMessage() throws IOException{
        ServerUpdate message = null;
        try{
            message = (ServerUpdate) socketIn.readObject();
            //System.out.println("[CLIENT] Receiving message " + message.getClass().getSimpleName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return message;
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
}
