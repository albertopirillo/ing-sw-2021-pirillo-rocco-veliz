package it.polimi.ingsw.client;

import it.polimi.ingsw.model.LeaderCard;
import it.polimi.ingsw.network.updates.*;

import java.util.List;

public class ClientGUI extends PlayerInterface {

    @Override
    public void readUpdate(ServerUpdate update) {

    }

    @Override
    public void setNickname(String nickname) {

    }

    @Override
    public String getNickname() {
        return null;
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
    public void updatePlayer(PlayerUpdate update) {

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
}
