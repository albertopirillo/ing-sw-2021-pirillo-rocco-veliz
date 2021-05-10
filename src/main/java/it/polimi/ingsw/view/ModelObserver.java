package it.polimi.ingsw.view;

import it.polimi.ingsw.model.ClientError;
import it.polimi.ingsw.model.Game;

public interface ModelObserver {
    void gameStateChange(Game game);
    void notifyGameOver(String playerWinner);
    void showFaithTrack(Game game);
    void showLeaderCards(Game game);
    void notifyInitResources(Game game, int numPlayer);
    void notifyInitLeaderCards(Game game);
    void showClientError(Game game, ClientError clientError);
    void showMarketTray(Game game);
    void showMarket(Game game);
    void showStorages(Game game, String playerNick);
    void showDevSlots(Game game);
    void updateStorages(Game game);
    void showTempRes(Game game);
    void quitGame();
    String getPlayer();
}