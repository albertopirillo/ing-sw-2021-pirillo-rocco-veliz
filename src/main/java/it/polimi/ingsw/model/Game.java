package it.polimi.ingsw.model;

import java.util.List;

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

    //Methods to be implemented
    public abstract void nextTurn();
    public abstract void computeFinalScore();
    public abstract void endGame();
    public String giveInkwell(){return null;};
    public abstract void startGame();
}
