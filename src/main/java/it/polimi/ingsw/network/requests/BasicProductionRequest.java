package it.polimi.ingsw.network.requests;

import it.polimi.ingsw.controller.MasterController;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.ResourceType;

public class BasicProductionRequest extends Request {

    private final ResourceType input1;
    private final ResourceType input2;
    private final ResourceType output;
    private final Resource fromDepot;
    private final Resource fromStrongbox;

    public BasicProductionRequest(ResourceType input1, ResourceType input2, ResourceType output, Resource fromDepot, Resource fromStrongbox) {
        super();
        this.input1 = input1;
        this.input2 = input2;
        this.output = output;
        this.fromDepot = fromDepot;
        this.fromStrongbox = fromStrongbox;
    }

    public void activateRequest(MasterController masterController) {
        masterController.getPlayerController().basicProduction(input1, input2, output, fromDepot, fromStrongbox);
    }
}