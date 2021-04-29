package it.polimi.ingsw.network;

import it.polimi.ingsw.server.Connection;
import it.polimi.ingsw.server.Server;

import java.io.Serializable;

public class Message implements Serializable, Processable {

    private MessageType type;
    private String text;
    private String activePlayer;

    public Message(){}
    public Message(int gameSize){
        this.text = String.valueOf(gameSize);
    }

    public Message(String string) {
        this.text = string; //test funzionamento sendMessage e receiveMessage
    }

    public String getText(){ return this.text;}

    public MessageType getType() {
        return this.type;
    }

    public void setType(MessageType type){
        this.type = type;
    }

    public String getActivePlayer() {
        return activePlayer;
    }

    public void setActivePlayer(String activePlayer) {
        this.activePlayer = activePlayer;
    }
    public void process(Server server, Connection connection) {
        server.handleMessage(this, connection);
    }
}