package it.polimi.ingsw.network.requests;

import it.polimi.ingsw.controller.MasterController;

@Deprecated
public class ShowTempResRequest extends Request {

    @Override
    public void activateRequest(MasterController masterController) {
        masterController.getGame().updateTempRes();
        masterController.getGame().notifyEndOfUpdates();
    }
}
