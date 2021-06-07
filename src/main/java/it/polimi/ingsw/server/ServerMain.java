package it.polimi.ingsw.server;

import java.io.IOException;

public class ServerMain {

    public static void main(String[] args) throws IOException {
        int port = 8080;
        if (args.length == 1 && args[0].startsWith("-")) {
            args[0] = (String) args[0].subSequence(1, args[0].length());
            try {
                port = Integer.parseInt(args[0]);
            } catch (Exception e) {
                port = 8080;
            }
        }
        Server server = new SocketServer(port);
        server.run();
    }
}