package it.polimi.ingsw.view;

import it.polimi.ingsw.model.*;

import java.util.List;

public interface ModelObserver {
    void gameStateChange(Game game);
    void notifyGameOver(String playerWinner);
    void showFaithTrack(Game game);
    void showLeaderCards(Game game);
    void notifyInitResources(Game game, int numPlayer);
    void showInitLeaderCards(Game game);
    void showClientError(Game game, ClientError clientError);
    void showMarketTray(Game game);
    void showMarket(Game game);
    void showStorages(Game game);
    void showDevSlots(Game game);
    void showTempRes(Game game);
    void showDiscardedCards(SoloGame soloGame, List<DevelopmentCard> cardList);
    void showNextActionToken(SoloGame soloGame, SoloActionToken nextToken);
    void quitGame();
    String getPlayer();
    void showTempMarbles(Game game, int numWhiteMarbles);
}