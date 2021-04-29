package it.polimi.ingsw.network;

import it.polimi.ingsw.server.Connection;
import it.polimi.ingsw.server.Server;

public interface Processable {

    void process(Server server, Connection conn);
}
