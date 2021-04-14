package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @Test
    public void allResTest() throws AlreadyInAnotherLayerException, CannotContainFaithException, NotEnoughSpaceException, NegativeResAmountException, LayerNotEmptyException, InvalidLayerNumberException, InvalidKeyException, InvalidResourceException {
        //stub game = null
        Player player = new Player(false, "abc");
        PersonalBoard personalBoard = player.getPersonalBoard();
        Depot depot = personalBoard.getDepot();
        Strongbox strongbox = personalBoard.getStrongbox();

        depot.modifyLayer(1, ResourceType.SERVANT, 1);
        depot.modifyLayer(3, ResourceType.COIN, 2);
        strongbox.addResources(new Resource(1, 2, 0, 4));

        Resource playerRes = player.getAllResources();
        Resource resCheck = new Resource(1, 4, 0, 5);
        for (ResourceType key: ResourceType.values()) {
            if (key != ResourceType.FAITH && key != ResourceType.ALL) {
                assertEquals(resCheck.getValue(key), playerRes.getValue(key));
            }
        }
    }
}