package it.polimi.ingsw.network;

import it.polimi.ingsw.client.Client;

public class ClientMain {

    public static void main(String[] args) {
        int port = 8080;
        boolean gui = false;
        for (String command : args) {
            if (command.startsWith("-")) {
                if (command.equals("-gui")) {
                    gui = true;
                } else if (command.equals("-cli")) {
                    gui = false;
                } else {
                    try {
                        port = Integer.parseInt((String) command.subSequence(1, command.length()));
                    } catch (NumberFormatException e) {
                        port = 8080;
                    }
                }
            }
        }
        System.out.println("Using port: " + port);
        System.out.println("Starting " + (gui ? "GUI..." : "CLI..."));
        Client client = new Client(port, gui);
        client.run();
    }
}