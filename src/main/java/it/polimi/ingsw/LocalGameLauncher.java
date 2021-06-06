package it.polimi.ingsw;

import it.polimi.ingsw.client.LocalClient;
import it.polimi.ingsw.server.LocalServer;
import it.polimi.ingsw.server.Server;

import java.util.Scanner;

public class LocalGameLauncher {
    public static void main(String[] args) {
        boolean gui;
        System.out.println("\n********************************");
        System.out.println("Please select the desired interface:");
        System.out.println("1: CLI");
        System.out.println("2: GUI");
        System.out.println("********************************");
        System.out.print("Interface: ");
        Scanner stdin = new Scanner(System.in);
        try {
            int selection = Integer.parseInt(stdin.nextLine());
            System.out.println();
            switch (selection) {
                case 1 -> gui = false;
                case 2 -> gui = true;
                default -> {
                    System.out.println("Invalid selection");
                    return;
                }
            }
            System.out.println("Starting " + (gui ? "GUI..." : "CLI..."));
            LocalClient client = new LocalClient(gui);
            Server server = new LocalServer(client);
            server.run();
            client.run();
        }  catch (NumberFormatException e) {
            System.out.println("Invalid selection");
        }
    }
}
