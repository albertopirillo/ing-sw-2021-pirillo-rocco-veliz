package it.polimi.ingsw.client;

import it.polimi.ingsw.model.LeaderCard;
import it.polimi.ingsw.network.ServerUpdate;
import it.polimi.ingsw.network.StorageUpdate;
import it.polimi.ingsw.network.*;

import java.util.List;

public interface PlayerInterface {

    public String getNickname() {
        return nickname;
    }

    public void updateStorages(StorageUpdate update);
    public void updateLeaderCards();
    public void updateMarket();
    public void updateMarketTray();

    public void setNickname(String nickname);
    public String getNickname();
    public String chooseNickname();
    public void getGameSize();
    public void setup();
    public void viewInitialsLeadersCards(List<LeaderCard> cards);
    public void getInitialResources(int numPlayer);

    public void simulateGame(); //testing
    public abstract void updateStorages(StorageUpdate update);
    public abstract void updateLeaderCards(LeaderUpdate update);
    public abstract void updateDevSlots(DevSlotsUpdate update);
    public abstract void displayError(ErrorUpdate update);
    public abstract void updateMarket();
    public abstract void updateMarketTray();
    //more...
}
