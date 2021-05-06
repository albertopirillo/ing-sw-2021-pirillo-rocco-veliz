package it.polimi.ingsw.utils;

import it.polimi.ingsw.model.Game;

public interface ModelObserver {
    void gameStateChange(Game game);
    void notifyGameOver(String playerWinner);
    void showFaithTrack();
    void notifyInitResources(Game game, int numPlayer);
    void notifyInitLeaderCards(Game game);
}