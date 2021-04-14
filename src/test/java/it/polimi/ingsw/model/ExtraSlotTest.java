package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExtraSlotTest {

    @Test
    public void activate() throws FullCardDeckException, AlreadyInAnotherLayerException, InvalidResourceException, CannotContainFaithException, NotEnoughSpaceException, NegativeResAmountException, LayerNotEmptyException, InvalidLayerNumberException, LeaderAbilityAlreadyActive, TooManyLeaderAbilitiesException {
        Player player = new Player(false, "abc");
        Depot depot = player.getPersonalBoard().getDepot();
        assertInstanceOf(ConcreteDepot.class, depot);
        depot.modifyLayer(2, ResourceType.COIN, 1);

        LeaderAbility ability = new ExtraSlot(ResourceType.SERVANT);
        Resource cost = new Resource(0, 2, 3, 1);
        ResLeaderCard res1 = new ResLeaderCard(2, ability, cost);
        player.setLeaderCards(0, res1);
        player.useLeader(0, LeaderAction.USE_ABILITY);
        depot = player.getPersonalBoard().getDepot();
        assertInstanceOf(DepotDecorator.class, depot);

        depot.modifyLayer(4, ResourceType.SERVANT,2);
        assertEquals(2, depot.getLayer(4).getAmount());
        assertEquals(ResourceType.SERVANT, depot.getLayer(4).getResource());

        LeaderAbility ability2 = new ExtraSlot(ResourceType.SHIELD);
        Resource cost2 = new Resource(4, 1, 3, 0);
        ResLeaderCard res2 = new ResLeaderCard(1, ability2,cost2);
        player.setLeaderCards(1, res2);
        player.useLeader(1, LeaderAction.USE_ABILITY);
        depot = player.getPersonalBoard().getDepot();
        assertInstanceOf(DepotDecorator.class, depot);

        depot.modifyLayer(5, ResourceType.SHIELD, 1);
        assertEquals(1, depot.getLayer(5).getAmount());
        assertEquals(ResourceType.SHIELD, depot.getLayer(5).getResource());

        assertThrows(LeaderAbilityAlreadyActive.class, () -> player.useLeader(0, LeaderAction.USE_ABILITY));
        LeaderAbility ability3 = new ExtraSlot(ResourceType.STONE);
        Resource cost3 = new Resource(1, 2, 4, 1);
        ResLeaderCard res3 = new ResLeaderCard(2, ability3, cost3);
        player.setLeaderCards(0, res3);
        assertThrows(InvalidLayerNumberException.class, () -> player.useLeader(0, LeaderAction.USE_ABILITY));
    }
}