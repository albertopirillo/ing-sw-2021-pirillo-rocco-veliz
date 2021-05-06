package it.polimi.ingsw.network;

import it.polimi.ingsw.controller.MasterController;

public class FaithTrackRequest extends Request{

    @Override
    public void activateRequest(MasterController masterController) {
        masterController.getGame().showFaithTrack();
    }
}
