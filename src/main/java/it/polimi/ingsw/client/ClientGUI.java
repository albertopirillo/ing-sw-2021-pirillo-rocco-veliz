package it.polimi.ingsw.client;

import it.polimi.ingsw.client.gui.*;
import it.polimi.ingsw.model.LeaderCard;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.ResourceType;
import it.polimi.ingsw.network.DepotSetting;
import it.polimi.ingsw.network.Processable;
import it.polimi.ingsw.network.messages.GameSizeMessage;
import it.polimi.ingsw.network.messages.LoginMessage;
import it.polimi.ingsw.network.requests.ChooseLeaderRequest;
import it.polimi.ingsw.network.requests.InitialResRequest;
import it.polimi.ingsw.network.updates.*;
import javafx.application.Platform;

import java.util.*;

public class ClientGUI implements UserInterface {

    private String nickname;
    private final Client client;
    private final MainController mainController;
    private final boolean testing = false;
    private final int playerAmountTesting = 2;

    public ClientGUI(Client client, MainController controller) {
        this.client = client;
        this.mainController = controller;
    }

    @Override
    public void setup() {
        System.out.println("[INFO] Game is starting...");
        this.mainController.setClientGUI(this);
    }

    @Override
    public Client getClient() {
       return  this.client;
    }

    @Override
    public String getNickname() {
        return this.nickname;
    }

    @Override
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public void endOfUpdate(EndOfUpdate update) {
        printLog("The current active player is " + update.getActivePlayer());
        Platform.runLater(() -> {
            mainController.switchToTab(update.getActivePlayer());
            //Activate the GUI only if this is the active player
            mainController.disableGUI(!update.getActivePlayer().equals(this.nickname));
        });
    }

    @Override
    public void readUpdate(ServerUpdate updateMessage) {
        if (updateMessage != null) {
            updateMessage.update(this);
        }
    }

