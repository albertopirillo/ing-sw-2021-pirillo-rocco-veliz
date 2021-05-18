package it.polimi.ingsw.network;

import it.polimi.ingsw.client.Client;

public class ClientMain {

    public static void main(String[] args){
        Client client;
        int port = 8080;
        boolean gui = false;
        if (args.length > 0 && args.length <= 2) {
            if (args[0].equalsIgnoreCase("gui")) {
                gui = true;
                System.out.println("Starting GUI...");
            } else {
                System.out.println("Using default UI (CLI)");
            }
            if (args.length == 2) {
                try {
                    port = Integer.parseInt(args[1]);
                    System.out.println("Using port: " + port);
                } catch (Exception e) {
                    System.out.println("Using default port (8080)");
                }
            }
            client = new Client(port, gui);
        }
        else {
            System.out.println("Using default port (8080)");
            System.out.println("Using default UI (CLI)");
            client = new Client();
        }

        client.run();

    }
}