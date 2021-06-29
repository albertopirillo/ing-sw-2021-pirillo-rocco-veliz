package it.polimi.ingsw.network;

import it.polimi.ingsw.server.Connection;
import it.polimi.ingsw.server.Server;

/**
 * Each message that the client sends to the server has the interface "processable"<br>
 * Each message has a specified goal and must be to processed by the server
 */
public interface Processable {

    void process(Server server, Connection conn);
}
