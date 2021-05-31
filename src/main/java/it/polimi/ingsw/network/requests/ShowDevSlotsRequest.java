package it.polimi.ingsw.network.requests;

import it.polimi.ingsw.controller.MasterController;

@Deprecated
public class ShowDevSlotsRequest extends Request {

    @Override
    public void activateRequest(MasterController masterController) {
        masterController.getGame().updateDevSlots();
        masterController.getGame().notifyEndOfUpdates();
    }
}
