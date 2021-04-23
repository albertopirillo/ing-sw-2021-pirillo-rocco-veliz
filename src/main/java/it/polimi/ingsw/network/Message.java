package it.polimi.ingsw.network;

import java.io.Serializable;

public class Message implements Serializable, Processable {

    private MessageType type;

    private String text;

    private Connection connection;

    public Message(String string) {
        this.text = string; //test funzionamento sendMessage e receiveMessage
    }

    public String getText(){ return this.text;}

    public MessageType getType() {
        return this.type;
    }

    public void process(Server server) {
        server.handleMessage(this);
    }
}