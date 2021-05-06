package it.polimi.ingsw.client;

import it.polimi.ingsw.network.Processable;
import it.polimi.ingsw.network.messages.LoginMessage;
import it.polimi.ingsw.network.updates.ServerUpdate;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client implements Runnable{

    private static Socket socket;
    private final String ip;
    private final int port;
    private ObjectInputStream socketIn;
    private ObjectOutputStream socketOut;
    private PlayerInterface cli;

    public Client() {
        this.port = 8080;
        this.ip = "127.0.0.1";
    }

    @Override
    public void run() {
        cli = new ClientCLI(this);
        cli.setup();
        startConnection();
        String nickname = cli.chooseNickname();
        Processable login = new LoginMessage(nickname, nickname);
        cli.setNickname(nickname);
        sendMessage(login);
        while(!Thread.currentThread().isInterrupted()) {
            ServerUpdate msg;
            try {
                msg = receiveMessage();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            cli.readUpdate(msg);
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
            socketOut.writeObject(message);
            socketOut.flush();
        }catch (IOException e){
            System.err.println(e.getMessage());
        }
    }

    private ServerUpdate receiveMessage() throws IOException{
        ServerUpdate msg = null;
        try{
            msg = (ServerUpdate) socketIn.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return msg;
    }
}