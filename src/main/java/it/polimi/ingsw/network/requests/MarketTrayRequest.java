package it.polimi.ingsw.network.requests;

import it.polimi.ingsw.controller.MasterController;

public class MarketTrayRequest extends Request{
    @Override
    public void activateRequest(MasterController masterController) {
        masterController.getPlayerController().showMarketTray();
    }
}
