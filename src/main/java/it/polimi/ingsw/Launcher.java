package it.polimi.ingsw;

import it.polimi.ingsw.client.ClientMain;
import it.polimi.ingsw.server.ServerMain;

import java.io.IOException;
import java.util.Scanner;

public class Launcher {
    public static void main(String[] args) throws IOException {
        System.out.println("Select the game mode");
        System.out.println("1: Server");
        System.out.println("2: Client CLI");
        System.out.println("3: Client GUI");
        Scanner stdin = new Scanner(System.in);
        int selection = Integer.parseInt(stdin.nextLine());
        switch (selection) {
            case 1: ServerMain.main(args); break;
            case 2: ClientMain.main(args); break;
            case 3: ClientMain.main(args); break;
            default: System.out.println("Invalid selection");
        }
    }
}
