package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.InvalidKeyException;
import it.polimi.ingsw.exceptions.NegativeResAmountException;
import it.polimi.ingsw.utils.ModelObserver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>Generic game class, for both SinglePlayer and MultiPlayer</p>
 * <p>Contains common methods to both the game modes</p>
 * <p>Can be used to access the whole model</p>
 * <p>Notifies the ModelObserver</p>
 */
public abstract class Game {

    private List<Player> playersList;
    private final List<ModelObserver> observers;
    private int playerAmount;
    private Player activePlayer;
    private Market market;

    /**
     * Constructs a new object, initializing its observer's list
     */
    public Game() {
        this.observers = new ArrayList<>();
    }

    /**
     * Gets the list of the player associated to this game
     * @return a list of players
     */
    public List<Player> getPlayersList() {
        return this.playersList;
    }
    /**
     * Gets the amount of players in this game
     * @return an int representing the amount of players
     */
    public int getPlayerAmount() {
        return this.playerAmount;
    }
    /**
     * Gets the active player
     * @return a Player object representing the active player
     */
    public Player getActivePlayer() {return this.activePlayer;}
    /**
     * Gets the market associated to this game
     * @return the Market object
     */
    public Market getMarket() { return this.market;}
    /**
     * Gets the list of observers of this game
     * @return a list of observers
     */
    public List<ModelObserver> getObservers() {
        return this.observers;
    }

    /**
     * Sets the player list of this game
     * @param playersList a list with all the Player objects
     */
    public void setPlayersList(List<Player> playersList) {
        this.playersList = playersList;
    }
    /**
     * Sets the player amount of this game
     * @param playerAmount the amount of players
     */
    public void setPlayerAmount(int playerAmount) {
        this.playerAmount = playerAmount;
    }
    /**
     * Sets the current active player
     * @param activePlayer the active player
     */
    public void setActivePlayer(Player activePlayer) {
        this.activePlayer = activePlayer;
    }
    /**
     * Sets the Market associated with this game
     * @param market the Market
     */
    public void setMarket(Market market) {
        this.market = market;
    }
    /**
     * Adds an observer to the observers list
     * @param observer a ModelObserver
     */
    public void addObserver(ModelObserver observer) {
        this.observers.add(observer);
    }

    /**
     * Computes the final score of all players, at the end of the game
     * @return a map that contains players as keys and the final score as values
     */
    public Map<Player, Integer> computeFinalScore() throws NegativeResAmountException, InvalidKeyException {
        Map<Player, Integer> finalScores = new HashMap<>();
        for(Player p: this.getPlayersList()) {
            p.addVictoryPoints(finalPositionVP(p.getPlayerFaith()));
            //Player gets 1 victoryPoint for every 5 resources he has
            Resource allPlayerRes = p.getAllResources();
            int amount = allPlayerRes.getTotalAmount();
            finalScores.put(p, p.getVictoryPoints() + (amount / 5));
        }
        return finalScores;
    }

    /**
     * At the end of the game, the FaitTrack gives you VPs depending on the final position of your marker
     * @param finalPosition the final position of your market (equal to Player.faithPoints)
     * @return the additional victory points from the FaithTrack
     */
    public int finalPositionVP(int finalPosition) {
        if (finalPosition < 3) return 0;
        else if (finalPosition < 6) return 1;
        else if (finalPosition < 9) return 2;
        else if (finalPosition < 12) return 4;
        else if (finalPosition < 15) return 6;
        else if (finalPosition < 18) return 9;
        else if (finalPosition < 21) return 12;
        else if (finalPosition < 24) return 16;
        else return 20;
    }

    /**
     * Sets the first player of the game, assigning the inkwell to him
     * @return  a String representing the nickname of the first player
     */
    public abstract String giveInkwell();
    /**
     * When called, it ends the game
     * @param win used in SoloGame to specify if the player won or not
     */
    public abstract void lastTurn(boolean win) throws NegativeResAmountException, InvalidKeyException;
    /**
     * Sets the next active player, updating actionToken if in SoloGame
     */
    public abstract void nextTurn() throws NegativeResAmountException, InvalidKeyException;
    /**
     * Checks if a condition to end the game is met, if so notifies the observers
     */
    public abstract void checkEndGame() throws NegativeResAmountException, InvalidKeyException;
    /**
     * <p>Setup method to initialize the game</p>
     * <p>Deserializes development cards and leader cards from their json files</p>
     * <p>Initializes the market cards and the market tray</p>
     * <p>Chooses 4 leader cards for each player</p>
     */
    public abstract void startGame();

