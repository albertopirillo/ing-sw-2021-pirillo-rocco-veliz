package it.polimi.ingsw.controller;

import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.network.DepotSetting;

import java.util.List;

/**
 * <p>The controller that handles the placement of the resources taken from the Market </p>
 * <p>Performs every check and throws an exception is something is not permitted</p>
 */
public class ResourceController {

    /**
     * Reference to the associated MasterController
     */
    private final MasterController controller;
    /**
     * The TempResource to take the resources to be placed from
     */
    private final TempResource tempRes;

    /**
     * Constructs a new ResourceController, setting is references
     * @param controller reference to the MainController
     * @param tempRes reference to the TempResource object
     */
    public ResourceController(MasterController controller, TempResource tempRes) {
        this.controller = controller;
        this.tempRes = tempRes;
    }

    /**
     * Gets the associated TempResource object
     * @return  the TempResource object
     */
    public TempResource getTempRes() {
        return tempRes;
    }

    /**
     * Algorithm to place the resources taken from the market
     * @param toDiscard all the resources that player want to discards
     * @param settings data structure that tell where to place the taken resources
     * @param fullDepot whether the request is coming from a CLI or a GUI
     * @throws WrongDepotInstructionsException if incorrect instructions were provided
     */
    public void handleResource(Resource toDiscard, List<DepotSetting> settings, boolean fullDepot) throws InvalidKeyException, NegativeResAmountException, WrongDepotInstructionsException, InvalidResourceException, LayerNotEmptyException, NotEnoughSpaceException, InvalidLayerNumberException, CannotContainFaithException, AlreadyInAnotherLayerException {
        if(this.tempRes.isEmpty()) throw new WrongDepotInstructionsException("There are no resources to be placed now");
        if(settings == null) throw new WrongDepotInstructionsException("You provided incorrect instructions to place those resources");
        Player player = this.controller.getGame().getActivePlayer();
        Resource tempRes = this.tempRes.getToHandle();
        Resource currentDepot = player.getPersonalBoard().getDepot().queryAllRes();

        //Check if there are enough resources to be discarded
        if(!tempRes.compare(toDiscard))
            throw new WrongDepotInstructionsException("You are trying to discard more resources than you can");

        //Check if the provided settings are correct to store this resource
        Resource settingRes = new Resource(0,0,0,0);
        for(DepotSetting setting: settings) {
            settingRes.modifyValue(setting.getResType(), setting.getAmount());
        }

        //Temporary resource, represents the resources from tempRes that wont be discarded
        Resource wontBeDiscarded = new Resource(tempRes.getMap());
        for(ResourceType key: toDiscard.keySet()) {
            wontBeDiscarded.modifyValue(key, - toDiscard.getValue(key));
        }

        Depot depot = player.getPersonalBoard().getDepot();
        if (fullDepot) {
            //Instructions and checks for GUI
            if(!(currentDepot.sum(wontBeDiscarded).equals(settingRes)))
                throw new WrongDepotInstructionsException("You provided incorrect instructions to place those resources");
            //Place those resources in the depot
            depot.setFromDepotSetting(settings);
        }
        else {
            //Instructions and checks for CLI
            if (!settingRes.equals(wontBeDiscarded) || !depot.canInsertInDepot(settings))
                throw new WrongDepotInstructionsException("You provided incorrect instructions to place those resources");
            //Place those resources in the depot
            for(DepotSetting setting: settings) {
                depot.modifyLayer(setting.getLayerNumber(), setting.getResType(), setting.getAmount());
            }
        }

        //Discard those resources
        player.discardRes(toDiscard);
        for(ResourceType key: toDiscard.keySet()) {
            tempRes.modifyValue(key, - toDiscard.getValue(key));
        }

        //Reset TempResource object if no exceptions were thrown
        this.tempRes.setToHandle(null);
    }
}
