package it.polimi.ingsw.network;

import it.polimi.ingsw.server.Server;

import java.io.IOException;

public class ServerMain {

    public static void main(String[] args) throws IOException {
        Server server;
        if (args.length == 1) {
            int port = Integer.parseInt(args[0]);
            server = new Server(port);
        }
        else {
            System.out.println("Using default port (8080)");
            server = new Server();
        }
        server.run();
    }
}