package it.polimi.ingsw.network.requests;

import it.polimi.ingsw.controller.MasterController;
import it.polimi.ingsw.model.AbilityChoice;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.ResourceType;

public class ExtraProductionRequest extends Request {

    private final AbilityChoice choice;
    private final Resource fromDepot;
    private final Resource fromStrongbox;
    private final ResourceType res;

    public ExtraProductionRequest(AbilityChoice choice, Resource fromDepot, Resource fromStrongbox, ResourceType res) {
        super();
        this.choice = choice;
        this.fromDepot = fromDepot;
        this.fromStrongbox = fromStrongbox;
        this.res = res;
    }

    public void activateRequest(MasterController masterController) {
        masterController.getPlayerController().extraProduction(choice, fromDepot, fromStrongbox, res);
    }
}