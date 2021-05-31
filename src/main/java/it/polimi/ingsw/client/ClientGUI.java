package it.polimi.ingsw.client;

import it.polimi.ingsw.client.gui.*;
import it.polimi.ingsw.client.model.ClientModel;
import it.polimi.ingsw.client.model.MarketModel;
import it.polimi.ingsw.model.*;
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
    private final ClientModel clientModel;
    private final boolean testing = false;
    private final int playerAmountTesting = 2;

    public ClientGUI(Client client, MainController controller) {
        this.client = client;
        this.clientModel = new ClientModel();
        this.mainController = controller;
    }

    @Override
    public ClientModel getClientModel() {
        return clientModel;
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
        this.clientModel.setNickname(nickname);
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
                leaderCardSelectionController.setInitialLeaderCards(cards);
                printLog("LeaderCardSelectionController ready");
                leaderCardSelectionController.setMainController(mainController);
            });
        }
    }

    @Override
    public void viewInitialResources(int numPlayer) {
        Map<ResourceType, Integer> res = new HashMap<>();
        if (testing) {
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
        else {
            if (numPlayer > 0) {
                Platform.runLater(() -> {
                    ResourceSelectionController resourceSelectionController = (ResourceSelectionController) JavaFXMain.changeScene("resource_selection");
                    resourceSelectionController.setNickname(nickname);
                    resourceSelectionController.setNumPlayer(numPlayer);
                    printLog("ResourceSelectionController ready");
                    resourceSelectionController.setMainController(mainController);
                });
            } else {
                InitialResRequest request = new InitialResRequest(res);
                request.setNumPlayer(numPlayer);
                request.setPlayer(nickname);
                getClient().sendMessage(request);
            }
        }
    }

    @Override
    public void gameMenu() {
        //Nothing to be done here
    }

    @Override
    public void updateTempResource(TempResourceUpdate update) {
        clientModel.getStoragesModel().saveTempRes(update);
        printLog("Updating TempResource...");
        Platform.runLater(() -> {
            PersonalBoardController personalBoardController = mainController.getPersonalBoardController(update.getActivePlayer());
            personalBoardController.updateTempResources(update.getResource());
        });
    }

    @Override
    public void updateStorages(StorageUpdate update) {
        clientModel.getStoragesModel().saveStorages(update);
        printLog("Updating storages...");
        Platform.runLater(() -> {
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
        clientModel.getPersonalBoardModel().saveLeaderCards(update);

        Platform.runLater(() -> {
            printLog("Updating leader cards...");
            Map<String, List<LeaderCard>> map = update.getLeaderMap();
            Map<String, PersonalBoardController> controllerMap = mainController.getPersonalBoardControllerMap();
            for(Map.Entry<String, List<LeaderCard>> entry: map.entrySet()){
                PersonalBoardController currentController = controllerMap.get(entry.getKey());
                currentController.updateLeaderCards(entry.getValue());
            }
        });
    }

    @Override
    public void updateDevSlots(DevSlotsUpdate update) {
        clientModel.getPersonalBoardModel().saveDevSlots(update);

    }

    @Override
    public void displayError(ErrorUpdate update) {
        if(update.getActivePlayer().equals(this.nickname)) {
            Platform.runLater(() ->
                    mainController.displayError(update.getClientError().getError()));
        }
    }

    @Override
    public void updateFaithTrack(FaithTrackUpdate update) {
        clientModel.getPersonalBoardModel().saveFaithTrack(update);
        Platform.runLater(() -> {
            printLog("Updating faith track...");
            Map<String, FaithTrack> map = update.getFaithTrackInfoMap();
            Map<String, PersonalBoardController> controllerMap = mainController.getPersonalBoardControllerMap();
            for(Map.Entry<String, FaithTrack> entry: map.entrySet()){
                PersonalBoardController currentController = controllerMap.get(entry.getKey());
                currentController.updateFaithTrack(entry.getValue());
            }
        });
    }

    @Override
    public void updateMarket(MarketUpdate update) {
        clientModel.getMarketModel().saveMarket(update);
        Platform.runLater(() -> {
            printLog("Updating market...");
            mainController.getMarketController().updateMarket(update.getCardImgs());
        });
    }

    @Override
    public void updateMarketTray(MarketTrayUpdate update) {
        MarketModel marketModel = clientModel.getMarketModel();
        marketModel.saveTray(update);
        Platform.runLater(() -> {
            printLog("Updating market tray...");
            MarblesColor[][] marbles = marketModel.getMarketTray();
            MarblesColor remainingMarble = marketModel.getRemainingMarble();
            mainController.getTrayController().updateMarketTray(marbles, remainingMarble);
        });
    }

    @Override
    public void updateDiscardedCards(DiscardedCardsUpdate update) {
        printLog("Updating discarded cards...");
        clientModel.getSoloGameModel().saveDiscardedCards(update);
        SoloController soloController = mainController.getSoloController();
        Platform.runLater(() -> soloController.updateDiscardedCards(update.getCardList()));
    }

    @Override
    public void updateSoloTokens(ActionTokenUpdate update) {
        printLog("Updating action tokens...");
        clientModel.getSoloGameModel().saveSoloTokens(update);
        SoloController soloController = mainController.getSoloController();
        Platform.runLater(() -> {
            mainController.switchSoloPopup();
            soloController.updateTokens(update.getNextToken());
        });

    }

    @Override
    public void updateTempMarbles(TempMarblesUpdate update) {
        clientModel.getMarketModel().saveTempMarbles(update);
        
    }

    @Override
    public void updateProductionDone(ProductionDoneUpdate update){

    }

    @Override
    public void updateActionDone(MainActionDoneUpdate update) {

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
        //Initialize the client model
        updateStorages(update.getStorageUpdate());
        updateMarketTray(update.getMarketTrayUpdate());
        updateMarket(update.getMarketUpdate());
        updateLeaderCards(update.getLeaderUpdate());
        updateFaithTrack(update.getFaithTrackUpdate());
        updateDevSlots(update.getDevSlotsUpdate());
    }
}
