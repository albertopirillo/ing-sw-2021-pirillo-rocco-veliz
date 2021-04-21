package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PlayerTest {

    @Test
    public void allResTest() throws AlreadyInAnotherLayerException, CannotContainFaithException, NotEnoughSpaceException, NegativeResAmountException, LayerNotEmptyException, InvalidLayerNumberException, InvalidKeyException, InvalidResourceException {
        //stub game = null
        Player player = new Player( "abc");
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

    @Test
    public void activateProductionTest() throws NegativeResAmountException, InvalidKeyException, NotEnoughResException {
        Player player = new Player( "abc");
        //Depot and StrongBox empty
        assertTrue(player.getAllResources().equals(new Resource(0,0,0,0)));
        //Stub DevelopmentCards
        Resource res1 = new Resource(1,2,3,4);
        Resource res2 = new Resource(1,2,3,1);
        DevelopmentCard dev1 = new DevelopmentCard(10, res1, CardColor.BLUE, 2, new ProductionPower(res1, res2));
        DevelopmentCard dev2 = new DevelopmentCard(10, res1, CardColor.BLUE, 2, new ProductionPower(res1, res2));
        DevelopmentCard dev3 = new DevelopmentCard(11, res2, CardColor.BLUE, 2, new ProductionPower(res2, res1));
        ArrayList<DevelopmentCard> devs = new ArrayList<>();
        devs.add(dev1);
        devs.add(dev2);
        player.activateProduction(devs);
        assertTrue(player.getAllResources().equals(new Resource(2,4,6,2)));
        ArrayList<DevelopmentCard> devs1 = new ArrayList<>();
        devs1.add(dev3);
        player.activateProduction(devs1);
        assertTrue(player.getAllResources().equals(new Resource(3,6,9,6)));
    }
}