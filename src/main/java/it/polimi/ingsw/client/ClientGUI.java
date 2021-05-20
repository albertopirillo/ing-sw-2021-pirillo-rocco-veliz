package it.polimi.ingsw.client;

import it.polimi.ingsw.client.gui.Launcher;
import it.polimi.ingsw.client.gui.MainController;
import it.polimi.ingsw.client.gui.PersonalBoardController;
import it.polimi.ingsw.model.LeaderCard;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.network.DepotSetting;
import it.polimi.ingsw.network.updates.*;

import java.util.List;
import java.util.Map;

public class ClientGUI implements UserInterface {

    private String nickname;
    private final Client client;
    private MainController guiController;

    //TODO: Platform.runLater(() -> {});

    public ClientGUI(Client client) {
        this.client = client;
    }

    public void setGuiController(MainController guiController) {
        this.guiController = guiController;
    }

    public MainController getGuiController() {
        return this.guiController;
    }

    @Override
    public void setup() {
        System.out.println("Game is starting...\n");
        Launcher.main(null);
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
        return null;
    }

    @Override
    public void getGameSize() {

    }

    @Override
    public void viewInitialsLeaderCards(List<LeaderCard> cards) {

    }

    @Override
    public void viewInitialResources(int numPlayer) {

    }

    @Override
    public void gameMenu() {

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
        System.out.println("Updating storages...");
        Map<String, List<DepotSetting>> depotMap = update.getDepotMap();
        Map<String, Resource> strongboxMap = update.getStrongboxMap();
        Map<String, PersonalBoardController> controllerMap = guiController.getPersonalBoardControllerMap();
        for(String playerNick: strongboxMap.keySet()) {
            PersonalBoardController currentController = controllerMap.get(playerNick);
            currentController.setDepot(depotMap.get(playerNick));
            currentController.setStrongbox(strongboxMap.get(playerNick));
        }
    }

    @Override
    public void updateLeaderCards(LeaderUpdate update) {

    }

    @Override
    public void updateDevSlots(DevSlotsUpdate update) {

    }

    @Override
    public void displayError(ErrorUpdate update) {

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
    public void updateActionDone(ProductionDoneUpdate update){

    }

    @Override
    public void updateGameOver(GameOverUpdate update){

    }
}
