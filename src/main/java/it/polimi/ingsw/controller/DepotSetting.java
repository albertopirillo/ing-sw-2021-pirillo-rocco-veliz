package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.ResourceType;

public class DepotSetting {
    private final int layerNumber;

    private final ResourceType resType;

    private final int amount;

    public DepotSetting(int layerNumber, ResourceType resType, int amount) {
        this.layerNumber = layerNumber;
        this.resType = resType;
        this.amount = amount;
    }

    public int getLayerNumber() {
        return layerNumber;
    }

    public ResourceType getResType() {
        return resType;
    }

    public int getAmount() {
        return amount;
    }
}
