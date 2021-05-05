package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.network.Processable;
import it.polimi.ingsw.network.updates.ServerUpdate;
import it.polimi.ingsw.server.Connection;
import it.polimi.ingsw.server.Server;

import java.io.Serializable;

public abstract class Message extends ServerUpdate implements Serializable, Processable {

    public Message(String activePlayer) {
        super(activePlayer);
    }

    public abstract void process(Server server, Connection connection);
}