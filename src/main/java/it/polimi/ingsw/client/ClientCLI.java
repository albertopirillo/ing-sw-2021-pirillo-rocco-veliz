package it.polimi.ingsw.client;

import it.polimi.ingsw.model.LeaderCard;
import it.polimi.ingsw.model.ResourceType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ClientCLI {
    private final Scanner stdin;
    private String nickname;
    //private Board boardState;

    public ClientCLI(){
        this.stdin = new Scanner(System.in);
    }

    public void setup(){
        System.out.println("Game is starting...\n");
    }

    public String getIP(){
        System.out.println("Choose ip address: ");
        return stdin.next();
    }

    public int getPort(){
        System.out.println("Choose socket port: ");
        return stdin.nextInt();
    }

    public String getNickname(){
        String nickname = "";

        do {
            System.out.println("Choose your username (symbols are not allowed): ");

            if (stdin.hasNextLine()){
                nickname = stdin.nextLine();
            }

            if (nickname.equals("")){
                System.out.println("Please choose a valid username" );
            }
        } while (!nickname.matches("[a-zA-Z0-9]+"));

        return nickname;
    }

    public int getGameSize(){
        int gameSize = 0;

        do{
            System.out.println("How many players do you want?");
            System.out.print("[MIN: 2, MAX: 4]: ");
            gameSize = Integer.parseInt(stdin.nextLine());
        }while (gameSize < 2 || gameSize > 4);

        return gameSize;
    }

    public Map<ResourceType, Integer> getInitialResources(int numPlayer) {
        Map<ResourceType, Integer> res = new HashMap<>();
        switch (numPlayer){
            case 1:
            case 2:
                System.out.println("Choose one initial resource");
                int numRes = viewInitialResources();
                res.put(parseToResourceType(numRes),1);
                break;
            case 3:
                System.out.println("Choose two initial resources");
                int res1 = viewInitialResources();
                int res2 = viewInitialResources();
                res.put(parseToResourceType(res1),1);
                if(res1==res2) res.put(parseToResourceType(res2),2);
                else res.put(parseToResourceType(res2),1);
                break;
            default:
                System.out.println("ko");
                break;
        }
        return res;
    }

    public int viewInitialResources(){
        System.out.println("1) STONE");
        System.out.println("2) COIN");
        System.out.println("3) SHIELD");
        System.out.println("4) SERVANT");
        return stdin.nextInt();
    }

    public void viewInitialsLeadersCars(List<LeaderCard> leaderCards){
        System.out.println("Please choose 2 cards to keep out of the "+leaderCards.size()+" I've chosen for you\n");
    }

    public int getInitialLeaderCards(){
        int selection;

        do {
            System.out.println("Choose a card");
            System.out.println("0) The first");
            System.out.println("1) The second");
            System.out.println("2) The third");
            System.out.println("3) The fourth\n");
            selection = stdin.nextInt();
        } while (selection<0 || selection>3);

        return selection;
    }

    public ResourceType parseToResourceType(int choice){
        switch (choice){
            case 1: return ResourceType.STONE;
            case 2: return ResourceType.COIN;
            case 3: return ResourceType.SHIELD;
            case 4: return ResourceType.SERVANT;
            default: break;
        }
        return null;
    }

    public String simulateGame() {
        String selection;

        do{
            System.out.println("It's your turn!");
            System.out.println("What do you want to do now? //per ora passa il turno e basta");
            System.out.println("0: Buy from market");
            System.out.println("1: Buy a development card");
            System.out.println("2: Discard leader card");
            System.out.println("3: Activate basic production");
            System.out.println("4: Activate a development card production");
            System.out.println("5: Use a leader card ability");
            System.out.println("6: End Turn\n");
            selection = stdin.nextLine();
        } while (!selection.matches("[0-6]"));

        switch(selection) {
            case "0": //call buy marbles from market request
                break;
            case "1": //call buy dev card request
                break;
            case "2": //call discard leader card request
                break;
            case "3": //activate basic production request
                break;
            case "4": //activate dev card production request
                break;
            case "5": //use leader ability request
                break;
            case "6": //end turn
                break;
        }
        return selection;
    }
}