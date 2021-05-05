package it.polimi.ingsw.network;

import it.polimi.ingsw.client.PlayerInterface;
import it.polimi.ingsw.model.DevelopmentSlot;
import it.polimi.ingsw.model.Player;

import java.util.List;

public class DevSlotsUpdate extends ServerUpdate {
    private final List<Player> playerList;
    private final List<List<DevelopmentSlot>> devSlotList;

    public DevSlotsUpdate(String activePlayer, boolean lastUpdate, List<Player> playerList, List<List<DevelopmentSlot>> devSlotList) {
        super(activePlayer, lastUpdate);
        this.playerList = playerList;
        this.devSlotList = devSlotList;
    }

    public List<Player> getPlayerList() {
        return playerList;
    }

    public List<List<DevelopmentSlot>> getDevSlotList() {
        return devSlotList;
    }

    @Override
    public void update(PlayerInterface playerInterface) {
        playerInterface.updateDevSlots(this);
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        for (int i = 0; i < playerList.size(); i++) {
            string.append(playerList.get(i).getNickname()).append("'s development slots:\n");
            for (int j = 0; j < devSlotList.get(i).size(); j++) {
                string.append("Slot").append(j + 1).append(":\n");
                string.append(devSlotList.get(i).get(j).getCards());
            }
        }
        return string.toString();
    }
}
