package it.polimi.ingsw.network;

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

    public void activateRequest(MasterController masterController) {
        //TODO: implement here
    }
}