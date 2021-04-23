package it.polimi.ingsw.network;

import it.polimi.ingsw.controller.MasterController;
import it.polimi.ingsw.model.Resource;

public class InitialResRequest extends Request {

    private final Resource res;

    public InitialResRequest(Resource res) {
        super();
        this.res = res;
    }

    public void activateRequest(MasterController masterController) {
        // TODO implement here
    }
}