package it.polimi.ingsw.network;

import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.ResourceType;

public class BasicProductionRequest extends Request {

    public BasicProductionRequest() {
    }

    private ResourceType input1;

    private ResourceType input2;

    private ResourceType output;

    private Resource fromDepot;

    private Resource fromStrongbox;

    public void activateRequest() {
        // TODO implement here
    }
}