package it.polimi.ingsw.client.model;

import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.network.DepotSetting;
import it.polimi.ingsw.network.updates.StorageUpdate;
import it.polimi.ingsw.network.updates.TempResourceUpdate;

import java.util.List;
import java.util.Map;

public class StorageModel {
    private final ClientModel clientModel;
    private Map<String, List<DepotSetting>> depotMap;
    private Map<String, Resource> strongboxMap;
    private Resource tempResource;

    //Constructor
    public StorageModel(ClientModel clientModel) {
        this.clientModel = clientModel;
    }

    //Getters
    public Map<String, List<DepotSetting>> getDepotMap() {
        return depotMap;
    }
    public Map<String, Resource> getStrongboxMap() {
        return strongboxMap;
    }
    public Resource getTempResource() {
        return tempResource;
    }

    //Save updates
    public void saveStorages(StorageUpdate update) {
        this.depotMap = update.getDepotMap();
        this.strongboxMap = update.getStrongboxMap();
    }
    public void saveTempRes(TempResourceUpdate update) {
        this.tempResource = update.getResource();
    }

    //Build new updates
    public StorageUpdate buildStorageUpdate() {
        String nickname = clientModel.getNickname();
        return new StorageUpdate(nickname, depotMap, strongboxMap);
    }
    public TempResourceUpdate buildTempResourceUpdate() {
        String nickname = clientModel.getNickname();
        return new TempResourceUpdate(nickname, tempResource);
    }
}
