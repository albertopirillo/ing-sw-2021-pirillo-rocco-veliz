package it.polimi.ingsw.network;

import it.polimi.ingsw.controller.MasterController;
import it.polimi.ingsw.model.Resource;

import java.util.List;

public class DevProductionRequest extends Request {

    private final List<Integer> devSlots;
    private final Resource fromDepot;
    private final Resource fromStrongbox;

    public DevProductionRequest(List<Integer> devSlots, Resource fromDepot, Resource fromStrongbox) {
        super();
        this.devSlots = devSlots;
        this.fromDepot = fromDepot;
        this.fromStrongbox = fromStrongbox;
    }

    public void activateRequest(MasterController masterController) {
        masterController.getPlayerController().activateProduction(fromDepot, fromStrongbox, devSlots);
    }
}