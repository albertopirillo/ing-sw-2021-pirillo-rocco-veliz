package it.polimi.ingsw.client;

import it.polimi.ingsw.model.LeaderCard;
import it.polimi.ingsw.network.ServerUpdate;
import it.polimi.ingsw.network.StorageUpdate;

import java.util.List;

public interface PlayerInterface {

    public void readUpdate(ServerUpdate updateMessage);

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
    //more...
}
