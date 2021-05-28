package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.model.ResourceType;
import it.polimi.ingsw.network.DepotSetting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Depot {
    private final Map<Integer, List<ResourceType>> layers = new HashMap<>();

    public Depot() {
        List<ResourceType> firstLayer = new ArrayList<>();
        firstLayer.add(null);
        this.layers.put(1, firstLayer);
        List<ResourceType> secondLayer = new ArrayList<>();
        secondLayer.add(null);
        secondLayer.add(null);
        this.layers.put(2, secondLayer);
        List<ResourceType> thirdLayer = new ArrayList<>();
        thirdLayer.add(null);
        thirdLayer.add(null);
        thirdLayer.add(null);
        this.layers.put(3, thirdLayer);
        List<ResourceType> firstExtraLayer = new ArrayList<>();
        firstExtraLayer.add(null);
        firstExtraLayer.add(null);
        this.layers.put(4, firstExtraLayer);
        List<ResourceType> secondExtraLayer = new ArrayList<>();
        secondExtraLayer.add(null);
        secondExtraLayer.add(null);
        this.layers.put(5, secondExtraLayer);
    }

    public ResourceType getSlot(int layer, int slot) {
        return this.layers.get(layer).get(slot - 1);
    }

    public ResourceType getSlot(String id) {
        //System.out.println(id);
        int layer = Character.getNumericValue(id.charAt(id.length() - 3));
        int slot = Character.getNumericValue(id.charAt(id.length() - 1));
        //System.out.println("LAYER: " + layer + " SLOT: " + slot);
        return getSlot(layer, slot);
    }

    public void setSlot(int layer, int slot, ResourceType resType) {
        this.layers.get(layer).set(slot - 1, resType);
        System.out.println("LAYER: " + layer + " SLOT: " + slot + " RES: " + resType);
    }

    public void setSlot(String id, ResourceType resourceType) {
        //System.out.println(id);
        int layer = Character.getNumericValue(id.charAt(id.length() - 3));
        int slot = Character.getNumericValue(id.charAt(id.length() - 1));
        //System.out.println("LAYER: " + layer + " SLOT: " + slot);
        setSlot(layer, slot, resourceType);
    }

    public List<DepotSetting> convertToDepotSetting() {
        List<DepotSetting> settings = new ArrayList<>();
        for(Integer layerNumber: this.layers.keySet()) {
            List<ResourceType> currentLayer = this.layers.get(layerNumber);
            //Get the the resourceType stored in this layer
            ResourceType layerRes = null;
            for(ResourceType resType: currentLayer) {
                if (resType != null) {
                    layerRes = resType;
                    break;
                }
            }
            if (layerRes != null) {
                 //Layer is not empty, count how many resources
                int amount = 0;
                for(ResourceType resType: currentLayer) {
                    if (resType != null) {
                        if (resType != layerRes) {
                            //Invalid setting, abort
                            return null;
                        }
                        else {
                            amount++;
                        }
                    }
                }
                if (amount != 0) {
                    settings.add(new DepotSetting(layerNumber, layerRes, amount));
                }
            }
        }
        for (DepotSetting setting: settings) {
            System.out.println(setting);
        }
        return settings;
    }
}

