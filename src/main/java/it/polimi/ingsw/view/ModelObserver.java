package it.polimi.ingsw.view;

import it.polimi.ingsw.model.ClientError;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.TempResource;

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
    void showDevSlots(Game game);
    void showStorage(Game game);
    void showTempRes(Game game, TempResource tempResource);
}