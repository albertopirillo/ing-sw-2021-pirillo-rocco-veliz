package it.polimi.ingsw.client;

import it.polimi.ingsw.model.LeaderCard;
import it.polimi.ingsw.network.*;

import java.util.List;

public abstract class PlayerInterface {

    public abstract void readUpdate(ServerUpdate update);
    public abstract void setNickname(String nickname);
    public abstract String getNickname();
    public abstract String chooseNickname();
    public abstract void getGameSize();
    public abstract void setup();
    public abstract void viewInitialsLeadersCards(List<LeaderCard> cards);
    public abstract void getInitialResources(int numPlayer);

    public abstract void simulateGame(); //testing
    public abstract void updateStorages(StorageUpdate update);
    public abstract void updateLeaderCards(LeaderUpdate update);
    public abstract void updateDevSlots(DevSlotsUpdate update);
    public abstract void displayError(ErrorUpdate update);
    public abstract void updateMarket();
    public abstract void updateMarketTray();
    //more...
}
