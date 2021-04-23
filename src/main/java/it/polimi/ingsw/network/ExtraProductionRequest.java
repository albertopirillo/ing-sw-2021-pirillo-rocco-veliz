package it.polimi.ingsw.network;

import it.polimi.ingsw.controller.MasterController;
import it.polimi.ingsw.model.AbilityChoice;
import it.polimi.ingsw.model.Resource;

public class ExtraProductionRequest extends Request {

    public ExtraProductionRequest() {
    }

    private AbilityChoice choice;

    private Resource fromDepot;

    private Resource fromStrongbox;

    public void activateRequest(MasterController masterController) {
        // TODO implement here
    }
}