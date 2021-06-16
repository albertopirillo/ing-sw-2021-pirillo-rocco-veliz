package it.polimi.ingsw.server;

import java.io.IOException;

/**
 * <p>Creates a new generic Server</p>
 * <p>Processes program arguments</p>
 * <p>"--port" can be used to set the port to use</p>
 */
public class ServerMain {

    public static void main(String[] args) throws IOException {
        int port = 8080;
        if (args.length == 1 && args[0].startsWith("--")) {
            args[0] = (String) args[0].subSequence(2, args[0].length());
            try {
                port = Integer.parseInt(args[0]);
            } catch (Exception e) {
                System.out.println("Invalid value, using default port");
                port = 8080;
            }
        }
        Server server = new SocketServer(port);
        server.run();
    }
}