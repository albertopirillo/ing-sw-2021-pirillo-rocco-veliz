package it.polimi.ingsw.network;

import it.polimi.ingsw.server.Connection;
import it.polimi.ingsw.server.Server;

import java.io.Serializable;

public abstract class Message extends ServerUpdate implements Serializable, Processable {

    public Message(String activePlayer, boolean lastUpdate) {
        super(activePlayer, lastUpdate);
    }

    public abstract void process(Server server, Connection connection);
}