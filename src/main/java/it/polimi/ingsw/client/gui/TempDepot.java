package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.model.ResourceType;
import it.polimi.ingsw.network.DepotSetting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>Simple implementation of the Depot, to be used in the Client</p>
 * <p>Items can be accessed by id or by the couple layer-slot</p>
 */
public class TempDepot {
    /**
     * A map that contains all the needed references to handle this Depot
     */
    private final Map<Integer, List<ResourceType>> layers = new HashMap<>();

    /**
     * Constructs a new Depot, initializing its structure according to the size of its slots
     */
    public TempDepot() {
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

    /**
     * Gets the given slot
     * @param layer layer the layer of the slot to set
     * @param slot the number of the slot to set
     * @return the type of resource contained in that slot
     */
    public ResourceType getSlot(int layer, int slot) {
        return this.layers.get(layer).get(slot - 1);
    }

    /**
     * Gets the given slot
     * @param id the id of the ImageView representing that slot
     * @return the type of resource contained in that slot
     */
    public ResourceType getSlot(String id) {
        int layer = Character.getNumericValue(id.charAt(id.length() - 3));
        int slot = Character.getNumericValue(id.charAt(id.length() - 1));
        return getSlot(layer, slot);
    }

    /**
     * Sets the given Depot's slot
     * @param layer the layer of the slot to set
     * @param slot the number of the slot to set
     * @param resType the type of resource to be inserted
     */
    public void setSlot(int layer, int slot, ResourceType resType) {
        this.layers.get(layer).set(slot - 1, resType);
        //System.out.println("LAYER: " + layer + " SLOT: " + slot + " RES: " + resType);
    }

    /**
     * Sets the given Depot's slot
     * @param id the id of the ImageView representing that slot
     * @param resourceType the type of resource to be inserted
     */
    public void setSlot(String id, ResourceType resourceType) {
        int layer = Character.getNumericValue(id.charAt(id.length() - 3));
        int slot = Character.getNumericValue(id.charAt(id.length() - 1));
        setSlot(layer, slot, resourceType);
    }

    /**
     * <p>Uses the internal model to read the Depot's content</p>
     * <p>Converts that content to a List of DepotSetting/p>
     * @return  a List of DepotSetting representing the content of the Depot
     */
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
                        if (resType != layerRes) return null;
                        else amount++;
                    }
                }
                if (amount != 0) {
                    settings.add(new DepotSetting(layerNumber, layerRes, amount));
                }
            }
        }
        //System.out.println("DEPOT SETTINGS:");
        //for(DepotSetting setting: settings) System.out.println(setting);
        return settings;
    }
}

