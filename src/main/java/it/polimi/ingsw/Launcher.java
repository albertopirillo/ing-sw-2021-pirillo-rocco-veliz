package it.polimi.ingsw;

import it.polimi.ingsw.client.ClientMain;
import it.polimi.ingsw.server.ServerMain;

import java.io.IOException;
import java.util.Scanner;

public class Launcher {
    public static void main(String[] args) throws IOException {
        System.out.println("\n********************************");
        System.out.println("Welcome to Master of Renaissance");
        System.out.println("Please select the game mode:");
        System.out.println("1: Server");
        System.out.println("2: Client CLI");
        System.out.println("3: Client GUI");
        System.out.println("4: Local Solo Game");
        System.out.println("********************************");
        System.out.print("Game mode: ");
        Scanner stdin = new Scanner(System.in);
        try {
            int selection = Integer.parseInt(stdin.nextLine());
            System.out.println();
            if (selection == 1) {
                ServerMain.main(args);
            } else if (selection == 2 || selection == 3) {
                int length = args.length;
                String[] newArgs = new String[length + 1];
                System.arraycopy(args, 0, newArgs, 0, length);
                if (selection == 2) newArgs[length] = "--cli";
                else newArgs[length] = "--gui";
                ClientMain.main(newArgs);
            } else if (selection == 4) {
                LocalGameLauncher.main(args);
            } else {
                System.out.println("Invalid selection");
            }
        }  catch (NumberFormatException e) {
            System.out.println("Invalid selection");
        }
    }
}
