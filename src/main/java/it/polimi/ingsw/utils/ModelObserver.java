package it.polimi.ingsw.utils;

import it.polimi.ingsw.model.*;

import java.util.List;
import java.util.Map;

public interface ModelObserver {
    void gameStateChange();
    void notifyGameOver(Game game, boolean win, List<String> ranking, Map<String, Integer> scores);
    void showFaithTrack(List<Player> players);
    void showLeaderCards(List<Player> players);
    void notifyInitResources(int numPlayer);
    void showInitLeaderCards(List<LeaderCard> leaderCards);
    void showClientError(ClientError clientError);
    void showMarketTray(MarketTray marketTray);
    void showMarket(List<DevelopmentCard> cards);
    void showStorages(List<Player> players);
    void showDevSlots(List<Player> players);
    void showTempRes();
    void showDiscardedCards(List<DevelopmentCard> cardList);
    void showLastActionToken(SoloActionToken lastToken);
    void quitGame();
    String getPlayer();
    void showTempMarbles(List<ResourceType> resTypesAbility, int numWhiteMarbles);
    void setProductionDone();
    void setSecondProductionDone();
    void setMainActionDone();
    void gameStateStart(Game game);
}