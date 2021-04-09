package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @Test
    public void strategiesTest(){
        Game game = new Game(); //Stub
        Player player = new Player(false, "abc", game, 1, 1);
        //testing set dev strategy
        Resource resource1 = new Resource(1,2,3,4);
        LeaderAbility ability = new Discount(ResourceType.COIN, 2);
        ResLeaderCard res = new ResLeaderCard(2, ability ,resource1);
        assertNull(player.getDevStrategy());
        //test correct strategy setting
        res.getSpecialAbility().activate(player);
        assertNotNull(player.getDevStrategy());
        //correct ability
        assertEquals(player.getDevStrategy().getDiscounts()[0].getResource(), ResourceType.COIN);
        assertEquals(player.getDevStrategy().getDiscounts()[0].getAmount(), 2);

        //testing set production strategy
        Resource resource2 = new Resource(4,3,2,1);
        LeaderAbility ability2 = new ExtraProduction(new ProductionPower(resource1, resource2));
        ResLeaderCard res2 = new ResLeaderCard(1, ability2 ,resource1);
        assertNull(player.getProdStrategy());
        res2.getSpecialAbility().activate(player);
        //testing strategy setting
        assertNotNull(player.getProdStrategy());
        assertEquals(player.getProdStrategy().getProduction()[0].getProduction().getInput().getAllRes(), resource1.getAllRes());
        assertEquals(player.getProdStrategy().getProduction()[0].getProduction().getOutput().getAllRes(), resource2.getAllRes());

        //testing set production strategy
        LeaderAbility ability3 = new ChangeWhiteMarbles(ResourceType.SERVANT);
        ResLeaderCard res3 = new ResLeaderCard(1, ability3 ,resource1);
        assertNull(player.getResStrategy());
        res3.getSpecialAbility().activate(player);
        //testing strategy setting
        assertNotNull(player.getResStrategy());
        assertEquals(player.getResStrategy().getResType()[0].getResourceType(), ResourceType.SERVANT);
    }

    @Test
    public void allResTest() throws AlreadyInAnotherLayerException, CannotContainFaithException, NotEnoughSpaceException, NegativeResAmountException, LayerNotEmptyException, InvalidLayerNumberException, InvalidKeyException, InvalidResourceException {
        Game game = new Game(); //Stub
        Player player = new Player(false, "abc", game, 0, 0);
        PersonalBoard personalBoard = player.getPersonalBoard();
        Depot depot = personalBoard.getDepot();
        Strongbox strongbox = personalBoard.getStrongbox();

        depot.modifyLayer(1, ResourceType.SERVANT, 1);
        depot.modifyLayer(3, ResourceType.COIN, 2);
        strongbox.addResources(new Resource(1, 2, 0, 4));

        Resource playerRes = player.getAllResources();
        Resource resCheck = new Resource(1, 4, 0, 5);
        for (ResourceType key: ResourceType.values()) {
            if (key != ResourceType.FAITH) {
                assertEquals(resCheck.getValue(key), playerRes.getValue(key));
            }
        }
    }
}