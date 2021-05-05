package it.polimi.ingsw.client;

import it.polimi.ingsw.model.LeaderCard;
import it.polimi.ingsw.model.ResourceType;
import it.polimi.ingsw.network.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.Map;

public class Client implements Runnable{

    private static Socket socket;
    private final String ip;
    private final int port;
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
            //TODO: Use polymorphism instead of this switch
            /*
            public void readUpdate(ServerUpdate update) {
                this.playerInterface.readUpdate(update);
            }
             */
            switch (msg.getType()){
                case LOBBY_SETUP:
                    int gameSize = cli.getGameSize();
                    Message rsp = new Message(gameSize);
                    rsp.setType(MessageType.LOBBY_SETUP);
                    sendMessage(rsp);
                    break;
                case INITIAL_RESOURCE:
                    if(msg.getActivePlayer().equals(nickname)) {
                        Map<ResourceType, Integer> res = cli.getInitialResources(Integer.parseInt(msg.getText()));
                        InitialResRequest request = new InitialResRequest(res);
                        request.setNumPlayer(Integer.parseInt(msg.getText()));
                        request.setPlayer(nickname);
                        sendMessage(request);
                    }
                    break;
                case INITIAL_CARDS:
                    if(msg.getActivePlayer().equals(nickname)) {
                        CardsMessage message = (CardsMessage) msg;
                        List<LeaderCard> leaderCards = message.getCards();
                        cli.viewInitialsLeadersCards(leaderCards);
                        int num1 = cli.getInitialLeaderCards(-1);
                        int num2 = cli.getInitialLeaderCards(num1);
                        ChooseLeaderRequest request = new ChooseLeaderRequest(num1, num2);
                        request.setPlayer(nickname);
                        sendMessage(request);
                    }
                    break;
                case PLAYER_MOVE:
                    if(msg.getActivePlayer().equals(nickname)) {
                        Request request = new TestRequest();
                        request.setText(cli.simulateGame());
                        sendMessage(request);
                    }
                    break;
                default:
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