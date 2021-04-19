package it.polimi.ingsw.controller;

import it.polimi.ingsw.exceptions.FullCardDeckException;
import it.polimi.ingsw.exceptions.InvalidKeyException;
import it.polimi.ingsw.exceptions.NegativeResAmountException;
import it.polimi.ingsw.exceptions.TooManyLeaderAbilitiesException;
import it.polimi.ingsw.model.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PlayerControllerTest {

    @Test
    public void basicProduction() throws NegativeResAmountException, InvalidKeyException {
        Controller controller = new Controller();
        PlayerController playerController = controller.getPlayerController();
        Player player = new Player(false, "John");
        Resource fromDepot = new Resource();
        Resource fromStrongbox = new Resource(1,1,0,0);

        playerController.basicProduction(player, ResourceType.COIN, ResourceType.STONE, ResourceType.SERVANT, fromDepot, fromStrongbox);
        assertEquals("The player hasn't got enough Resource to complete the action", controller.getError());

        player.getPersonalBoard().getStrongbox().addResources(new Resource(2,2,2,2));
        playerController.basicProduction(player, ResourceType.SHIELD, ResourceType.STONE, ResourceType.SERVANT, fromDepot, fromStrongbox);
        assertEquals("You did not provide enough resources to complete the action", controller.getError());

        playerController.basicProduction(player, ResourceType.COIN, ResourceType.STONE, ResourceType.SERVANT, fromDepot, fromStrongbox);
        assertEquals("Result: OK", controller.getError());
    }

    @Test
    public void extraProduction() throws TooManyLeaderAbilitiesException, NegativeResAmountException, InvalidKeyException {
        Controller controller = new Controller();
        PlayerController playerController = controller.getPlayerController();
        Player player = new Player(false, "John");
        playerController.extraProduction(player, AbilityChoice.FIRST, new Resource(), new Resource());
        assertEquals("The player has no leader ability of that type already active", controller.getError());

        ProductionPower productionPower = new ProductionPower(
                new Resource(1,0,1,0),
                new Resource(0,0,3,0));
        ExtraProduction extraProduction = new ExtraProduction(productionPower);
        extraProduction.activate(player);

        player.getPersonalBoard().getStrongbox().addResources(new Resource(3,3,3,3));
        playerController.extraProduction(player, AbilityChoice.SECOND, new Resource(), new Resource());
        assertEquals("You selected an invalid leader ability", controller.getError());

        playerController.extraProduction(player, AbilityChoice.FIRST,
                new Resource(), new Resource(1,0,1,0));
        assertEquals("Result: OK", controller.getError());
    }

    @Test
    public void takeResources() throws FullCardDeckException {
        Controller controller = new Controller();
        PlayerController playerController = controller.getPlayerController();
        Player player = new Player(false, "John");
        Game game = new Game(true);
        player.setGame(game);

        playerController.takeResources(player, 1, AbilityChoice.FIRST, 0, 0, null);
        assertEquals("The player has no leader ability of that type already active", controller.getError());

        //  PURPLE  PURPLE  YELLOW  YELLOW
        //  GREY  GREY  BLUE  BLUE
        //  WHITE  WHITE  WHITE  WHITE
        //  Remaining marble = RED

        //TODO: test with the new DepotSetting
        /*ChangeWhiteMarbles changeWhiteMarbles = new ChangeWhiteMarbles(ResourceType.SHIELD);
        changeWhiteMarbles.activate(player);
        playerController.takeResources(player, 1, AbilityChoice.STANDARD, 1, 0, null);
        assertEquals("Result: OK", controller.getError());*/
    }

    @Test
    public void buyDevCard() throws FullCardDeckException, TooManyLeaderAbilitiesException, NegativeResAmountException, InvalidKeyException {
        Controller controller = new Controller();
        PlayerController playerController = controller.getPlayerController();
        Player player = new Player(false, "John");
        Game game = new Game(true);
        player.setGame(game);

        //Card card = player.getGame().getMarket().getCard(2, CardColor.BLUE);
        //DevelopmentCard{cost={{STONE=0, SHIELD=0, COIN=4, SERVANT=0}}, type=BLUE, level=2, prodPower={input={{STONE=0, SHIELD=0, COIN=0, SERVANT=1}}, output={{FAITH=2}}}}

        playerController.buyDevCard(player,2, CardColor.BLUE, AbilityChoice.FIRST, null, null);
        assertEquals("The player has no leader ability of that type already active", controller.getError());

        Discount discount = new Discount(ResourceType.COIN, 2);
        discount.activate(player);
        player.getPersonalBoard().getStrongbox().addResources(new Resource(0,2,0,0));
        playerController.buyDevCard(player,2, CardColor.BLUE, AbilityChoice.FIRST,
                new Resource(0,0,0,0),
                new Resource(0,2,0,0));
        assertEquals("Result: OK", controller.getError());
    }
}