package it.polimi.ingsw.network.requests;

import it.polimi.ingsw.controller.MasterController;

@Deprecated
public class ShowStoragesRequest extends Request{

    @Override
    public void activateRequest(MasterController masterController) {
        masterController.getGame().updateStorages();
        masterController.getGame().notifyEndOfUpdates();
    }
}
