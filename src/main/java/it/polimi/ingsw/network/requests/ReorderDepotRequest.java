package it.polimi.ingsw.network.requests;

import it.polimi.ingsw.controller.MasterController;

public class ReorderDepotRequest extends Request {

    private final int fromLayer;
    private final int toLayer;
    private final int amount;

    public ReorderDepotRequest(int fromLayer, int toLayer, int amount) {
        super();
        this.fromLayer = fromLayer;
        this.toLayer = toLayer;
        this.amount = amount;
    }

    @Override
    public void activateRequest(MasterController masterController) {
        masterController.getPlayerController().reorderDepot(fromLayer, toLayer, amount);
    }
}