package it.polimi.ingsw.network.updates;

import it.polimi.ingsw.client.UserInterface;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.network.DepotSetting;

import java.util.List;
import java.util.Map;

public class StorageUpdate extends ServerUpdate {
    private final Map<String, List<DepotSetting>> depotMap;
    private final Map<String, Resource> strongboxMap;

    public StorageUpdate(String activePlayer, Map<String, List<DepotSetting>> depotMap, Map<String, Resource> strongboxMap) {
        super(activePlayer);
        this.depotMap = depotMap;
        this.strongboxMap = strongboxMap;
    }

    public Map<String, List<DepotSetting>> getDepotMap() {
        return depotMap;
    }

    public Map<String, Resource> getStrongboxMap() {
        return strongboxMap;
    }

    @Override
    public void update(UserInterface userInterface) {
        userInterface.updateStorages(this);
    }
}
