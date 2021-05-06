package it.polimi.ingsw.network.requests;

import it.polimi.ingsw.controller.MasterController;
import it.polimi.ingsw.network.requests.Request;

public class FaithTrackRequest extends Request {

    @Override
    public void activateRequest(MasterController masterController) {
        masterController.getGame().showFaithTrack();
    }
}
