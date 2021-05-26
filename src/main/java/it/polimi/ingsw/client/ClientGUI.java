package it.polimi.ingsw.client;

import it.polimi.ingsw.client.gui.JavaFXMain;
import it.polimi.ingsw.client.gui.MainController;
import it.polimi.ingsw.client.gui.PersonalBoardController;
import it.polimi.ingsw.client.gui.SetupController;
import it.polimi.ingsw.model.LeaderCard;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.ResourceType;
import it.polimi.ingsw.network.DepotSetting;
import it.polimi.ingsw.network.requests.ChooseLeaderRequest;
import it.polimi.ingsw.network.requests.InitialResRequest;
import it.polimi.ingsw.network.updates.*;
import javafx.application.Platform;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClientGUI implements UserInterface {

    private String nickname;
    private final Client client;
    private final MainController mainController;

    public ClientGUI(Client client, MainController controller) {
        this.client = client;
        this.mainController = controller;
    }

    @Override
    public void setup() {
        System.out.println("[INFO] Game is starting...");
        this.mainController.setClientGUI(this);
        System.out.println("[INFO] MainController set: " + mainController);
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

    }

    @Override
    public void readUpdate(ServerUpdate updateMessage) {
        if (updateMessage != null) {
            updateMessage.update(this);
        }
    }

    @Override
    public String chooseNickname() {
        SetupController setupController = mainController.getSetupController();
        synchronized (SetupController.lock) {
            while(setupController.getNickname() == null) {
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

    @Override
    public void getGameSize() {
        Platform.runLater(() -> mainController.getSetupController().firstPlayerSetup());
    }

    @Override
    public void viewInitialsLeaderCards(List<LeaderCard> cards) { //TODO
        ChooseLeaderRequest request = new ChooseLeaderRequest(0, 1);
        request.setPlayer(getNickname());
        getClient().sendMessage(request);
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
    public void errorPrint(String error) {

    }

    @Override
    public void loginMessage() {

    }

    @Override
    public void updateTempResource(TempResourceUpdate update) {

    }

    @Override
    public void updateStorages(StorageUpdate update) {
        Platform.runLater(() -> {
            System.out.println("Updating storages...");
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

    @Override
    public void updateLeaderCards(LeaderUpdate update) {

    }

    @Override
    public void updateDevSlots(DevSlotsUpdate update) {

    }

    @Override
    public void displayError(ErrorUpdate update) {
        Platform.runLater(() ->
                mainController.displayError(update.getClientError().getError()));
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
    public void changeNickname() {

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
        //Start the main scene
        Platform.runLater(() -> {
            List<String> playerList = new ArrayList<>(update.getStorageUpdate().getStrongboxMap().keySet());
            JavaFXMain.initMainScene(playerList);
        });
        //Update the GUI with storages and leader cards of all players
        updateStorages(update.getStorageUpdate());
        //TODO: updateLeaderCards(update.getLeaderUpdate());
    }
}
