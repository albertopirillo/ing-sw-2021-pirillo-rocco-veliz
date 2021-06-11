package it.polimi.ingsw.network;

import it.polimi.ingsw.model.ResourceType;

import java.io.Serializable;

/**
 * <p>Simplified representation of the content of a Depot layer</p>
 * <p>A list of DepotSetting can be used to describe the whole Depot</p>
 * <p>Used to send using the network instructions to place resources</p>
 */
public class DepotSetting implements Serializable {
    private final int layerNumber;

    private final ResourceType resType;

    private final int amount;

    /**
     * Creates a new DepotSetting
     * @param layerNumber the number of the layer to place the resource in
     * @param resType the type of resource to place in the layer
     * @param amount the amount of that resource to place in the layer
     */
    public DepotSetting(int layerNumber, ResourceType resType, int amount) {
        this.layerNumber = layerNumber;
        this.resType = resType;
        this.amount = amount;
    }

    /**
     * Gets the number of the layer
     * @return the number of the layer
     */
    public int getLayerNumber() {
        return layerNumber;
    }

    /**
     * Gets the type of the resource
     * @return the ResourceType
     */
    public ResourceType getResType() {
        return resType;
    }

    /**
     * Gets the amount to place
     * @return the amount
     */
    public int getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "Place " + amount + " " + resType + " in layer number " + layerNumber;
    }
}
