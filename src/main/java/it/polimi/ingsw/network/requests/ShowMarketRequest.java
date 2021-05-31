package it.polimi.ingsw.network.requests;

import it.polimi.ingsw.controller.MasterController;

@Deprecated
public class ShowMarketRequest extends Request {

    @Override
    public void activateRequest(MasterController masterController) {
        masterController.getGame().updateMarket();
        masterController.getGame().notifyEndOfUpdates();
    }
}
