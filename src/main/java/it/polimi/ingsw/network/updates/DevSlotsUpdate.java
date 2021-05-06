package it.polimi.ingsw.network.updates;

import it.polimi.ingsw.client.PlayerInterface;
import it.polimi.ingsw.model.DevelopmentSlot;

import java.util.List;
import java.util.Map;

public class DevSlotsUpdate extends ServerUpdate {
    private final Map<String, List<DevelopmentSlot>> devSlotList;

    public DevSlotsUpdate(String activePlayer, Map<String, List<DevelopmentSlot>> devSlotList) {
        super(activePlayer);
        this.devSlotList = devSlotList;
    }

    public Map<String, List<DevelopmentSlot>> getDevSlotList() {
        return devSlotList;
    }

    @Override
    public void update(PlayerInterface playerInterface) {
        playerInterface.updateDevSlots(this);
    }

//    @Override TODO
//    public String toString() {
//        StringBuilder string = new StringBuilder();
//        for (int i = 0; i < playerList.size(); i++) {
//            string.append(playerList.get(i).getNickname()).append("'s development slots:\n");
//            for (int j = 0; j < devSlotList.get(i).size(); j++) {
//                string.append("Slot").append(j + 1).append(":\n");
//                string.append(devSlotList.get(i).get(j).getCards());
//            }
//        }
//        return string.toString();
//    }
}
