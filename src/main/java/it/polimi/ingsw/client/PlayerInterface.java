package it.polimi.ingsw.client;

import it.polimi.ingsw.model.LeaderCard;
import it.polimi.ingsw.network.updates.*;
import it.polimi.ingsw.utils.ANSIColor;

import java.util.List;

public abstract class PlayerInterface {

    private String nickname;
    private final Client player;
    private final boolean debug = false; //TODO: remove

    public PlayerInterface(Client player) {
        this.player = player;
    }

    public Client getPlayer() {
        return player;
    }
    public String getNickname() {
        return nickname;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void endOfUpdate(EndOfUpdate update) {
        if (update.getActivePlayer().equals(this.nickname)) {
            //Call Client method to make requests;
            simulateGame();
        }
    }

    public void debugPrint(String string) {
        if (debug) {
            System.out.print(ANSIColor.BRIGHT_YELLOW + "[DEBUG] ");
            System.out.println(string + ANSIColor.RESET);
        }
    }

    public void readUpdate(ServerUpdate updateMessage) {
        debugPrint("Received updated of type: " + updateMessage);
        if (updateMessage != null) {
            debugPrint("Processing update...");
            updateMessage.update(this);
        }
    }

    public abstract String chooseNickname();
    public abstract void getGameSize();
    public abstract void setup();
    public abstract void viewInitialResources(int numPlayer);
    public abstract void viewInitialsLeaderCards(List<LeaderCard> cards);
    public abstract void simulateGame(); //testing
    public abstract void errorPrint(String error);
    public abstract void loginMessage();

    public abstract void updateTempResource(TempResourceUpdate update);
    public abstract void updateStorages(StorageUpdate update);
    public abstract void updateLeaderCards(LeaderUpdate update);
    public abstract void updateDevSlots(DevSlotsUpdate update);
    public abstract void displayError(ErrorUpdate update);
    public abstract void updateFaithTrack(FaithTrackUpdate faithTrackUpdate);
    public abstract void updateMarket(MarketUpdate marketUpdate);
    public abstract void updateMarketTray(MarketTrayUpdate update);
    public abstract void updateDiscardedCards(DiscardedCardsUpdate update);
    public abstract void updateSoloTokens(ActionTokenUpdate actionTokenUpdate);
    public abstract void updateTempMarbles(TempMarblesUpdate tempMarblesUpdate);
    public abstract void updateGameOver(GameOverUpdate update);
}
