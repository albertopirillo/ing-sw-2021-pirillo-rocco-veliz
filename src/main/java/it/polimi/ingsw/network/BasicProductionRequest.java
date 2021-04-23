package it.polimi.ingsw.network;

import it.polimi.ingsw.controller.MasterController;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.ResourceType;

public class BasicProductionRequest extends Request {

    private ResourceType input1;
    private ResourceType input2;
    private ResourceType output;
    private Resource fromDepot;
    private Resource fromStrongbox;

    public BasicProductionRequest() {
        super();
    }

    public void activateRequest(MasterController masterController) {
        masterController.getPlayerController().basicProduction(input1, input2, output, fromDepot, fromStrongbox);
    }
}