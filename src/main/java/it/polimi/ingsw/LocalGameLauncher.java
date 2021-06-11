package it.polimi.ingsw;

import it.polimi.ingsw.client.LocalClient;
import it.polimi.ingsw.server.LocalServer;
import it.polimi.ingsw.server.Server;

import java.util.Scanner;

public class LocalGameLauncher {
    public static void main(String[] args) {
        boolean gui = false;
        System.out.println("\n*********************************");
        System.out.println("Welcome to Master of Renaissance");
        if (args.length == 0) {
            System.out.println("Please select the game mode:");
            System.out.println("1: CLI");
            System.out.println("2: GUI");
            System.out.print("Game mode: ");
            Scanner stdin = new Scanner(System.in);
            try {
                int selection = Integer.parseInt(stdin.nextLine());
                switch (selection) {
                    case 1 -> { }
                    case 2 -> gui = true;
                    default -> {
                        System.out.println("Invalid selection");
                        return;
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid selection");
                return;
            }
        }
        else {
            for (String command : args) {
                if (command.startsWith("-")) {
                    if (command.equals("-gui")) {
                        gui = true;
                    } else if (command.equals("-cli")) {
                        gui = false;
                    }
                }
            }
        }
        System.out.println("Starting game...");
        System.out.println("*********************************\n");
        System.out.println("Starting " + (gui ? "GUI..." : "CLI..."));
        LocalClient client = new LocalClient(gui);
        Server server = new LocalServer(client);
        server.run();
        client.run();
    }
}
