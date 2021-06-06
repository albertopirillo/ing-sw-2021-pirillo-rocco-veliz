package it.polimi.ingsw;

import it.polimi.ingsw.server.ServerMain;

import java.io.IOException;

public class ServerLauncher {
    public static void main(String[] args) throws IOException {
        System.out.println("\n********************************");
        System.out.println("Welcome to Master of Renaissance");
        System.out.println("Starting server...");
        System.out.println("********************************\n");
        ServerMain.main(args);
    }
}