    @Override
    public String chooseNickname() {
        if (testing) {
            Random random = new Random();
            return "Player " + random.nextInt(1000);
        }
        else {
            SetupController setupController = mainController.getSetupController();
            synchronized (SetupController.lock) {
                while (setupController.getNickname() == null) {
                    try {
                        System.out.println("[CLIENT] Waiting for nickname...");
                        SetupController.lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            String nickname = setupController.getNickname();
            System.out.println("[CLIENT] Nickname set: " + nickname);
            return nickname;
        }
    }

    @Override
    public void changeNickname(){
        System.out.println("[CLIENT] Nickname already in use");
        mainController.getSetupController().resetNickname();
        Platform.runLater(() -> mainController.getSetupController().enableNicknameInput(true));
        //Wait for the nickname to be reset by the controller
        synchronized (SetupController.lock) {
            while(mainController.getSetupController().getNickname() != null) {
                try {
                    SetupController.lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            loginMessage();
        }
    }

    @Override
    public void loginMessage(){
        String nickname = chooseNickname();
        Processable login = new LoginMessage(nickname, nickname);
        setNickname(nickname);
        getClient().sendMessage(login);
    }

    @Override
    public void getGameSize() {
        if (testing) {
            Processable rsp = new GameSizeMessage(getNickname(), this.playerAmountTesting);
            mainController.sendMessage(rsp);
        }
        else {
            Platform.runLater(() -> mainController.getSetupController().firstPlayerSetup());
        }
    }

    @Override
    public void waitForHostError(String text) {
        mainController.getSetupController().resetNickname();
        System.out.println("[CLIENT] A game is being created. Please wait for the host");
        Platform.runLater(() -> mainController.getSetupController().waitForHostError(text));
    }

    @Override
    public void viewInitialsLeaderCards(List<LeaderCard> cards) {
        if (testing) {
            ChooseLeaderRequest request = new ChooseLeaderRequest(0, 1);
            request.setPlayer(nickname);
            mainController.sendMessage(request);
        }
        else {
            Platform.runLater(() -> {
                //Set and initialize the LeaderCardSelectionController
                LeaderCardSelectionController leaderCardSelectionController = (LeaderCardSelectionController) JavaFXMain.changeScene("leader_card_selection");
                leaderCardSelectionController.setNickname(nickname);
                leaderCardSelectionController.setLeaderCards(cards);
                printLog("LeaderCardSelectionController ready");
                mainController.setLeaderCardSelectionController(leaderCardSelectionController);
                leaderCardSelectionController.setMainController(mainController);
            });
        }
    }

    @Override
    public void viewInitialResources(int numPlayer) { //TODO
        Map<ResourceType, Integer> res = new HashMap<>();
        switch (numPlayer){
            case 1:
            case 2:
                res.put(ResourceType.STONE, 1);
                break;
            case 3:
                res.put(ResourceType.STONE, 1);
                res.put(ResourceType.COIN, 1);
                break;
            default:
                break;
        }
        InitialResRequest request = new InitialResRequest(res);
        request.setNumPlayer(numPlayer);
        request.setPlayer(getNickname());
        getClient().sendMessage(request);
    }

    @Override
    public void gameMenu() {
        //Nothing to be done here
    }

    @Override
    public void updateTempResource(TempResourceUpdate update) {

    }

    @Override
    public void updateStorages(StorageUpdate update) {
        Platform.runLater(() -> {
            printLog("Updating storages...");
            Map<String, List<DepotSetting>> depotMap = update.getDepotMap();
            Map<String, Resource> strongboxMap = update.getStrongboxMap();
            Map<String, PersonalBoardController> controllerMap = mainController.getPersonalBoardControllerMap();
            for(String playerNick: strongboxMap.keySet()) {
                PersonalBoardController currentController = controllerMap.get(playerNick);
                currentController.setDepot(depotMap.get(playerNick));
                currentController.setStrongbox(strongboxMap.get(playerNick));
            }
        });
    }

    private void printLog(String toPrint) {
        System.out.println("[GUI] " + toPrint);
    }

    @Override
    public void updateLeaderCards(LeaderUpdate update) {

    }

    @Override
    public void updateDevSlots(DevSlotsUpdate update) {

    }

    @Override
    public void displayError(ErrorUpdate update) {
        if(update.getActivePlayer().equals(this.nickname)) {
            Platform.runLater(() ->
                    mainController.displayError(update.getClientError().getError()));
        }
    }

    @Override
    public void updateFaithTrack(FaithTrackUpdate faithTrackUpdate) {

    }

    @Override
    public void updateMarket(MarketUpdate marketUpdate) {

    }

    @Override
    public void updateMarketTray(MarketTrayUpdate update) {

    }

    @Override
    public void updateDiscardedCards(DiscardedCardsUpdate update) {

    }

    @Override
    public void updateSoloTokens(ActionTokenUpdate actionTokenUpdate) {

    }

    @Override
    public void updateTempMarbles(TempMarblesUpdate tempMarblesUpdate) {
        
    }

    @Override
    public void updateProductionDone(ProductionDoneUpdate update){

    }

    @Override
    public void updateActionDone(MainActionDoneUpdate mainActionDoneUpdate) {

    }

    @Override
    public void updateGameOver(GameOverUpdate update){

    }

    @Override
    public void startMainGame(EndOfInitialUpdate update) {
        printLog("Setting up main game...");
        printLog("The first player is " + update.getActivePlayer());
        //Start the main scene
        Platform.runLater(() -> {
            List<String> playerList = new ArrayList<>(update.getStorageUpdate().getStrongboxMap().keySet());
            JavaFXMain.initMainScene(playerList);
            mainController.switchToTab(update.getActivePlayer());
            mainController.disableGUI(!update.getActivePlayer().equals(this.nickname));
        });
        //Update the GUI with storages and leader cards of all players
        updateStorages(update.getStorageUpdate());
        //TODO: updateLeaderCards(update.getLeaderUpdate());
    }
}
