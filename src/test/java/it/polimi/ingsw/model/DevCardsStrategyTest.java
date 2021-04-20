package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DevCardsStrategyTest {

    @Test
    public void buyTest() throws FullCardDeckException, DeckEmptyException, CannotContainFaithException, NotEnoughSpaceException, NegativeResAmountException, CostNotMatchingException, NotEnoughResException, InvalidKeyException, InvalidLayerNumberException, AlreadyInAnotherLayerException, LayerNotEmptyException, InvalidResourceException, NoLeaderAbilitiesException, InvalidAbilityChoiceException, InvalidNumSlotException, DevSlotEmptyException {
        Player player = new Player(true, "abc");
        Game game = new Game(true);
        player.setGame(game);

        Depot depot = player.getPersonalBoard().getDepot();
        Strongbox strongbox = player.getPersonalBoard().getStrongbox();
        depot.modifyLayer(3, ResourceType.COIN, 3);
        strongbox.addResources(new Resource(2,1,0,3));

        Card card = player.getGame().getMarket().getCard(2, CardColor.BLUE);
        //DevelopmentCard{cost={{STONE=0, SHIELD=0, COIN=4, SERVANT=0}}, type=BLUE, level=2, prodPower={input={{STONE=0, SHIELD=0, COIN=0, SERVANT=1}}, output={{FAITH=2}}}}


        player.buyDevCard(2, CardColor.BLUE, 0, AbilityChoice.STANDARD,
                new Resource(0,3,0,0),
                new Resource(0,1,0,0));

        assertEquals(card, player.getPersonalBoard().getSlot(0).getTopCard());
        assertEquals(new Resource(0,0,0,0), depot.queryAllRes());
        assertEquals(new Resource(2,0,0,3), strongbox.queryAllRes());
    }

    @Test
    public void CostNotMatchingTest() throws FullCardDeckException, AlreadyInAnotherLayerException, InvalidResourceException, CannotContainFaithException, NotEnoughSpaceException, NegativeResAmountException, LayerNotEmptyException, InvalidLayerNumberException {
        Player player = new Player(true, "abc");
        Game game = new Game(true);
        player.setGame(game);

        Depot depot = player.getPersonalBoard().getDepot();
        //DevelopmentCard{cost={{SHIELD=2}}, type=GREEN, level=1, prodPower={input={{COIN=1}}, output={{FAITH=1}}}}

        //Player hasn't got the needed resources
        assertThrows(NotEnoughResException.class, () -> player.buyDevCard(1, CardColor.GREEN, 0, AbilityChoice.STANDARD,
                new Resource(0,0,2,0),
                new Resource(0,0,0,0)));

        depot.modifyLayer(3, ResourceType.SHIELD, 3);
        //Player has enough resources but it is not proving enough to buy the card
        assertThrows(CostNotMatchingException.class, () -> player.buyDevCard(1, CardColor.BLUE, 0, AbilityChoice.STANDARD,
                new Resource(0,0,1,0),
                new Resource(0,0,0,0)));

        //Player has provided more resources than needed to buy the card
        assertThrows(CostNotMatchingException.class, () -> player.buyDevCard(1, CardColor.BLUE, 0, AbilityChoice.STANDARD,
                new Resource(0,0,3,0),
                new Resource(0,0,0,0)));
   }

    @Test
    public void abilityTest() throws FullCardDeckException, DeckEmptyException, TooManyLeaderAbilitiesException, CostNotMatchingException, InvalidAbilityChoiceException, NotEnoughSpaceException, NoLeaderAbilitiesException, CannotContainFaithException, NotEnoughResException, NegativeResAmountException, InvalidKeyException, InvalidNumSlotException, DevSlotEmptyException {
        Player player = new Player(true, "abc");
        Game game = new Game(true);
        player.setGame(game);

        Depot depot = player.getPersonalBoard().getDepot();
        Strongbox strongbox = player.getPersonalBoard().getStrongbox();

        Card card = player.getGame().getMarket().getCard(1, CardColor.PURPLE);
        //DevelopmentCard{cost={{SERVANT=3}}, type=PURPLE, level=1, prodPower={input={{COIN=1}}, output={{STONE=1, SHIELD=1, COIN=0, SERVANT=1}}}}

        Discount discount = new Discount(ResourceType.SERVANT, 2);
        discount.activate(player);
        strongbox.addResources(new Resource(0,0,0,2));
        player.buyDevCard(1, CardColor.PURPLE, 0, AbilityChoice.FIRST,
                new Resource(0,0,0,0),
                new Resource(0,0,0,1));

        assertEquals(card, player.getPersonalBoard().getSlot(0).getTopCard());
        assertEquals(new Resource(0,0,0,0), depot.queryAllRes());
        assertEquals(new Resource(0,0,0,1), strongbox.queryAllRes());
   }
}