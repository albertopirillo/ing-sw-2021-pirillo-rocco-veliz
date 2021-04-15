package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

class DevCardsStrategyTest {

    //TODO: testing

    @Test
    public void buyTest() throws FullCardDeckException, DeckEmptyException, CannotContainFaithException, NotEnoughSpaceException, NegativeResAmountException, CostNotMatchingException, NotEnoughResException, InvalidKeyException, InvalidLayerNumberException, AlreadyInAnotherLayerException, LayerNotEmptyException, InvalidResourceException, NoLeaderAbilitiesException, InvalidAbilityChoiceException {
        Player player = new Player(false, "abc");
        List<Player> players = new ArrayList<>();
        players.add(player);
        Game game = new Game(1, players);
        player.setGame(game);

        Depot depot = player.getPersonalBoard().getDepot();
        Strongbox strongbox = player.getPersonalBoard().getStrongbox();

        Resource cost1 = new Resource(1, 0, 1, 1);
        DevelopmentCard card1 = new DevelopmentCard(3, cost1, CardColor.GREEN, 1, null);

        Resource fromDepot = new Resource(1, 0, 1, 0);
        Resource fromStrongbox = new Resource(0, 0, 0, 1);

        depot.modifyLayer(1, ResourceType.STONE, 1);
        depot.modifyLayer(2, ResourceType.COIN, 2);
        depot.modifyLayer(3, ResourceType.SHIELD, 2);
        strongbox.addResources(new Resource(0,0,0,1));

    }

    @Test
    public void CostNotMatchingTest() throws FullCardDeckException, AlreadyInAnotherLayerException, InvalidResourceException, CannotContainFaithException, NotEnoughSpaceException, NegativeResAmountException, LayerNotEmptyException, InvalidLayerNumberException, InvalidKeyException {
        Player player = new Player(false, "abc");
        Depot depot = player.getPersonalBoard().getDepot();
        Strongbox strongbox = player.getPersonalBoard().getStrongbox();

        Resource cost1 = new Resource(2, 0, 1, 1);
        DevelopmentCard card1 = new DevelopmentCard(3, cost1, CardColor.BLUE, 2, null);

        Resource fromDepot = new Resource(1, 0, 1, 0);
        Resource fromStrongbox = new Resource(1, 0, 0, 1);

       /* //Player hasn't got the needed resources
        assertThrows(NotEnoughResException.class, () -> player.buyDevCard(card1, AbilityChoice.STANDARD, fromDepot, fromStrongbox));

        depot.modifyLayer(2, ResourceType.STONE, 1);
        depot.modifyLayer(3, ResourceType.SHIELD, 3);
        strongbox.addResources(new Resource(1, 0, 0, 1));

        //Player has enough resources but it is not proving enough to buy the card
        Resource fromDepot2 = new Resource(0, 0, 1, 0);
        assertThrows(CostNotMatchingException.class, () -> player.buyDevCard(card1, AbilityChoice.STANDARD, fromDepot2, fromStrongbox));

        //Player has provided more resources than needed to buy the card
        Resource fromDepot3 = new Resource(1, 0, 3, 0);
        assertThrows(CostNotMatchingException.class, () -> player.buyDevCard(card1, AbilityChoice.STANDARD, fromDepot3, fromStrongbox));
   */}
}