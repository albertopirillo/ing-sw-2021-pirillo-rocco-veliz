package it.polimi.ingsw.network.requests;

import it.polimi.ingsw.controller.MasterController;

public class ShowTempResRequest extends Request {

    @Override
    public void activateRequest(MasterController masterController) {
        masterController.getGame().showTempRes();
    }
}
