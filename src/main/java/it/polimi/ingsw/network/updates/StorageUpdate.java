package it.polimi.ingsw.network.updates;

import it.polimi.ingsw.client.PlayerInterface;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.network.DepotSetting;

import java.util.List;
import java.util.Map;


public class StorageUpdate extends ServerUpdate {
    private final Map<String, List<DepotSetting>> depotMap;
    private final Map<String, List<Resource>> strongboxMap;

    public StorageUpdate(String activePlayer, Map<String, List<DepotSetting>> depotMap, Map<String, List<Resource>> strongboxMap) {
        super(activePlayer);
        this.depotMap = depotMap;
        this.strongboxMap = strongboxMap;
    }

    public Map<String, List<DepotSetting>> getDepotMap() {
        return depotMap;
    }

    public Map<String, List<Resource>> getStrongboxMap() {
        return strongboxMap;
    }

    @Override
    public void update(PlayerInterface playerInterface) {
        playerInterface.updateStorages(this);
    }

/*    @Override TODO
    public String toString() {
        StringBuilder string = new StringBuilder();
        for (int i = 0; i < playerList.size(); i++) {
            string.append(playerList.get(i).getNickname()).append("'s storages:\n");
            string.append("Strongbox: ").append(strongboxList.get(i)).append("\n");
            string.append("Depot:\n ").append(settingsList.get(i)).append("\n");
        }
        return string.toString();
    }*/
}
