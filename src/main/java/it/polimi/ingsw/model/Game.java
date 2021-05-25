package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.InvalidKeyException;
import it.polimi.ingsw.exceptions.NegativeResAmountException;
import it.polimi.ingsw.utils.ModelObserver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Game {

    private List<Player> playersList;
    private final List<ModelObserver> observers;
    private int playerAmount;
    private Player activePlayer;
    private Market market;

    //Default constructor
    public Game() {
        this.observers = new ArrayList<>();
    }

    //Inherited getters
    public List<Player> getPlayersList() {
        return this.playersList;
    }
    public int getPlayerAmount() {
        return this.playerAmount;
    }
    public Player getActivePlayer() {return this.activePlayer;}
    public Market getMarket() { return this.market;}
    public List<ModelObserver> getObservers() {
        return this.observers;
    }


    //Inherited setters
    public void setPlayersList(List<Player> playersList) {
        this.playersList = playersList;
    }
    public void setPlayerAmount(int playerAmount) {
        this.playerAmount = playerAmount;
    }
    public void setActivePlayer(Player activePlayer) {
        this.activePlayer = activePlayer;
    }
    public void setMarket(Market market) {
        this.market = market;
    }
    public void addObserver(ModelObserver observer) {
        this.observers.add(observer);
    }


    //Shared methods
    public Player getPlayerFromNick(String nickname){
        for(Player player : playersList){
            if(player.getNickname().equals(nickname)) return player;
        }
        return null;
    }

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

    //Faith Track gives you VPs depending on the final position of your Faith Marker
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

    //Methods to be implemented
    public abstract String giveInkwell();
    public abstract void lastTurn(boolean win) throws NegativeResAmountException, InvalidKeyException;
    public abstract void nextTurn() throws NegativeResAmountException, InvalidKeyException;
    public abstract void checkEndGame() throws NegativeResAmountException, InvalidKeyException;
    public abstract void startGame();

    //Observers methods
    public void notifyEndOfGame(List<String> ranking, Map<String, Integer> scores){
        String winnerName = ranking.size() > 0 ? ranking.get(0) : null;
        for(ModelObserver observer: observers){
            observer.notifyGameOver(this, observer.getPlayer().equals(winnerName), ranking, scores);
        }
    }

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

    public void notifyEndOfUpdates() {
        //System.out.println("[MODEL] Notifying listeners of simulate game");
        for(ModelObserver observer : observers)
            observer.gameStateChange(this);
    }

    public void updateInitResources(int numPlayer){
        //System.out.println("[MODEL] Notifying listeners of players init resources update");
        for(ModelObserver observer : observers)
            observer.notifyInitResources(this, numPlayer);
    }

    public void updateInitLeaderCards(){
        //System.out.println("[MODEL] Notifying listeners of players init leaders cards update");
        for(ModelObserver observer : observers)
            observer.showInitLeaderCards(this);
    }

    public void updateFaithTrack(){
        //System.out.println("[MODEL] Notifying listeners of faith track info request");
        for(ModelObserver observer : observers)
            observer.showFaithTrack(this);
    }

    public void updateLeaderCards(){
        //System.out.println("[MODEL] Notifying listeners of leader cards info request");
        for(ModelObserver observer : observers)
            observer.showLeaderCards(this);
    }

    public void updateClientError(ClientError clientError){
        //System.out.println("[MODEL] Notifying listeners of client error");
        for(ModelObserver observer : observers)
            observer.showClientError(this, clientError);
    }

    public void updateMarketTray(){
        //System.out.println("[MODEL] Notifying listeners of market tray update");
        for(ModelObserver observer : observers)
            observer.showMarketTray(this);
    }

    public void updateDevSlots() {
        //System.out.println("[MODEL] Notifying listeners of development slots update");
        for(ModelObserver observer : observers)
            observer.showDevSlots(this);
    }

    public void updateStorages() {
        //System.out.println("[MODEL] Notifying listeners of storage update");
        for(ModelObserver observer : observers)
            observer.showStorages(this);
    }

    public void updateMarket(){
        //System.out.println("[MODEL] Notifying listeners of market update");
        for(ModelObserver observer : observers)
            observer.showMarket(this);
    }

    public void updateTempRes() {
        //System.out.println("[MODEL] Notifying listeners of temporary resources update");
        for(ModelObserver observer : observers)
            observer.showTempRes(this);
    }

    public void updateTempMarbles(int numWhiteMarbles){
        //System.out.println("[MODEL] Notifying listeners of temporary resources update");
        for(ModelObserver observer : observers)
            observer.showTempMarbles(this, numWhiteMarbles);
    }

    public void setProductionDone(){
        //System.out.println("[MODEL] Setting production action to true");
        for(ModelObserver observer : observers)
            observer.setProductionDone(this);
    }

    public void setMainActionDone(){
        //System.out.println("[MODEL] Setting turn action to true");
        for(ModelObserver observer : observers)
            observer.setMainActionDone(this);
    }

    public void notifyEndOfInitialUpdates() {
        //System.out.println("[MODEL] Notifying listeners of the start of the game");
        for(ModelObserver observer : observers)
            observer.gameStateStart(this);
    }

}
