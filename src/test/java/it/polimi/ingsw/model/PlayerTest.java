package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

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
    public void activateProductionTest() throws NegativeResAmountException {
        Player player = new Player( "abc");
        //Depot and StrongBox empty
        assertEquals(player.getAllResources(), new Resource(0, 0, 0, 0));
        //Stub DevelopmentCards
        Resource res1 = new Resource(1,2,3,4);
        Resource res2 = new Resource(1,2,3,1);
        DevelopmentCard dev1 = new DevelopmentCard(10, res1, CardColor.BLUE, 2, new ProductionPower(res1, res2), "");
        DevelopmentCard dev2 = new DevelopmentCard(10, res1, CardColor.BLUE, 2, new ProductionPower(res1, res2), "");
        DevelopmentCard dev3 = new DevelopmentCard(11, res2, CardColor.BLUE, 2, new ProductionPower(res2, res1), "");
        ArrayList<DevelopmentCard> devs = new ArrayList<>();
        devs.add(dev1);
        devs.add(dev2);
        player.activateProduction(devs);
        assertEquals(player.getAllTempResources(), new Resource(2, 4, 6, 2));
        ArrayList<DevelopmentCard> devs1 = new ArrayList<>();
        devs1.add(dev3);
        player.activateProduction(devs1);
        assertEquals(player.getAllTempResources(), new Resource(3, 6, 9, 6));
        //transfer the temp resources into the strongbox and check
        Strongbox strongbox = player.getPersonalBoard().getStrongbox();
        strongbox.transferTempRes();
        assertEquals(player.getAllResources(), new Resource(3, 6, 9, 6));

    }

    @Test
    public void useLeaderTest() throws FullCardDeckException, TooManyLeaderAbilitiesException, CostNotMatchingException, InvalidLayerNumberException, NoLeaderAbilitiesException, NegativeResAmountException, LeaderAbilityAlreadyActive {

        Game game = new SoloGame(true);
        Player player = new Player( "abc");
        player.setGame(game);

        assertThrows(NoLeaderAbilitiesException.class, () -> player.useLeader(-1, LeaderAction.USE_ABILITY));
        assertThrows(NoLeaderAbilitiesException.class, () -> player.useLeader(2, LeaderAction.DISCARD));

        ChangeWhiteMarblesAbility ability1 = new ChangeWhiteMarblesAbility(ResourceType.COIN);
        LeaderCard leader1 = new ResLeaderCard(2, ability1, new Resource(0,1,2,2));
        player.addLeaderCard(leader1);
        //assertThrows(CostNotMatchingException.class, () -> player.useLeader(0, LeaderAction.USE_ABILITY));

        player.useLeader(0, LeaderAction.DISCARD);
        assertTrue(player.getActiveLeaderAbilities().isEmpty());
        assertTrue(player.getLeaderCards().isEmpty());
        assertEquals(1, player.getPlayerFaith());
    }

    @Test
    public void resLeaderTest() throws InvalidResourceException, LayerNotEmptyException, NotEnoughSpaceException, InvalidLayerNumberException, CannotContainFaithException, AlreadyInAnotherLayerException, NegativeResAmountException, TooManyLeaderAbilitiesException, CostNotMatchingException, NoLeaderAbilitiesException, LeaderAbilityAlreadyActive {
        Player player = new Player( "abc");
        ChangeWhiteMarblesAbility ability1 = new ChangeWhiteMarblesAbility(ResourceType.COIN);
        LeaderCard leader1 = new ResLeaderCard(2, ability1, new Resource(0,1,2,2));
        player.addLeaderCard(leader1);

        Depot depot = player.getPersonalBoard().getDepot();
        Strongbox strongbox = player.getPersonalBoard().getStrongbox();
        depot.modifyLayer(2, ResourceType.SHIELD, 2);
        strongbox.addResources(new Resource(3,1,0,2));

        player.useLeader(0, LeaderAction.USE_ABILITY);
        assertTrue(player.getLeaderCards().get(0).isActive());
        assertEquals(ability1, player.getActiveLeaderAbilities().get(0));
        assertEquals(2, depot.getLayer(2).getAmount());
        assertEquals(new Resource(3,1,0,2), strongbox.queryAllRes());
    }
}