    /**
     * Notifies the observers that the game is over, sending final scores
     * @param ranking a list with the final scores
     * @param scores a map that contains players as keys and the final score as values
     */
    public void notifyEndOfGame(List<String> ranking, Map<String, Integer> scores){
        String winnerName = ranking.size() > 0 ? ranking.get(0) : null;
        for(ModelObserver observer: observers){
            observer.notifyGameOver(this, observer.getPlayer().equals(winnerName), ranking, scores);
        }
    }

    /**
     * <p>Quits the game for the player that asked to</p>
     * <p>Ends the game for all players if there are 1 or 2 players</p>
     * <p>Otherwise ends the game only for the player who sent the request</p>
     */
    public void quitGame(){
        if(observers.size() < 3){
            //end the game for all players if there are 1 or 2 players
            for(ModelObserver observer : observers){
                observer.quitGame();
            }
        } else {
            //quit the game only for the player who sent the QuitGameRequest
            for(int i = 0; i < observers.size(); i++){
                if(observers.get(i).getPlayer().equals(getActivePlayer().getNickname())){
                    setActivePlayer(getPlayersList().get((i + 1) % getPlayerAmount()));
                    observers.get(i).quitGame();
                    observers.remove(i);
                    playersList.remove(i);
                    break;
                }
            }
            notifyEndOfUpdates();
        }
    }

    /**
     * Notifies the observers that ServerUpdates are over and the Client can send Requests
     */
    public void notifyEndOfUpdates() {
        for(ModelObserver observer : observers)
            observer.gameStateChange(this);
    }
    /**
     * Notifies the observers that player has to choose its initial resources
     * @param numPlayer the order in which the players will play the game
     */
    public void updateInitResources(int numPlayer){
        for(ModelObserver observer : observers)
            observer.notifyInitResources(this, numPlayer);
    }
    /**
     * Notifies the observers that player has to choose its initial leader cards
     */
    public void updateInitLeaderCards(){
        for(ModelObserver observer : observers)
            observer.showInitLeaderCards(this);
    }
    /**
     * Notifies the observers that the FaithTrack was updated
     */
    public void updateFaithTrack(){
        for(ModelObserver observer : observers)
            observer.showFaithTrack(this);
    }
    /**
     * Notifies the observers that the leader cards of a player were updated
     */
    public void updateLeaderCards(){
        for(ModelObserver observer : observers)
            observer.showLeaderCards(this);
    }
    /**
     * Notifies the observers that the Server found an error in the client's Request
     */
    public void updateClientError(ClientError clientError){
        for(ModelObserver observer : observers)
            observer.showClientError(this, clientError);
    }
    /**
     * Notifies the observers that the Market Tray was updated
     */
    public void updateMarketTray(){
        for(ModelObserver observer : observers)
            observer.showMarketTray(this);
    }
    /**
     * Notifies the observers that the development slots of a player were updated
     */
    public void updateDevSlots() {
        for(ModelObserver observer : observers)
            observer.showDevSlots(this);
    }
    /**
     * Notifies the observers that the Depot or the Strongbox of a player was updated
     */
    public void updateStorages() {
        for(ModelObserver observer : observers)
            observer.showStorages(this);
    }
    /**
     * Notifies the observers that the Market Cards was updated
     */
    public void updateMarket(){
        for(ModelObserver observer : observers)
            observer.showMarket(this);
    }
    /**
     * Notifies the observers that there are resources from the Market that needs to be placed
     */
    public void updateTempRes() {
        for(ModelObserver observer : observers)
            observer.showTempRes(this);
    }
    /**
     * Notifies the observer that there are white marbles whose color needs to be changed
     * @param numWhiteMarbles the amount of white marbles to be changed
     */
    public void updateTempMarbles(int numWhiteMarbles){
        for(ModelObserver observer : observers)
            observer.showTempMarbles(this, numWhiteMarbles);
    }
    /**
     * Notifies the observers that the player already activated a production this turn
     */
    public void setProductionDone(){
        for(ModelObserver observer : observers)
            observer.setProductionDone(this);
    }
    /**
     *  Notifies the observers that the player already performed one of its main actions this turn
     */
    public void setMainActionDone(){
        for(ModelObserver observer : observers)
            observer.setMainActionDone(this);
    }
    /**
     * Notifies the observers that the ServerUpdates are over and the game can start normally
     */
    public void notifyEndOfInitialUpdates() {
        for(ModelObserver observer : observers)
            observer.gameStateStart(this);
    }

}
