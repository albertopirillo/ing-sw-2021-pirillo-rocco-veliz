package it.polimi.ingsw;

import it.polimi.ingsw.client.ClientMain;

import java.io.IOException;
import java.util.Scanner;

public class ClientLauncher {
    public static void main(String[] args) throws IOException {
        Scanner stdin = new Scanner(System.in);
        String[] newArgs;
        System.out.println("\n*****************************************");
        System.out.println("Welcome to Master of Renaissance");

        if (args.length == 0) { //Ask for input
            newArgs = new String[2];
            System.out.print("Please enter the server IP: ");
            String serverIP = stdin.nextLine();
            System.out.print("Please enter the server port: ");
            int port;
            try {
                port = Integer.parseInt(stdin.nextLine());
            } catch (NumberFormatException e) {
                port = 8080;
            }
            newArgs[0] = "-" + serverIP + ":" + port;
            System.out.println("Please select the game mode:");
            System.out.println("1: Client CLI");
            System.out.println("2: Client GUI");
            System.out.print("Game mode: ");
            try {
                int selection = Integer.parseInt(stdin.nextLine());
                System.out.println("Starting Client...");
                System.out.println("*****************************************\n");
                if (selection == 1) {
                    newArgs[1] = "-cli";
                    ClientMain.main(newArgs);
                } else if (selection == 2) {
                    newArgs[1] = "-gui";
                    ClientMain.main(newArgs);
                } else {
                    System.out.println("Invalid selection");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid selection");
            }
        }
        else { //Pass program arguments to ClientMain
            System.out.println("Starting Client...");
            System.out.println("*****************************************\n");
            ClientMain.main(args);
        }
    }
}
