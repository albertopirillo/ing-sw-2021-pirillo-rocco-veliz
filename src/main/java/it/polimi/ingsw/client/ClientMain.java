package it.polimi.ingsw.client;

public class ClientMain {
    public static void main(String[] args) {
        int port = 8080;
        String serverIP = "localhost";
        boolean gui = false;
        for (String command : args) {
            if (command.startsWith("-")) {
                if (command.equals("-gui")) {
                    gui = true;
                } else if (command.equals("-cli")) {
                    gui = false;
                } else {
                    try {
                        int separator = command.lastIndexOf(":");
                        serverIP = (String) command.subSequence(1, separator);
                        port = Integer.parseInt((String) command.subSequence(separator + 1, command.length()));
                    } catch (NumberFormatException e) {
                        port = 8080;
                    }
                }
            }
        }
        System.out.println("Server IP: " + serverIP);
        System.out.println("Using port: " + port);
        System.out.println("Starting " + (gui ? "GUI..." : "CLI..."));
        Client client = new Client(gui, port, serverIP);
        client.run();
    }
}