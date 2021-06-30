package it.polimi.ingsw.utils;

import it.polimi.ingsw.model.*;

import java.util.List;
import java.util.Map;

public interface ModelObserver {

    String getPlayer();
    /**
     * Send to the client a EndOfUpdate(Message)
     */
    void gameStateChange();
    /**
     * Send to the client the finals scores and the nickname of the winner
     * @param win true if the player win, false otherwise
     * @param ranking list with all players' nicknames
     * @param scores map with all players' nicknames and the corresponding score
     */
    void notifyGameOver(boolean win, List<String> ranking, Map<String, Integer> scores);
    /**
     * Send to the client the FaithTrack
     * @param players List of Players
     */
    void showFaithTrack(List<Player> players);
    /**
     * Sent to the client the players list with the leader cards of each player
     * @param players List of Players
     */
    void showLeaderCards(List<Player> players);
    /**
     * Send to the client its initial resources that he has to choose
     * @param numPlayer the order in which the players will play the game
     */
    void notifyInitResources(int numPlayer);
    /**
     * Send to the client its initial leader cards that he has to choose
     * @param leaderCards List of initials leader cards
     */
    void showInitLeaderCards(List<LeaderCard> leaderCards);
    /**
     * Send to the client the error that the Server found in the client's Request
     * @param clientError Error Object that the server create and sends to the client
     */
    void showClientError(ClientError clientError);
    /**
     * Send to the client the Market Tray
     * @param marketTray MarketTray with all marbles
     */
    void showMarketTray(MarketTray marketTray);
    /**
     * Send to the client the Market's cards
     * @param cards List of the Development Cards available on the Market
     */
    void showMarket(List<DevelopmentCard> cards);
    /**
     * Sent to the client the players list with the depot and the strongbox of each player
     * @param players List of Players
     */
    void showStorages(List<Player> players);
    /**
     * Sent to the client the players list with the slots of each player
     * @param players List of Players
     */
    void showDevSlots(List<Player> players);
    /**
     * Sent to the client the resources from the Market that needs to be placed
     */
    void showTempRes();
    /**
     * Send to the client the two development cards that Lorenzo discarded
     * @param cardList a list with the two cards that were discarded
     */
    void showDiscardedCards(List<DevelopmentCard> cardList);
    /**
     * Send to the client the information that the action tokens have been updated
     * @param lastToken the next action token on the queue
     */
    void showLastActionToken(SoloActionToken lastToken);
    /**
     * <p>Quits the game for the player that asked to</p>
     * <p>Ends the game for all players if there are 1 or 2 players</p>
     * <p>Otherwise ends the game only for the player who sent the request</p>
     */
    void quitGame();
    /**
     * Send to the client the white marbles whose color needs to be changed
     * @param resTypesAbility a list of ResourceType. The player will change the white marbles with them
     * @param numWhiteMarbles the amount of white marbles to be changed
     */
    void showTempMarbles(List<ResourceType> resTypesAbility, int numWhiteMarbles);
    /**
     * Send to the client the information that the player already activated a production this turn
     */
    void setProductionDone();
    /**
     * Send to the client the information that the player already activated a leader o development production this turn
     */
    void setSecondProductionDone();
    /**
     *  Send to the client the information that the player already performed one of its main actions this turn
     */
    void setMainActionDone();
    /**
     *  Send to the client the information that the player chose a market tray o market cards action as one its main move this turn
     */
    void setMarketActionDone();
    /**
     * Send to the client a EndOfInitialUpdate. That means the game can start normally
     * @param game the full Game object
     */
    void gameStateStart(Game game);
}