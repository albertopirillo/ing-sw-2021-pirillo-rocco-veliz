package it.polimi.ingsw.network;

import java.io.Serializable;

public class Message implements Serializable {

    private MessageType type;

    private String text;

    private Connection connection;

    public Message(String string) {
        this.text = string; //test funzionamento sendMessage e receiveMessage
    }

    public String getText(){ return this.text;}

}