package it.polimi.ingsw;

import it.polimi.ingsw.client.ClientMain;

import java.io.IOException;
import java.util.Scanner;

public class ClientLauncher {
    public static void main(String[] args) throws IOException {
        System.out.println("\n********************************");
        System.out.println("Welcome to Master of Renaissance");
        System.out.println("Please select the game mode:");
        System.out.println("1: Client CLI");
        System.out.println("2: Client GUI");
        System.out.println("********************************\n");
        System.out.print("Game mode: ");
        Scanner stdin = new Scanner(System.in);
        try {
            int length = args.length;
            String[] newArgs = new String[length + 1];
            System.arraycopy(args, 0, newArgs, 0, length);
            int selection = Integer.parseInt(stdin.nextLine());
            if (selection == 1) {
                newArgs[length] = "-cli";
                ClientMain.main(args);
            } else if (selection == 2) {
                newArgs[length] = "-gui";
                ClientMain.main(newArgs);
            } else {
                System.out.println("Invalid selection");
            }
        }  catch (NumberFormatException e) {
            System.out.println("Invalid selection");
        }
    }
}
