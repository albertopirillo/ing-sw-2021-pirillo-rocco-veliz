package it.polimi.ingsw.controller;

import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.model.Depot;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.ResourceType;
import it.polimi.ingsw.network.DepotSetting;

import java.util.List;

public class ResourceController {

    private final MasterController controller;
    private Resource toHandle;

    public ResourceController(MasterController controller) {
        this.controller = controller;
        this.toHandle = null;
    }

    public void setToHandle(Resource toHandle) {
        this.toHandle = toHandle;
    }

    public Resource getToHandle() {
        return this.toHandle;
    }

    public boolean isEmpty() {
        return (this.toHandle == null);
    }

    public void handleResource(Player player, Resource toDiscard, List<DepotSetting> settings) throws NotEnoughResException, InvalidKeyException, NegativeResAmountException, WrongDepotInstructionsException, InvalidResourceException, LayerNotEmptyException, NotEnoughSpaceException, InvalidLayerNumberException, CannotContainFaithException, AlreadyInAnotherLayerException {
        if(this.toHandle == null) throw new WrongDepotInstructionsException("There are no resources to be placed now");

        //Check if there are enough resources to be discarded
        if(!this.toHandle.compare(toDiscard))
            throw new WrongDepotInstructionsException("You are trying to discard more resources than you can");
        player.discardRes(toDiscard);
        for(ResourceType key: toDiscard.keySet()) {
            this.toHandle.modifyValue(key, - toDiscard.getValue(key));
        }

        //Check if the provided settings are correct to store this resource
        Resource checkRes = new Resource(0,0,0,0);
        for(DepotSetting setting: settings) {
            checkRes.modifyValue(setting.getResType(), setting.getAmount());
        }
        Depot depot = player.getPersonalBoard().getDepot();
        if (!checkRes.equals(this.toHandle) || !depot.canInsert(settings))
            throw new WrongDepotInstructionsException("You provided incorrect instructions to place those resources");

        //Place those resources in the depot
        for(DepotSetting setting: settings) {
            depot.modifyLayer(setting.getLayerNumber(), setting.getResType(), setting.getAmount());
        }
        //Reset toPlace field if no exceptions were thrown
        this.toHandle = null;
    }
}
