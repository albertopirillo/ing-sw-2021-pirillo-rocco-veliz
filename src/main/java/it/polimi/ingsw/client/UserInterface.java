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

    /**
     * Gets the associated Client
     * @return the client
     */
    Client getClient();
    /**
     * Gets the nickname of the associated Player
     * @return the nickname
     */
    String getNickname();
    /**
     * Sets the nickname of the associated Player
     * @param nickname the nickname to set
     */
    void setNickname(String nickname);
    /**
     * <p>Performed when a EndOfUpdate is received from the Server</p>
     * <p>Gives the active player the possibility of performing actions</p>
     * @param update the object received from the server
     */
    void endOfUpdate(EndOfUpdate update);
    /**
     * Reads and activates a generic Update received from the Server
     * @param updateMessage the update received from the server
     */
    void readUpdate(ServerUpdate updateMessage);
    /**
     * Asks and waits for the player to input a nickname
     * @return the chosen nickname
     */
    String chooseNickname();
    /**
     * Asks and waits for the first player to input the player amount
     */
    void getGameSize();
    /**
     * Used at the beginning to setup the CLI or the GUI
     */
    void setup();
    /**
     * <p>Shows the initial resources to the player</p>
     * <p>Waits for the player to select one or more</p>
     * @param numPlayer the index of the player in the player's list
     */
    void viewInitialResources(int numPlayer);
    /**
     * <p>Shows the player four leader cards</p>
     * <p>Waits for the player to select two of them</p>
     * @param cards a list of the cards that the player chose
     */
    void viewInitialsLeaderCards(List<LeaderCard> cards);
    /**
     * <p>Prints a list of all the action a player can perform</p>
     * <p>Waits for the player to select one</p>
     * <p>Used only in the CLI</p>
     */
    void gameMenu();
    /**
     * Displays an error message when a player inputs the nickname when a game is still being created
     * @param text the error message to display
     */
    void waitForHostError(String text);
    /**
     * Asks the player wait before entering the username, because a game is still being created
     */
    void loginMessage();
    /**
     * <p>Updates and shows the resources taken from the market in the model of the Client</p>
     * <p>Uses the data received from the server</p>
     * @param update the update containing the data received from the server
     */
    void updateTempResource(TempResourceUpdate update);
    /**
     * <p>Updates and shows all depots and strongboxes in the model of the Client</p>
     * <p>Uses the data received from the server</p>
     * @param update the update containing the data received from the server
     */
    void updateStorages(StorageUpdate update);
    /**
     * <p>Updates and shows all the leader cards in the model of the Client</p>
     * <p>Uses the data received from the server</p>
     * @param update the update containing the data received from the server
     */
    void updateLeaderCards(LeaderUpdate update);
    /**
     * <p>Updates and shows all the development slots in the model of the Client</p>
     * <p>Uses the data received from the server</p>
     * @param update the update containing the data received from the server
     */
    void updateDevSlots(DevSlotsUpdate update);
    /**
     * <p>Display an error message to the player using an exception received from the server</p>
     * @param update the data received from the server
     */
    void displayError(ErrorUpdate update);
    /**
     * <p>Updates and shows the all faith tracks in the model of the Client</p>
     * <p>Uses the data received from the server</p>
     * @param update the update containing the data received from the server
     */
    void updateFaithTrack(FaithTrackUpdate update);
    /**
     * <p>Updates and shows the cards available at the market in the model of the Client</p>
     * <p>Uses the data received from the server</p>
     * @param update the update containing the data received from the server
     */
    void updateMarket(MarketUpdate update);
    /**
     * <p>Updates and shows the marbles tray of the market in the model of the Client</p>
     * <p>Uses the data received from the server</p>
     * @param update the update containing the data received from the server
     */
    void updateMarketTray(MarketTrayUpdate update);
    /**
     * <p>Updates and shows the cards that Lorenzo discarded in the last turn</p>
     * <p>Uses the data received from the server</p>
     * <p>Used only in single player mode</p>
     * @param update the update containing the data received from the server
     */
    void updateDiscardedCards(DiscardedCardsUpdate update);
    /**
     * <p>Updates and shows the action token that was activated in the last turn</p>
     * <p>Uses the data received from the server</p>
     * <p>Used only in single player mode</p>
     * @param update the update containing the data received from the server
     */
    void updateSoloTokens(ActionTokenUpdate update);
    /**
     * <p>Updates and shows the marbles whose color has to be changed in the model of the Client</p>
     * <p>Uses the data received from the server</p>
     * @param update the update containing the data received from the server
     */
    void updateTempMarbles(TempMarblesUpdate update);
    /**
     * <p>Ends the game and shows the players the final scores, telling them who won</p>
     * <p>Uses the data received from the server</p>
     * @param update the update containing the data received from the server
     */
    void updateGameOver(GameOverUpdate update);
    /**
     * <p>Initializes and shows all elements of the model of the Client</p>
     * <p>Uses the data received from the server</p>
     * @param update the update containing the data received from the server
     */
    void startMainGame(EndOfInitialUpdate update);
    /**
     * Asks the player to enter another nickname because the one he chose was already taken
     */
    void changeNickname();
    /**
     * <p>Sets the productionDone flag in the Client, so that it cannot perform another production</p>
     * <p>Uses the data received from the server</p>
     * @param update the update containing the data received from the server
     */
    void updateProductionDone(ProductionDoneUpdate update);
    /**
     * <p>Sets the secondProductionDone flag in the Client, so that extra and dev production can be performed</p>
     * <p>Uses the data received from the server</p>
     * @param update the update containing the data received from the server
     */
    void updateSecondProductionDone(SecondProductionDoneUpdate update);
    /**
     * <p>Sets the actionDone flag in the Client, so that it cannot perform another main action</p>
     * <p>Uses the data received from the server</p>
     * @param update the update containing the data received from the server
     */
    void updateActionDone(MainActionDoneUpdate update);
    /**
     * Gets a reference to the main class of the Model in the Client
     * @return the reference to the correspondingClientModel object
     */
    ClientModel getClientModel();
}
