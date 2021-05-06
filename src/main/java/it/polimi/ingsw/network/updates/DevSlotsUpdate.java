package it.polimi.ingsw.network.updates;

import it.polimi.ingsw.client.PlayerInterface;
import it.polimi.ingsw.model.DevelopmentSlot;

import java.util.List;
import java.util.Map;

public class DevSlotsUpdate extends ServerUpdate {
    private final Map<String, List<DevelopmentSlot>> devSlotMap;

    public DevSlotsUpdate(String activePlayer, Map<String, List<DevelopmentSlot>> devSlotMap) {
        super(activePlayer);
        this.devSlotMap = devSlotMap;
    }

    public Map<String, List<DevelopmentSlot>> getDevSlotMap() {
        return devSlotMap;
    }

    @Override
    public void update(PlayerInterface playerInterface) {
        playerInterface.updateDevSlots(this);
    }
}
