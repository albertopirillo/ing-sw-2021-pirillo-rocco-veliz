package it.polimi.ingsw.network.requests;

import it.polimi.ingsw.controller.MasterController;

public class ShowMarketTrayRequest extends Request {

    @Override
    public void activateRequest(MasterController masterController) {
        masterController.getGame().updateMarketTray();
        masterController.getGame().notifyEndOfUpdates();
    }
}
