package it.polimi.ingsw.controller;

import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.model.Depot;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Resource;

import java.util.List;

public class DepotController {

    private final Controller controller;

    public DepotController(Controller controller) {
        this.controller = controller;
    }

    public void handleDepot(Player player, Resource resource, List<DepotSetting> settings) throws WrongDepotInstructionsException {
        Depot depot = player.getPersonalBoard().getDepot();
        Resource checkRes = new Resource(0,0,0,0);

        try {
            //Check if settings are correct to store this resource
            for(DepotSetting setting: settings) {
                checkRes.modifyValue(setting.getResType(), setting.getAmount());
                if (!checkRes.equals(resource)) throw new WrongDepotInstructionsException();
            }
            //TODO: discard
            for(DepotSetting setting: settings) {
                depot.modifyLayer(setting.getLayerNumber(), setting.getResType(), setting.getAmount());
                }
        } catch (NegativeResAmountException | CannotContainFaithException | LayerNotEmptyException | NotEnoughSpaceException | InvalidLayerNumberException | AlreadyInAnotherLayerException | InvalidResourceException | InvalidKeyException e) {
            controller.setException(e);
        }
    }
}
