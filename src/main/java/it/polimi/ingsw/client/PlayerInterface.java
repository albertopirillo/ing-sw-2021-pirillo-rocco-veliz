package it.polimi.ingsw.client;

import it.polimi.ingsw.model.LeaderCard;
import it.polimi.ingsw.network.updates.*;

import java.util.List;

public abstract class PlayerInterface {

    public void endOfUpdate() {
        //Call Client method to make requests;
        simulateGame();
    }

    public abstract void readUpdate(ServerUpdate update);
    public abstract void setNickname(String nickname);
    public abstract String getNickname();
    public abstract String chooseNickname();
    public abstract void getGameSize();
    public abstract void setup();
    public abstract void viewInitialResources(int numPlayer);
    public abstract void viewInitialsLeaderCards(List<LeaderCard> cards);
    public abstract void simulateGame(); //testing

    public abstract void updateTempResource(TempResourceUpdate update);
    public abstract void updateStorages(StorageUpdate update);
    public abstract void updateLeaderCards(LeaderUpdate update);
    public abstract void updateDevSlots(DevSlotsUpdate update);
    public abstract void displayError(ErrorUpdate update);
    public abstract void updatePlayer(PlayerUpdate update);
    public abstract void updateFaithTrack(FaithTrackUpdate faithTrackUpdate);
    public abstract void updateMarket(MarketUpdate marketUpdate);
    public abstract void updateMarketTray(MarketTrayUpdate update);
}
