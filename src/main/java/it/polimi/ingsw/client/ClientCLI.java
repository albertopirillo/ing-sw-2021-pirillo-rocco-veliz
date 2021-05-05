package it.polimi.ingsw.client;

import it.polimi.ingsw.model.*;

import java.util.*;

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
        System.out.println("1: STONE");
        System.out.println("2: COIN");
        System.out.println("3: SHIELD");
        System.out.println("4: SERVANT");
        return stdin.nextInt();
    }

    public void viewInitialsLeadersCards(List<LeaderCard> leaderCards){
        System.out.println("You must select two cards out of four:\n");
        int index = 0;
        for(LeaderCard leaderCard: leaderCards){
            System.out.println("\nCard " + index++ + " (" + leaderCard.getImg() + "):");
            LeaderAbility leaderAbility = leaderCard.getSpecialAbility();
            if(leaderCard.getLeaderCardType() == LeaderCardType.RES){
                /*ResLeaderCard resLeaderCard = (ResLeaderCard)leaderCard;
                System.out.println("\tCost: " + resLeaderCard.getCost().toString());
                ExtraSlot extraSlot = (ExtraSlot)leaderAbility;
                System.out.println("\tAbility");
                System.out.println("\t\tType: " + extraSlot.getLeaderAbilityType().toString());
                System.out.println("\t\tResource: " + extraSlot.getResource().toString());*/
                System.out.println(leaderCard.toString());
            } else {
                DevLeaderCard devLeaderCard = (DevLeaderCard)leaderCard;
                List<LeaderDevCost> requires = devLeaderCard.getRequires();
                System.out.println("\tRequires: ");

                for(LeaderDevCost leaderDevCost: requires){
                    System.out.println("\t\tColor: " + leaderDevCost.getColor().name());
                    System.out.println("\t\tLevel: " + leaderDevCost.getLevel());
                    System.out.println("\t\tAmount: " + leaderDevCost.getAmount());
                }
                LeaderAbilityType leaderAbilityType = leaderAbility.getLeaderAbilityType();
                System.out.println("\tAbility");
                System.out.println("\t\tType: " + leaderAbilityType.toString());
                switch(leaderAbilityType){
                    case CHANGE:
                        ChangeWhiteMarbles changeWhiteMarbles = (ChangeWhiteMarbles)leaderAbility;
                        System.out.println("\t\tResource: " + changeWhiteMarbles.getResourceType().toString());
                        break;
                    case DISCOUNT:
                        Discount discount = (Discount)leaderAbility;
                        System.out.println("\t\tResource: " + discount.getResource().toString());
                        break;
                    case PRODUCTION:
                        ExtraProduction extraProduction = (ExtraProduction)leaderAbility;
                        ProductionPower production = extraProduction.getProduction();
                        System.out.println("\t\tInput: " + production.getInput().toString());
                        System.out.println("\t\tOutput: " + production.getOutput().toString());
                        break;
                    default:
                        break;
                }
            }
            System.out.println("\tVictoryPoints: " + leaderCard.getVictoryPoints());
        }
        System.out.println();
    }

    public int getInitialLeaderCards(int exclude){
        List<Integer> list = new ArrayList<>();
        for(int i = 0; i < 4; i++){
            if(i != exclude){
                list.add(i);
            }
        }
        StringBuilder sb = new StringBuilder("Choose card number [");
        String sep = "";
        for(Integer element: list){
            sb.append(sep).append(element);
            sep = ", ";
        }
        sb.append("]:");
        int selection;
        do {
            System.out.println(sb.toString());
            selection = stdin.nextInt();
        } while (!list.contains(selection));
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