package it.polimi.ingsw.utils;

import it.polimi.ingsw.model.*;

import java.util.List;
import java.util.Map;

public interface ModelObserver {
    void gameStateChange(Game game);
    void notifyGameOver(Game game, boolean win, List<String> ranking, Map<String, Integer> scores);
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
    void showLastActionToken(SoloGame soloGame, SoloActionToken lastToken);
    void quitGame();
    String getPlayer();
    void showTempMarbles(Game game, int numWhiteMarbles);
    void setProductionDone(Game game);
    void setMainActionDone(Game game);
    void gameStateStart(Game game);
}