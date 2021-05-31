package it.polimi.ingsw.network.requests;

import it.polimi.ingsw.controller.MasterController;

@Deprecated
public class ShowFaithTrackRequest extends Request {

    @Override
    public void activateRequest(MasterController masterController) {
        masterController.getGame().updateFaithTrack();
        masterController.getGame().notifyEndOfUpdates();
    }
}
