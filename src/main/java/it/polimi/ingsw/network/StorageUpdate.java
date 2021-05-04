package it.polimi.ingsw.network;

import it.polimi.ingsw.client.PlayerInterface;
import it.polimi.ingsw.model.Resource;

import java.util.List;

public class StorageUpdate extends ServerUpdate {
    private final List<DepotSetting> depotSettings;
    private final Resource strongbox;

    public StorageUpdate(String activePlayer, List<DepotSetting> depotSettings, Resource strongbox) {
        super(activePlayer);
        this.depotSettings = depotSettings;
        this.strongbox = strongbox;
    }

    public List<DepotSetting> getDepotSettings() {
        return depotSettings;
    }

    public Resource getStrongbox() {
        return strongbox;
    }

    @Override
    public void update(PlayerInterface playerInterface) {
        playerInterface.updateStorages(this);
    }
}
