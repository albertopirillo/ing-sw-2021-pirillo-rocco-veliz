package it.polimi.ingsw.client;

import it.polimi.ingsw.client.gui.MainController;
import it.polimi.ingsw.model.LeaderCard;
import it.polimi.ingsw.network.updates.*;

import java.util.List;

public class ClientGUI extends PlayerInterface {

    private MainController guiController;
    //TODO: Platform.runLater(() -> {});

    public ClientGUI(Client player) {
        super(player);
    }

    @Override
    public String chooseNickname() {
        return null;
    }

    @Override
    public void getGameSize() {

    }

    @Override
    public void setup() {

    }

    @Override
    public void viewInitialsLeaderCards(List<LeaderCard> cards) {

    }

    @Override
    public void viewInitialResources(int numPlayer) {

    }

    @Override
    public void simulateGame() {

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
}
