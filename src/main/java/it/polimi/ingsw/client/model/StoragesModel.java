package it.polimi.ingsw.client.model;

import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.network.DepotSetting;

import java.util.List;
import java.util.Map;

public class StoragesModel {
    private Map<String, List<DepotSetting>> depotMap;
    private Map<String, Resource> strongboxMap;
    private Resource tempResource;

    public Map<String, List<DepotSetting>> getDepotMap() {
        return depotMap;
    }

    public Map<String, Resource> getStrongboxMap() {
        return strongboxMap;
    }

    public Resource getTempResource() {
        return tempResource;
    }
}
