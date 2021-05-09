package it.polimi.ingsw.client;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.network.DepotSetting;
import it.polimi.ingsw.network.Processable;
import it.polimi.ingsw.network.messages.GameSizeMessage;
import it.polimi.ingsw.network.requests.*;
import it.polimi.ingsw.network.updates.*;
import it.polimi.ingsw.utils.ANSIColor;

import java.util.*;

public class ClientCLI extends PlayerInterface {
    private final Scanner stdin;

    public ClientCLI(Client player){
        super(player);
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

    public String chooseNickname(){
        String nickname = "";

        do {
            System.out.println("\nChoose your username (symbols are not allowed): ");

            if (stdin.hasNextLine()){
                nickname = stdin.nextLine();
            }

            if (nickname.equals("")){
                System.out.println("Please choose a valid username" );
            }
        } while (!nickname.matches("[a-zA-Z0-9]+"));

        return nickname;
    }

    public void getGameSize(){
        int gameSize;
        do {
            System.out.println("\nHow many players do you want?");
            System.out.print("[MIN: 1, MAX: 4]: ");
            gameSize = Integer.parseInt(stdin.nextLine());
        } while (gameSize < 1 || gameSize > 4);
        Processable rsp = new GameSizeMessage(getNickname(), gameSize);
        getPlayer().sendMessage(rsp);
    }

    @Override
    public void viewInitialResources(int numPlayer) {
        Map<ResourceType, Integer> res = new HashMap<>();
        switch (numPlayer){
            case 0:
                break;
            case 1:
            case 2:
                System.out.println("\nChoose one initial resource:");
                int numRes = getInitialResources();
                res.put(parseToResourceType(numRes), 1);
                break;
            case 3:
                System.out.println("\nChoose two initial resources:");
                int res1 = getInitialResources();
                int res2 = getInitialResources();
                res.put(parseToResourceType(res1), 1);
                if(res1 == res2) {
                    res.put(parseToResourceType(res2), 2);
                } else {
                    res.put(parseToResourceType(res2), 1);
                }
                break;
            default:
                System.out.println("ko");
                break;
        }
        InitialResRequest request = new InitialResRequest(res);
        request.setNumPlayer(numPlayer);
        request.setPlayer(getNickname());
        getPlayer().sendMessage(request);
    }

    public int getInitialResources(){
        int selection;
        do {
            System.out.println("1: STONE");
            System.out.println("2: COIN");
            System.out.println("3: SHIELD");
            System.out.println("4: SERVANT");
            selection = stdin.nextInt();
        } while (selection<1 || selection>4);
        return selection;
    }

    @Override
    public void viewInitialsLeaderCards(List<LeaderCard> leaderCards) {
        System.out.println("\nYou must select two cards out of four:");
        int index = 0;
        for(LeaderCard leaderCard: leaderCards){
            System.out.println("\nCard " + index++ + " (" + leaderCard.getImg() + "):");
            System.out.println(leaderCard);
        }
        System.out.println();

        int num1 = getInitialLeaderCards(-1);
        int num2 = getInitialLeaderCards(num1);
        ChooseLeaderRequest request = new ChooseLeaderRequest(num1, num2);
        request.setPlayer(getNickname());
        getPlayer().sendMessage(request);
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
            System.out.println(sb);
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

    public void simulateGame() {
        int selection;
        do {
            System.out.println("\nIt's your turn!");
            System.out.println("What do you want to do now?");
            System.out.println("0: Show faith track");
            System.out.println("1: Show leader cards");
            System.out.println("2: Buy from market");
            System.out.println("3: Buy a development card");
            System.out.println("4: Activate basic production");
            System.out.println("5: Activate a development card production");
            System.out.println("6: Use leader card 1");
            System.out.println("7: Use leader card 2");
            System.out.println("8: Discard leader card 1");
            System.out.println("9: Discard leader card 2");
            System.out.println("10: End Turn");
            System.out.println();
            selection = stdin.nextInt();
        } while (selection < 0 || selection > 10);

        Request request = null;
        switch(selection) {
            case 0:
                request = new FaithTrackRequest();
                break;
            case 1:
                request = new LeaderCardsRequest();
                break;
            case 6:
                request = new UseLeaderRequest(0, LeaderAction.USE_ABILITY);
                break;
            case 7:
                request = new UseLeaderRequest(1, LeaderAction.USE_ABILITY);
                break;
            case 8:
                request = new UseLeaderRequest(0, LeaderAction.DISCARD);
                break;
            case 9: //use leader ability request
                request = new UseLeaderRequest(1, LeaderAction.DISCARD);
                break;
            case 10:
                request = new EndTurnRequest();
                break;
            default:
                break;
        }
        if(request != null){
            getPlayer().sendMessage(request);
        } else {
            simulateGame();
        }
    }

    @Override
    public void updateTempResource(TempResourceUpdate update) {

    }

    @Override
    public void updateStorages(StorageUpdate update) {
        Map<String, List<DepotSetting>> depotMap = update.getDepotMap();
        Map<String, Resource> strongboxMap = update.getStrongboxMap();
        for(String playerNick: depotMap.keySet()) {
            System.out.println("Showing " + playerNick + "'s resources:");
            System.out.println("Depot:");
            for(DepotSetting layer: depotMap.get(playerNick)) {
                System.out.print("\t" + layer.getLayerNumber() + ": ");
                if (layer.getResType() == null) System.out.println("Empty");
                else System.out.println(layer.getResType() + " x" + layer.getAmount());
            }
            System.out.println("Strongbox:");
            System.out.println("\t" + strongboxMap.get(playerNick) + "\n");
        }

    }

    @Override
    public void updateLeaderCards(LeaderUpdate update) {
        List<LeaderCard> leaderCards = update.getLeaderMap().get(getNickname());
        if(leaderCards.size() > 0){
            System.out.println("\nYou have the following leader cards:");
            int index = 0;
            for(LeaderCard leaderCard: leaderCards){
                System.out.println("\nCard " + index++ + " (" + leaderCard.getImg() + "):");
                System.out.println("active: " + leaderCard.isActive());
                System.out.println(leaderCard);
            }
            System.out.println();
        } else {
            System.out.println("\nYou have no more leader cards.");
        }
        //simulateGame()
    }

    @Override
    public void updateDevSlots(DevSlotsUpdate update) {
        Map<String, List<DevelopmentSlot>> devSlotList = update.getDevSlotMap();

        for(String playerNick: devSlotList.keySet()) {
            System.out.println("Showing " + playerNick + "'s development slots:");
            int slotNumber = 1;
            for (DevelopmentSlot slot : devSlotList.get(playerNick)) {
                if (slot.getCards().size() == 0) System.out.println("\tSlot number " + slotNumber + " is empty");
                else System.out.println("  - Showing slot number " + slotNumber + ":");
                for (DevelopmentCard card : slot.getCards()) {
                    System.out.println("  >" + card + "\n");
                }
                slotNumber++;
            }
            System.out.println();
        }
    }


    public void updateDevSlotsPretty(DevSlotsUpdate update) {
        Map<String, List<DevelopmentSlot>> devSlotList = update.getDevSlotMap();
        for(String playerNick: devSlotList.keySet()) {
            List<DevelopmentSlot> currentSlots = devSlotList.get(playerNick);
            System.out.println("Showing " + playerNick + "'s development slots:");
            System.out.println("\t\t\t\tSlot number 1:\t\t\t\t\t\t\t\t\t\tSlot number2:\t\t\t\t\t\t\t\t\t\t\tSlot number3:");
            for (int layer = 0; layer < 3; layer++) {
                for(int slot = 0; slot < 3; slot++) {
                    if (currentSlots.get(slot).numberOfElements() > layer)
                        System.out.print("  > Cost: " + currentSlots.get(slot).getCards().get(layer).getCost() + "\t\t\t\t");
                    else System.out.print("\t\t\t\t\t\t\t\t\t\t\t");
                }
                System.out.print("\n\t");
                for(int slot = 0; slot < 3; slot++) {
                    if (currentSlots.get(slot).numberOfElements() > layer)
                        System.out.print("Color: " + currentSlots.get(slot).getCards().get(layer).getType() + "\t\t\t\t\t\t\t\t\t\t");
                    else System.out.print("\t\t\t\t\t\t\t\t\t\t\t\t\t");
                }
                System.out.print("\n\t");
                for(int slot = 0; slot < 3; slot++) {
                    if (currentSlots.get(slot).numberOfElements() > layer)
                        System.out.print("Level: " + currentSlots.get(slot).getCards().get(layer).getLevel() + "\t\t\t\t\t\t\t\t\t\t\t");
                    else System.out.print("\t\t\t\t\t\t\t\t\t\t\t\t\t");
                }
                System.out.print("\n\t");
                for(int slot = 0; slot < 3; slot++) {
                    if (currentSlots.get(slot).numberOfElements() > layer)
                        System.out.print("Production power: " + "\t\t\t\t\t\t\t\t\t");
                    else System.out.print("\t\t\t\t\t\t\t\t\t\t\t\t\t");
                }
                System.out.print("\n\t");
                for(int slot = 0; slot < 3; slot++) {
                    if (currentSlots.get(slot).numberOfElements() > layer)
                        System.out.print("\tInput : " + currentSlots.get(slot).getCards().get(layer).getProdPower().getInput() + "\t\t\t");
                     else System.out.print("\t\t\t\t\t\t\t\t\t\t\t\t\t");
                }
                System.out.print("\n\t");
                for(int slot = 0; slot < 3; slot++) {
                    if (currentSlots.get(slot).numberOfElements() > layer)
                        System.out.print("\tOutput: " + currentSlots.get(slot).getCards().get(layer).getProdPower().getOutput() + "\t");
                    else System.out.print("\t\t\t\t\t\t\t\t\t\t\t\t\t");
                }
                System.out.println("\n");
            }
            System.out.println();
        }
    }

    @Override
    public void displayError(ErrorUpdate update) {
        System.out.println(ANSIColor.RED + "\nReceived an error message from the server:" + ANSIColor.RESET);
        System.out.println(ANSIColor.RED + update.getClientError().getError() + ANSIColor.RESET);
        //simulateGame();
    }

    @Override
    public void updatePlayer(PlayerUpdate update) {

    }

    @Override
    public void updateFaithTrack(FaithTrackUpdate faithTrackUpdate) {
        Map<String, FaithTrack> map = faithTrackUpdate.getFaithTrackInfoMap();
        System.out.println("\nFaith track information: ");
        for(Map.Entry<String, FaithTrack> entry: map.entrySet()){
            System.out.println("\nPlayer: " + entry.getKey());
            System.out.println(entry.getValue().toString());
        }
        simulateGame();
    }

    @Override
    public void updateMarket(MarketUpdate update) {
        List<DevelopmentCard> devCards = update.getDevCardList();
        int index = 0;
        for(DevelopmentCard devCard: devCards){
            System.out.println("\nCard " + index++ + " (url.pdf) ");
            System.out.println(devCard);
        }
        System.out.println();
        //simulateGame();
    }

    @Override
    public void updateMarketTray(MarketTrayUpdate update) {
        System.out.println("\nMarket: ");
        System.out.println(update);
        //simulateGame();
    }
}