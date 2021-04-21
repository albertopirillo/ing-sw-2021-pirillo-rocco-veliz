package it.polimi.ingsw.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client implements Runnable{

    private static Socket socket;
    private static String ip;
    private static int port; //TODO change static port
    private static ObjectInputStream socketIn;
    private static ObjectOutputStream socketOut;

    public Client() {
        Client.port = 8080;
        Client.ip = "127.0.0.1";
    }


    public void sendMessage(Message message) throws IOException {
        socketOut.writeObject(message);

        socketOut.reset();
    }

    private void receiveMessage() throws IOException{
        Message msg = new Message("nomessage");
        try{
            msg = (Message) socketIn.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println(msg.getText());
    }


    @Override
    public void run() {
        try {
            startConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }

        while(!Thread.currentThread().isInterrupted()){
            Scanner scanner = new Scanner(System.in);
            String string = scanner.nextLine();
            Message msg = new Message(string);
            try {
                sendMessage(msg);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try{
                receiveMessage();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }



    private void startConnection() throws IOException{
        socket = new Socket(ip, port);
        System.out.println("Connected to localhost");
        socketOut = new ObjectOutputStream(socket.getOutputStream());
        socketIn = new ObjectInputStream(socket.getInputStream());
    }
}