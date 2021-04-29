package it.polimi.ingsw.client;

import it.polimi.ingsw.model.ResourceType;
import it.polimi.ingsw.network.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Map;

public class Client implements Runnable{

    private static Socket socket;
    private String ip;
    private int port;
    private ObjectInputStream socketIn;
    private ObjectOutputStream socketOut;
    private ClientCLI cli;

    public Client() {
        this.port = 8080;
        this.ip = "127.0.0.1";
    }

    @Override
    public void run() {
        cli = new ClientCLI();
        cli.setup();
        startConnection();
        String nickname = cli.getNickname();
        Message login = new Message(nickname);
        login.setType(MessageType.LOGIN);
        sendMessage(login);
        while(!Thread.currentThread().isInterrupted()){
            Message msg;
            try {
                msg = receiveMessage();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            switch (msg.getType()){
                case LOBBY_SETUP:
                    int gameSize = cli.getGameSize();
                    Message rsp = new Message(gameSize);
                    rsp.setType(MessageType.LOBBY_SETUP);
                    sendMessage(rsp);
                    break;
                case INITIAL_RESOURCE:
                    Map <ResourceType, Integer> res = cli.getInitialResources(Integer.parseInt(msg.getText()));
                    InitialResRequest request = new InitialResRequest(res);
                    request.setNumPlayer(Integer.parseInt(msg.getText()));
                    request.setPlayer(msg.getActivePlayer());
                    sendMessage(request);
                    break;
            }
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

    private Message receiveMessage() throws IOException{
        Message msg = new Message();
        try{
            msg = (Message) socketIn.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        //System.out.println(msg.getText());
        return msg;
    }
}