package it.polimi.ingsw.network.requests;

import it.polimi.ingsw.controller.MasterController;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.network.DepotSetting;

import java.util.List;

public class PlaceResourceRequest extends Request {

    private final Resource toDiscard;
    private final List<DepotSetting> toPlace;

    public PlaceResourceRequest(Resource toDiscard, List<DepotSetting> toPlace) {
        super();
        this.toDiscard = toDiscard;
        this.toPlace = toPlace;
    }

    public void activateRequest(MasterController masterController) {
        masterController.getPlayerController().placeResource(toDiscard, toPlace);
    }
}