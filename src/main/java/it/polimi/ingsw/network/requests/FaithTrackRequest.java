package it.polimi.ingsw.network.requests;

import it.polimi.ingsw.controller.MasterController;

public class FaithTrackRequest extends Request {

    @Override
    public void activateRequest(MasterController masterController) {
        masterController.getPlayerController().showFaithTrack();
    }
}
