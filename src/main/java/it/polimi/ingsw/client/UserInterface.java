package it.polimi.ingsw.client;

import it.polimi.ingsw.client.model.ClientModel;
import it.polimi.ingsw.model.LeaderCard;
import it.polimi.ingsw.network.updates.*;

import java.util.List;

/**
 * <p>List of all the functionalities a Client implementation has to offer</p>
 * <p>Should make the client able to send every request and receive every update</p>
 * <p>Implemented by both the CLI and the GUI</p>
 */
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
    void waitForHostError(String text);
    void loginMessage();
    void updateTempResource(TempResourceUpdate update);
    void updateStorages(StorageUpdate update);
    void updateLeaderCards(LeaderUpdate update);
    void updateDevSlots(DevSlotsUpdate update);
    void displayError(ErrorUpdate update);
    void updateFaithTrack(FaithTrackUpdate update);
    void updateMarket(MarketUpdate update);
    void updateMarketTray(MarketTrayUpdate update);
    void updateDiscardedCards(DiscardedCardsUpdate update);
    void updateSoloTokens(ActionTokenUpdate update);
    void updateTempMarbles(TempMarblesUpdate update);
    void updateGameOver(GameOverUpdate update);
    void startMainGame(EndOfInitialUpdate update);
    void changeNickname();
    void updateProductionDone(ProductionDoneUpdate update);
    void updateSecondProductionDone(SecondProductionDoneUpdate update);
    void updateActionDone(MainActionDoneUpdate update);
    ClientModel getClientModel();

}
