package it.polimi.ingsw.client;

import it.polimi.ingsw.model.LeaderCard;
import it.polimi.ingsw.network.updates.*;

import java.util.List;

public interface UserInterface {

    Client getClient();
    String getNickname();
    void setNickname(String nickname);
    void endOfUpdate(EndOfUpdate update);
    void readUpdate(ServerUpdate updateMessage);
    String chooseNickname();
    void getGameSize();
    void setup();
    void viewInitialResources(int numPlayer);
    void viewInitialsLeaderCards(List<LeaderCard> cards);
    void gameMenu();
    void errorPrint(String error);
    void loginMessage();
    void updateTempResource(TempResourceUpdate update);
    void updateStorages(StorageUpdate update);
    void updateLeaderCards(LeaderUpdate update);
    void updateDevSlots(DevSlotsUpdate update);
    void displayError(ErrorUpdate update);
    void updateFaithTrack(FaithTrackUpdate faithTrackUpdate);
    void updateMarket(MarketUpdate marketUpdate);
    void updateMarketTray(MarketTrayUpdate update);
    void updateDiscardedCards(DiscardedCardsUpdate update);
    void updateSoloTokens(ActionTokenUpdate actionTokenUpdate);
    void updateTempMarbles(TempMarblesUpdate tempMarblesUpdate);
    void updateGameOver(GameOverUpdate update);
    void changeNickname();

}
