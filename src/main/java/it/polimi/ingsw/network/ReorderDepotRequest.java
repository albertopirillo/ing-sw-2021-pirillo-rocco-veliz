package it.polimi.ingsw.network;

import it.polimi.ingsw.controller.MasterController;

public class ReorderDepotRequest extends Request {

    public ReorderDepotRequest() {
    }

    private int fromLayer;

    private int toLayer;

    private int amount;

    public void activateRequest(MasterController masterController) {
        // TODO implement here
    }
}