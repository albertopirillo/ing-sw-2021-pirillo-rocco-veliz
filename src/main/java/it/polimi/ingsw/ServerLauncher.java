package it.polimi.ingsw;

import it.polimi.ingsw.server.Server;
import it.polimi.ingsw.server.ServerMain;
import it.polimi.ingsw.server.SocketServer;

import java.io.IOException;
import java.util.Scanner;

public class ServerLauncher {
    public static void main(String[] args) throws IOException {
        int port;
        System.out.println("\n********************************");
        System.out.println("Welcome to Master of Renaissance");
        if(args.length == 0) {
            System.out.print("Enter the server port: ");
            try {
                Scanner stdin = new Scanner(System.in);
                port = Integer.parseInt(stdin.nextLine());
            } catch (Exception e) {
                port = 8080;
            }
            System.out.println("Starting server...");
            System.out.println("********************************\n");
            Server server = new SocketServer(port);
            server.run();
        }
        else {
            System.out.println("Starting server...");
            System.out.println("********************************\n");
            ServerMain.main(args);
        }
    }
}
