package it.polimi.ingsw.network;

import it.polimi.ingsw.server.Server;

import java.io.IOException;

public class ServerMain {

    public static void main(String[] args) throws IOException {

        Server server = new Server();

        server.run();

    }
}