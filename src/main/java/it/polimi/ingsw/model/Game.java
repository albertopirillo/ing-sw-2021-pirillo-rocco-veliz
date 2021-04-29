package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.InvalidKeyException;
import it.polimi.ingsw.exceptions.NegativeResAmountException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Game {

    private List<Player> playersList;
    private int playerAmount;
    private Player activePlayer;
    private Market market;

    //Default constructor
    public Game() {}

    //Inherited getters
    public List<Player> getPlayersList() {
        return this.playersList;
    }
    public int getPlayerAmount() {
        return this.playerAmount;
    }

    public Player getPlayer(String nickname){
        for(Player player : playersList){
            if(player.getNickname().equals(nickname)) return player;
        }
        return null;
    }

    public Player getActivePlayer() {return this.activePlayer;}

    public Market getMarket() { return this.market;}

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

    //Shared methods
    public Map<Player, Integer> computeFinalScore() throws NegativeResAmountException, InvalidKeyException {
        Map<Player, Integer> finalScores = new HashMap<>();
        for(Player p: this.getPlayersList()) {
            //Player gets 1 victoryPoint for every 5 resources he has
            Resource allPlayerRes = p.getAllResources();
            int amount = allPlayerRes.getTotalAmount();
            finalScores.put(p, p.getVictoryPoints() + (amount / 5));
        }
        return finalScores;
    }

    //Methods to be implemented
    public abstract void lastTurn(boolean win) throws NegativeResAmountException, InvalidKeyException;
    public abstract void nextTurn() throws NegativeResAmountException, InvalidKeyException;
    public abstract void checkEndGame() throws NegativeResAmountException, InvalidKeyException;
    public String giveInkwell(){return null;};
    public abstract void startGame();
}
