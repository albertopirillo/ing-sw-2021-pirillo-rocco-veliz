package it.polimi.ingsw.controller;

import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.network.DepotSetting;

import java.util.List;

public class ResourceController {

    private final MasterController controller;
    private final TempResource tempRes;

    public ResourceController(MasterController controller, TempResource tempRes) {
        this.controller = controller;
        this.tempRes = tempRes;
    }

    public TempResource getTempRes() {
        return tempRes;
    }

    public void handleResource(Resource toDiscard, List<DepotSetting> settings) throws NotEnoughResException, InvalidKeyException, NegativeResAmountException, WrongDepotInstructionsException, InvalidResourceException, LayerNotEmptyException, NotEnoughSpaceException, InvalidLayerNumberException, CannotContainFaithException, AlreadyInAnotherLayerException {
        if(this.tempRes.isEmpty()) throw new WrongDepotInstructionsException("There are no resources to be placed now");
        Resource tempRes = this.tempRes.getToHandle();
        Player player = this.controller.getGame().getActivePlayer();

        //Check if there are enough resources to be discarded
        if(!tempRes.compare(toDiscard))
            throw new WrongDepotInstructionsException("You are trying to discard more resources than you can");
        player.discardRes(toDiscard);
        for(ResourceType key: toDiscard.keySet()) {
            tempRes.modifyValue(key, - toDiscard.getValue(key));
        }

        //Check if the provided settings are correct to store this resource
        Resource checkRes = new Resource(0,0,0,0);
        for(DepotSetting setting: settings) {
            checkRes.modifyValue(setting.getResType(), setting.getAmount());
        }
        Depot depot = player.getPersonalBoard().getDepot();
        if (!checkRes.equals(tempRes) || !depot.canInsert(settings))
            throw new WrongDepotInstructionsException("You provided incorrect instructions to place those resources");

        //Place those resources in the depot
        for(DepotSetting setting: settings) {
            depot.modifyLayer(setting.getLayerNumber(), setting.getResType(), setting.getAmount());
        }
        //Reset TempResource object if no exceptions were thrown
        this.tempRes.setToHandle(null);
    }
}
