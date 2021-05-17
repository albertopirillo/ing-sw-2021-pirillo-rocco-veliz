package it.polimi.ingsw.client;

import it.polimi.ingsw.exceptions.InvalidKeyException;
import it.polimi.ingsw.exceptions.NegativeResAmountException;
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
    private Resource tempRes;

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
            try {
                gameSize = Integer.parseInt(stdin.nextLine());
            } catch(Exception e){
                gameSize = -1;
            }
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

    private int getIntegerSelection(String[] options){
        int selection;
        do {
            for (String option : options) {
                System.out.println(option);
            }
            try {
                selection = Integer.parseInt(stdin.nextLine());
            } catch(Exception e){
                selection = -1;
            }
        } while(selection < 1 || selection > options.length);
        return selection;
    }

    private String getStringSelection(String[] selections, String[] options){
        String selection;
        List<String> selectionList = Arrays.asList(selections);
        do {
            for (String option : options) {
                System.out.println(option);
            }
            try {
                selection = stdin.nextLine();
            } catch(Exception e){
                selection = "";
            }
        } while(!selectionList.contains(selection));
        return selection;
    }

    private int getResourceMenu(){
        String[] options = {"1: STONE", "2: COIN", "3: SHIELD", "4: SERVANT"};
        return getIntegerSelection(options);
    }

    public int getInitialResources(){
        return getResourceMenu();
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
            try {
                selection = Integer.parseInt(stdin.nextLine());
            } catch(Exception e){
                selection = -1;
            }
        } while (!list.contains(selection));
        return selection;
    }

    public int getDevCardsSlot (List<Integer> exclude){
        List<Integer> list = new ArrayList<>();
        for(int i = 1; i < 4; i++){
            if(!exclude.contains(i-1)){
                list.add(i);
            }
        }
        StringBuilder sb = new StringBuilder("Which leader card production do you want to use [");
        String sep = "";
        for(Integer element: list){
            sb.append(sep).append(element);
            sep = ", ";
        }
        sb.append("]:");
        int selection;
        do {
            System.out.println(sb);
            try {
                selection = Integer.parseInt(stdin.nextLine());
            } catch(Exception e){
                selection = -1;
            }
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

    private AbilityChoice parseToAbility(int choice){
        switch (choice){
            case 1: return AbilityChoice.STANDARD;
            case 2: return AbilityChoice.FIRST;
            case 3: return AbilityChoice.SECOND;
            case 4: return AbilityChoice.BOTH;
        }
        return null;
    }

    public int getPosition(int min, int max){
        int position;
        do {
            System.out.print("Choose num of position : [MIN : "+ min + " MAX: " + max + "] ");
            System.out.println("(Press q to abort)");
            String input = stdin.nextLine();
            if (input.equals("q")) return - 1;
            else position = Integer.parseInt(input);
            System.out.println(position);
        }while (position < min || position >max);
        return position;
    }

    public void simulateGame() {
        int selection;
        do {
            System.out.println("\nIt's your turn!");
            System.out.println("What do you want to do now?");
            System.out.println("1: Show faith track");
            System.out.println("2: Show depot and strongbox");
            System.out.println("3: Show leader cards");
            System.out.println("5: Show market development cards");
            System.out.println("6: Show market marbles tray");
            System.out.println("7: Show Development Slots");
            System.out.println("8: Buy from marbles tray");
            System.out.println("9: Buy a development card");
            System.out.println("10: Activate a production");
            System.out.println("13: Leader Card options");
            System.out.println("17: Reorder depot");
            System.out.println("19: End Turn");
            System.out.println("20: Quit Game");
            System.out.println();
            try {
                selection = Integer.parseInt(stdin.nextLine());
            } catch(Exception e){
                selection = -1;
                System.out.println("Invalid selection");
            }
        } while (selection < 1 || selection > 20);

        Request request = null;
        switch(selection) {
            case 1:
                request = new ShowFaithTrackRequest();
                break;
            case 2:
                request = new ShowStoragesRequest();
                break;
            case 3:
                request = new ShowLeaderCardsRequest();
                break;
            /*case 4:
                request = new ShowTempResRequest();
                break; TODO: fix order */
            case 5:
                request = new ShowMarketRequest();
                break;
            case 6:
                request = new ShowMarketTrayRequest();
                break;
            case 7:
                request = new ShowDevSlotsRequest();
                break;
            case 8:
                int position = getPosition(0,6);
                if (position != -1) {
                    request = new InsertMarbleRequest(position);
                }
                break;
            case 9:
                request = buyDevCardMenu();
                break;
            case 10:
                request = productionMenu();
                break;
            case 13:
                request = UseLeaderMenu();
                break;
            case 17:
                request = reorderDepotMenu();
                break;
            case 19:
                request = new EndTurnRequest();
                break;
            case 20:
                request = new QuitGameRequest();
                break;
        }
        if(request != null){
            getPlayer().sendMessage(request);
        } else {
            simulateGame();
        }
    }

    private void errorPrint(String str) {
        System.out.println(ANSIColor.RED + str + ANSIColor.RESET);
    }

    private Request buyDevCardMenu() {
        Resource depotResource = new Resource(0, 0, 0, 0);
        Resource strongboxResource = new Resource(0, 0, 0, 0);
        String[] abilityOptions = {"1: NO ABILITY", "2: FIRST ABILITY", "3: SECOND ABILITY", "4: BOTH ABILITY"};
        String[] levelOptions = {"1: Level 1", "2: Level 2", "3: Level 3"};
        String[] colorOptions = {"1: GREEN", "2: BLUE", "3: YELLOW", "4: PURPLE"};
        String[] slotOptions = {"1: Slot 1", "2: Slot 2", "3: Slot 3"};
        String[] options = {"STONE", "COIN", "SHIELD", "SERVANT"};
        while(true) {
            try {
                System.out.println("Choose what ability you want to use");
                int ability = getIntegerSelection(abilityOptions);
                System.out.println("Choose the DevCard's level");
                int level = getIntegerSelection(levelOptions);
                System.out.println("Choose the DevCard's color");
                int color = getIntegerSelection(colorOptions);
                System.out.println("Choose the slot's number");
                int slot = getIntegerSelection(slotOptions);
                System.out.println("\nSelect where are you taking the resource from");
                for (String option : options) {
                    System.out.println(option);
                    System.out.println("How many resources to take from Depot");
                    int amountDepot = Integer.parseInt(stdin.nextLine());
                    if (amountDepot < 0 || amountDepot > 3) throw new Exception();
                    System.out.println("How many resources to take from Strongbox");
                    int amountStrongbox = Integer.parseInt(stdin.nextLine());
                    if (amountStrongbox < 0) throw new Exception();
                    depotResource.modifyValue(strToResType(option.toLowerCase()),amountDepot);
                    strongboxResource.modifyValue(strToResType(option.toLowerCase()),amountStrongbox);
                }
                return new BuyDevCardRequest(level,CardColor.parseColorCard(color), parseToAbility(ability),depotResource, strongboxResource, slot-1);
            } catch (Exception e){
                errorPrint("Invalid input, retry");
            }
        }
    }

    private Request productionMenu(){
        Request request = null;

        String[] options = {"b: Use a Basic Production", "d: Use a Development Card Production", "e: Use a Leader Card Extra Production", "q: Exit this menu"};
        String[] selections = {"b", "d", "e", "q"};

        System.out.println("\nWhat type of production do you want to activate?");
        String selection = getStringSelection(selections, options);

        if (selection.equals("b")) {
            request = basicProductionMenu();
        } else if (selection.equals("d")) {
            request = devProductionMenu();
        } else if (selection.equals("e")) {
            request = extraProductionMenu();
        }
        return request;
    }

    private Request basicProductionMenu(){
        Request request = null;
        Resource depotResource = new Resource(0, 0, 0, 0);
        Resource strongboxResource = new Resource(0, 0, 0, 0);

        String[] options = {"d: DEPOT", "s: STRONGBOX"};
        String[] selections = {"d", "s"};

        System.out.println("\nSelect first input resource type:");
        ResourceType input1 = parseToResourceType(getResourceMenu());
        System.out.println("\nWhere are you taking this resource from:");
        String inputPlace1 = getStringSelection(selections, options);
        System.out.println("\nSelect second input resource type:");
        ResourceType input2 = parseToResourceType(getResourceMenu());
        System.out.println("\nWhere are you taking this resource from:");
        String inputPlace2 = getStringSelection(selections, options);
        System.out.println("\nSelect output resource type:");
        ResourceType output = parseToResourceType(getResourceMenu());

        try {
            if(inputPlace1.equals("d")) {
                depotResource.modifyValue(input1, 1);
            } else if(inputPlace1.equals("s")){
                strongboxResource.modifyValue(input1, 1);
            }
            if(inputPlace2.equals("d")) {
                depotResource.modifyValue(input2, 1);
            } else if(inputPlace2.equals("s")){
                strongboxResource.modifyValue(input2, 1);
            }
            request = new BasicProductionRequest(input1, input2, output, depotResource, strongboxResource);
        } catch (InvalidKeyException | NegativeResAmountException e) {
            System.out.println("Invalid input, retry");
        }
        return request;
    }

    private Request extraProductionMenu() {
        Request request;

        Resource depotResource = new Resource(0, 0, 0, 0);
        Resource strongboxResource = new Resource(0, 0, 0, 0);
        String[] abilityOptions = {"1: FIRST", "2: SECOND",/*"3: BOTH",*/ "3: Exit this menu"};
        String[] options = {"STONE", "COIN", "SHIELD", "SERVANT"};

        System.out.println("\nWhat leader card production do you want to use");
        int ability = getIntegerSelection(abilityOptions);
        if (ability == 3){
            return null;
        } else {
            System.out.println("\nWhere do you want to get the resources from?");
            try{
                for (String option : options) {
                    System.out.println(option);
                    System.out.println("How many resources to take from Depot");
                    int amountDepot = Integer.parseInt(stdin.nextLine());
                    if (amountDepot < 0 || amountDepot > 3) throw new Exception();
                    System.out.println("How many resources to take from Strongbox");
                    int amountStrongbox = Integer.parseInt(stdin.nextLine());
                    if (amountStrongbox < 0) throw new Exception();
                    depotResource.modifyValue(strToResType(option.toLowerCase()),amountDepot);
                    strongboxResource.modifyValue(strToResType(option.toLowerCase()),amountStrongbox);
                }
            } catch (Exception e){
                errorPrint("Invalid input, retry");
            }
            System.out.println("\nWhat resource do you want to get?");
            ResourceType res = parseToResourceType(getResourceMenu());

            request = new ExtraProductionRequest(parseToAbility(ability+1), depotResource, strongboxResource, res);
        }


        return request;
    }

    private Request devProductionMenu(){
        Request request = null;

        List<Integer> cards = new ArrayList<>();
        Resource depotResource = new Resource(0, 0, 0, 0);
        Resource strongboxResource = new Resource(0, 0, 0, 0);
        String[] amountOptions = {"1", "2", "3", "4: Quit this menu"};
        String[] resourceOptions = {"STONE", "COIN", "SHIELD", "SERVANT"};

        System.out.println("\nHow many development card production do you want to use?");
        int amount = getIntegerSelection(amountOptions);

        if(amount==4){
            return null;
        } else {
            for (int i=0; i<amount; i++){

                System.out.println("\nWhat leader card production do you want to use");
                int card = getDevCardsSlot(cards);

                cards.add(card-1);
                System.out.println("\nSelect the input resources:");
                try{
                    for (String option : resourceOptions) {
                        System.out.println(option);
                        System.out.println("\nHow many resources to take from Depot");
                        int amountDepot = Integer.parseInt(stdin.nextLine());
                        if (amountDepot < 0 || amountDepot > 3) throw new Exception();
                        System.out.println("\nHow many resources to take from Strongbox");
                        int amountStrongbox = Integer.parseInt(stdin.nextLine());
                        if (amountStrongbox < 0) throw new Exception();
                        depotResource.modifyValue(strToResType(option.toLowerCase()),amountDepot);
                        strongboxResource.modifyValue(strToResType(option.toLowerCase()),amountStrongbox);
                    }
                } catch (Exception e){
                    errorPrint("Invalid input, retry");
                }
            }
        }

        request = new DevProductionRequest(cards, depotResource, strongboxResource);

        return request;
    }

    private Request UseLeaderMenu() {
        Request request = null;

        String[] firstActionOptions = {"1: Activate a leader card", "2: Discard a leader card", "3: Exit this menu"};
        String[] firstSelections = {"a", "d", "q"};
        String[] leaderOptions = {"1: The first", "2: The second"};

        System.out.println("\nWhat do you want to do with you leader cards?");
        int selection = getIntegerSelection(firstActionOptions);

        if (selection == 1) {
            System.out.println("\nWhich leader card do you want to activate?");
            int leader = getIntegerSelection(leaderOptions);
            request = new UseLeaderRequest(leader-1, LeaderAction.USE_ABILITY);
        } else if (selection == 2) {
            System.out.println("\nWhich leader card do you want to activate?");
            int leader = getIntegerSelection(leaderOptions);
            request = new UseLeaderRequest(leader-1, LeaderAction.DISCARD);
        }
        return request;
    }

    @Override
    public void updateTempResource(TempResourceUpdate update) {
        //Calling selectPlayerList() is not needed
        Resource resource = update.getResource();
        if (resource == null || resource.getActualSize() == 0) {
            this.tempRes = null;
            System.out.println("\nThere are no resources from the market that need to be placed");
            this.simulateGame();
        }
        else {
            System.out.println("\nResources obtained from the Market that need to be placed: ");
            System.out.println(resource);
            this.tempRes = resource;
            Request request = this.placeResourceMenu();
            this.getPlayer().sendMessage(request);
        }
    }

    private Request placeResourceMenu() {
        int amountSelection, layerSelection;
        List<DepotSetting> toPlace = new ArrayList<>();
        Resource toDiscard = new Resource(0,0,0,0);
        try {
            for(ResourceType key: this.tempRes.keySet()) {
                if (tempRes.getValue(key) != 0) {
                    System.out.println("\nYou have " + key + "x" + tempRes.getValue(key));
                    System.out.println("How many resources to discard?");
                    amountSelection = Integer.parseInt(stdin.nextLine());
                    toDiscard.modifyValue(key, amountSelection);
                    tempRes.modifyValue(key, -amountSelection);

                    while (tempRes.getValue(key) > 0) {
                        System.out.println("How many resources to place? (Remaining: " + tempRes.getValue(key) + ")");
                        amountSelection = Integer.parseInt(stdin.nextLine());
                        System.out.println("In which layer would you like to place them?");
                        layerSelection = Integer.parseInt(stdin.nextLine());
                        toPlace.add(new DepotSetting(layerSelection, key, amountSelection));
                        tempRes.modifyValue(key, -amountSelection);
                    }
                }
            }
        }   catch (Exception e) {
            System.out.println("Invalid input, retry");
        }
        return new PlaceResourceRequest(toDiscard, toPlace);
    }

    public ResourceType strToResType(String input){
        switch (input) {
            case "stone": return ResourceType.STONE;
            case "coin": return ResourceType.COIN;
            case "shield": return ResourceType.SHIELD;
            case "servant": return ResourceType.SERVANT;
            default: return null;
        }
    }

    private ReorderDepotRequest reorderDepotMenu() {
        int fromLayer, toLayer, amount;
        while(true) {
            try {
                System.out.println("Move resources FROM which layer? (Press q to abort)");
                String input = stdin.next();
                if (input.equals("q")) return null;
                fromLayer = Integer.parseInt(input);
                System.out.println("Move resources TO which layer?");
                toLayer = Integer.parseInt(stdin.nextLine());
                System.out.println("How many resources would you like to move?");
                amount = Integer.parseInt(stdin.nextLine());
                return new ReorderDepotRequest(fromLayer, toLayer, amount);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input, retry");
            }
        }
    }

    /**
     * Choose whose player information should be printed
     * @param playerList    the complete list of players received with the update
     * @param activePlayer  the activePlayer received with the update
     * @return  playerList if the owner of the client is the active player,
     * otherwise a list with the active player only
     */
    public Set<String> selectPlayerList(Set<String> playerList, String activePlayer) {
        if (this.getNickname().equals(activePlayer)) {
            return playerList;
        }
        else {
            Set<String> newSet = new HashSet<>();
            newSet.add(activePlayer);
            return newSet;
        }
    }

    @Override
    public void updateStorages(StorageUpdate update) {
        Set<String> toPrint = this.selectPlayerList(update.getStrongboxMap().keySet(), update.getActivePlayer());
        Map<String, List<DepotSetting>> depotMap = update.getDepotMap();
        Map<String, Resource> strongboxMap = update.getStrongboxMap();
        for(String playerNick: toPrint) {
            System.out.println("\nShowing " + playerNick + "'s resources:");
            System.out.println("Depot:");
            for(DepotSetting layer: depotMap.get(playerNick)) {
                System.out.print("\t" + layer.getLayerNumber() + ": ");
                if (layer.getResType() == null) System.out.println("Empty");
                else System.out.println(layer.getResType() + " x" + layer.getAmount());
            }
            System.out.print("Strongbox: ");
            if(strongboxMap.get(playerNick).getTotalAmount() == 0) System.out.println("Empty");
            else System.out.println("\n\t" + strongboxMap.get(playerNick) + "\n");
        }
    }

    @Override
    public void updateLeaderCards(LeaderUpdate update) {
        Set<String> toPrint = this.selectPlayerList(update.getLeaderMap().keySet(), update.getActivePlayer());
        Map<String, List<LeaderCard>> leaderMap = update.getLeaderMap();
        for(String playerNick: toPrint) {
            List<LeaderCard> leaderCards = leaderMap.get(playerNick);
            if (leaderCards.size() > 0) {
                System.out.println("\nThe player " + playerNick + " has the following leader cards:");
                int index = 0;
                for (LeaderCard leaderCard : leaderCards) {
                    if(playerNick.equals(this.getNickname()) || leaderCard.isActive()) {
                        System.out.println("\nCard " + index++ + " (" + leaderCard.getImg() + "):");
                        System.out.println("active: " + leaderCard.isActive());
                        System.out.println(leaderCard);
                    }
                    else {
                        System.out.println("\nThis card is hidden");
                    }
                }
                System.out.println();
            } else {
                System.out.println("\nYou have no more leader cards.");
            }
        }
    }

    @Override
    public void updateDevSlots(DevSlotsUpdate update) {
        Set<String> toPrint = this.selectPlayerList(update.getDevSlotMap().keySet(), update.getActivePlayer());
        Map<String, List<DevelopmentSlot>> devSlotList = update.getDevSlotMap();
        for(String playerNick: toPrint) {
            System.out.println("\nShowing " + playerNick + "'s development slots:");
            int slotNumber = 1;
            for (DevelopmentSlot slot : devSlotList.get(playerNick)) {
                if (slot.getCards().size() == 0) System.out.println("\tSlot number " + slotNumber + " is empty");
                else System.out.println("  - Showing slot number " + slotNumber + ":");
                for (DevelopmentCard card : slot.getCards()) {
                    System.out.println("  >" + card + "\n");
                }
                slotNumber++;
            }
            System.out.println("\n");
        }
    }

    public void updateDevSlotsPretty(DevSlotsUpdate update) {
        Set<String> toPrint = this.selectPlayerList(update.getDevSlotMap().keySet(), update.getActivePlayer());
        Map<String, List<DevelopmentSlot>> devSlotList = update.getDevSlotMap();
        for(String playerNick: toPrint) {
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
        if(update.getActivePlayer().equals(this.getNickname())) {
            System.out.println(ANSIColor.RED + "\nReceived an error message from the server:" + ANSIColor.RESET);
            System.out.println(ANSIColor.RED + update.getClientError().getError() + ANSIColor.RESET + "\n");
        }
    }

    @Override
    public void updateFaithTrack(FaithTrackUpdate faithTrackUpdate) {
        Map<String, FaithTrack> map = faithTrackUpdate.getFaithTrackInfoMap();
        System.out.println("\nFaith track information: ");
        for(Map.Entry<String, FaithTrack> entry: map.entrySet()){
            System.out.println("\nPlayer: " + entry.getKey());
            System.out.println(entry.getValue().toString());
        }
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
    }

    @Override
    public void updateMarketTray(MarketTrayUpdate update) {
        System.out.println("\nShowing Market tray: ");
        System.out.println(update);
    }

    @Override
    public void updateDiscardedCards(DiscardedCardsUpdate update) {
        int index = 1;
        System.out.print("\nLorenzo discarded the following cards: ");
        for(DevelopmentCard devCard: update.getCardList()){
            System.out.println("\nCard " + (index++) + ":");
            System.out.println(devCard);
        }
    }

    @Override
    public void updateSoloTokens(ActionTokenUpdate actionTokenUpdate) {
        System.out.println("\nAction tokens have been updated, the next one on the list is:");
        System.out.println(actionTokenUpdate.getNextToken());
    }

    @Override
    public void updateTempMarbles(TempMarblesUpdate tempMarblesUpdate) {
        //No need to call selectPlayerList() here
        if(tempMarblesUpdate.getActivePlayer().equals(this.getNickname())) {
            System.out.println(tempMarblesUpdate);
            Request request = toChangeMarble(tempMarblesUpdate.getResources().get(0), tempMarblesUpdate.getResources().get(1), tempMarblesUpdate.getNumWhiteMarbles());
            getPlayer().sendMessage(request);
        }
    }

    private Request toChangeMarble(ResourceType res1, ResourceType res2, int numMarbles){
        while(true) {
            try{
                System.out.println("Choice the num of "+res1);
                int amount1 = Integer.parseInt(stdin.nextLine());
                if (amount1 > numMarbles) throw new Exception();
                System.out.println("Choice the num of "+res2);
                int amount2 = Integer.parseInt(stdin.nextLine());
                if (amount2 > numMarbles) throw new Exception();
                if (amount1 + amount2 != numMarbles) throw new Exception();
                return new ChangeMarblesRequest(amount1, amount2);
            } catch (Exception e) {
                System.out.println(ANSIColor.RED + "The number of white marbles does not match" + ANSIColor.RESET);
            }
        }
    }
}