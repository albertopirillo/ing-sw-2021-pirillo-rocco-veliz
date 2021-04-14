package it.polimi.ingsw.controller;

import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.model.Depot;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Resource;

import java.util.List;

public class DepotController {

    public static void handleDepot(Player player, Resource resource, List<DepotSetting> settings) throws WrongDepotInstructionsException {
        Depot depot = player.getPersonalBoard().getDepot();
        Resource checkRes = new Resource(0,0,0,0);

        try {
            //Check if settings are correct to store this resource
            for(DepotSetting setting: settings) {
                checkRes.modifyValue(setting.getResType(), setting.getAmount());
                if (!checkRes.equals(resource)) throw new WrongDepotInstructionsException();
            }
            for(DepotSetting setting: settings) {
                depot.modifyLayer(setting.getLayerNumber(), setting.getResType(), setting.getAmount());
                }
        } catch (NegativeResAmountException e) {
            e.printStackTrace();
        } catch (CannotContainFaithException e) {
            e.printStackTrace();
        } catch (LayerNotEmptyException e) {
            e.printStackTrace();
        } catch (NotEnoughSpaceException e) {
            e.printStackTrace();
        } catch (InvalidLayerNumberException e) {
            e.printStackTrace();
        } catch (AlreadyInAnotherLayerException e) {
            e.printStackTrace();
        } catch (InvalidResourceException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
    }
}
