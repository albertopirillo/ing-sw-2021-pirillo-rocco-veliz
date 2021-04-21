package it.polimi.ingsw.controller;

import it.polimi.ingsw.exceptions.FullCardDeckException;
import it.polimi.ingsw.exceptions.InvalidKeyException;
import it.polimi.ingsw.exceptions.NegativeResAmountException;
import it.polimi.ingsw.exceptions.TooManyLeaderAbilitiesException;
import it.polimi.ingsw.model.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PlayerMasterControllerTest {

    @Test
    public void basicProduction() throws NegativeResAmountException, InvalidKeyException {
        MasterController controller = new MasterController(null);
        PlayerController playerController = controller.getPlayerController();
        Player player = new Player( "John");
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
        MasterController controller = new MasterController(null);
        PlayerController playerController = controller.getPlayerController();
        Player player = new Player( "John");
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
    public void takeResources() throws FullCardDeckException, TooManyLeaderAbilitiesException {
        Game game = new Game(true);
        MasterController controller = new MasterController(game);
        PlayerController playerController = controller.getPlayerController();
        Player activePlayer = game.getActivePlayer();
        activePlayer.setGame(game);

        playerController.insertMarble(1, AbilityChoice.FIRST, 0, 0);
        assertEquals("The player has no leader ability of that type already active", controller.getError());

        //  PURPLE  PURPLE  YELLOW  YELLOW
        //  GREY  GREY  BLUE  BLUE
        //  WHITE  WHITE  WHITE  WHITE
        //  Remaining marble = RED

        ChangeWhiteMarbles changeWhiteMarbles = new ChangeWhiteMarbles(ResourceType.SHIELD);
        changeWhiteMarbles.activate(activePlayer);
        playerController.insertMarble(1, AbilityChoice.STANDARD, 1, 0);
        assertEquals("Result: OK", controller.getError());
    }

    @Test
    public void buyDevCard() throws FullCardDeckException, TooManyLeaderAbilitiesException, NegativeResAmountException, InvalidKeyException {
        MasterController controller = new MasterController(null);
        PlayerController playerController = controller.getPlayerController();
        Player player = new Player( "John");
        Game game = new Game(true);
        player.setGame(game);

        //Card card = player.getGame().getMarket().getCard(1, CardColor.BLUE);
        //DevelopmentCard{cost={{STONE=0, COIN=3, SHIELD=0, SERVANT=0}}, type=BLUE, level=1,
        //      prodPower={
        //              input={{STONE=2, COIN=0, SHIELD=0, SERVANT=0}},
        //              output={{STONE=0, SHIELD=1, COIN=1, SERVANT=1}}}}


        playerController.buyDevCard(player,1, CardColor.BLUE, 0, AbilityChoice.FIRST, null, null);
        assertEquals("The player has no leader ability of that type already active", controller.getError());

        Discount discount = new Discount(ResourceType.COIN, 2);
        discount.activate(player);
        player.getPersonalBoard().getStrongbox().addResources(new Resource(0,1,0,0));
        playerController.buyDevCard(player,1, CardColor.BLUE, 0, AbilityChoice.FIRST,
                new Resource(0,0,0,0),
                new Resource(0,1,0,0));
        assertEquals("Result: OK", controller.getError());
    }

    @Test
    public void activateProductionTest() throws FullCardDeckException, NegativeResAmountException, InvalidKeyException{
        MasterController controller = new MasterController(null);
        PlayerController playerController = controller.getPlayerController();
        Player player = new Player( "John");
        Game game = new Game(true);
        player.setGame(game);
        //player.getPersonalBoard().getStrongbox().addResources(new Resource(0,2,0,0));
        List<Integer> numSlots = new ArrayList<>();
        numSlots.add(1);
        playerController.activateProduction(player, null, null, numSlots);
        assertEquals("The slot is empty or the slot number is invalid", controller.getError());
        //Card card = player.getGame().getMarket().getCard(1, CardColor.BLUE);
        //DevelopmentCard{cost={{STONE=0, COIN=3, SHIELD=0, SERVANT=0}}, type=BLUE, level=1,
        //      prodPower={
        //              input={{STONE=2, COIN=0, SHIELD=0, SERVANT=0}},
        //              output={{STONE=0, SHIELD=1, COIN=1, SERVANT=1}}}}
        player.getPersonalBoard().getStrongbox().addResources(new Resource(2,3,0,0));
        assertTrue(player.getPersonalBoard().getStrongbox().queryAllRes().equals(new Resource(2,3,0,0)));

        playerController.buyDevCard(player,1, CardColor.BLUE, 1, AbilityChoice.STANDARD,
                new Resource(0,0,0,0),
                new Resource(0,3,0,0));
        assertEquals("Result: OK", controller.getError());
        //This devCard is in slot number 1
        playerController.activateProduction(player,
                new Resource(0,1,0,0),
                new Resource(0,3,0,0),
                numSlots);
        //cost not matching
        assertEquals("The player hasn't got enough Resource to complete the action", controller.getError());
        playerController.activateProduction(player,
                new Resource(0,0,0,0),
                new Resource(2,0,0,0),
                numSlots);
        assertEquals("Result: OK", controller.getError());
        assertTrue(player.getAllResources().equals(new Resource(0,1,1,1)));
        assertTrue(player.getPersonalBoard().getStrongbox().queryAllRes().equals(new Resource(0,1,1,1)));

        //Test activate two devCards
        //Card card = player.getGame().getMarket().getCard(2, CardColor.GREEN);
        //DevelopmentCard{cost={{STONE=0, COIN=0, SHIELD=4, SERVANT=0}}, type=GREEN, level=2,
        //  prodPower={
        //      input={{STONE=1, COIN=0, SHIELD=0, SERVANT=0}},
        //      output={{STONE=0, COIN=0, SHIELD=0, SERVANT=0, FAITH=2}}}}
        player.getPersonalBoard().getStrongbox().addResources(new Resource(1,0,4,0));

        //test invalid number of slot
        playerController.buyDevCard(player,2, CardColor.GREEN, 2, AbilityChoice.STANDARD,
                new Resource(0,0,0,0),
                new Resource(0,0,4,0));
        assertEquals("The slot is empty or the slot number is invalid", controller.getError());

        playerController.buyDevCard(player,2, CardColor.GREEN, 1, AbilityChoice.STANDARD,
                new Resource(0,0,0,0),
                new Resource(0,0,4,0));
        assertEquals("Result: OK", controller.getError());

        //Test strongbox before production
        assertTrue(player.getAllResources().equals(new Resource(1,1,1,1)));
        assertTrue(player.getPersonalBoard().getStrongbox().queryAllRes().equals(new Resource(1,1,1,1)));
        assertEquals(0, player.getPlayerFaith());

        playerController.activateProduction(player,
                new Resource(0,0,0,0),
                new Resource(1,0,0,0),
                numSlots);
        assertEquals("Result: OK", controller.getError());

        //Test strongbox after production
        assertTrue(player.getAllResources().equals(new Resource(0,1,1,1)));
        assertTrue(player.getPersonalBoard().getStrongbox().queryAllRes().equals(new Resource(0,1,1,1)));
        assertEquals(2, player.getPlayerFaith());

        //Testing num activate slot empty
        playerController.activateProduction(player,
                new Resource(0,0,0,0),
                new Resource(1,0,0,0),
                new ArrayList<>(Arrays.asList(2)));
        assertEquals("The slot is empty or the slot number is invalid", controller.getError());

        //Card card = player.getGame().getMarket().getCard(1, CardColor.YELLOW);
        //DevelopmentCard{cost={{STONE=3, COIN=0, SHIELD=0, SERVANT=0}}, type=YELLOW, level=1,
        //     prodPower={
        //          input={{STONE=0, COIN=0, SHIELD=2, SERVANT=0}},
        //          output={{STONE=1, COIN=1, SHIELD=0, SERVANT=1}}}}
        player.getPersonalBoard().getStrongbox().addResources(new Resource(3,0,2,0));
        playerController.buyDevCard(player,1, CardColor.YELLOW, 0, AbilityChoice.STANDARD,
                new Resource(0,0,0,0),
                new Resource(3,0,0,0));
        assertEquals("Result: OK", controller.getError());
        playerController.activateProduction(player,
                new Resource(0,0,0,0),
                new Resource(0,0,2,0),
                new ArrayList<>(Arrays.asList(0,1)));
        assertEquals("You did not provide enough resources to complete the action", controller.getError());
        playerController.activateProduction(player,
                new Resource(0,0,0,0),
                new Resource(0,0,2,0),
                new ArrayList<>(Arrays.asList(0)));
        assertEquals("Result: OK", controller.getError());

        //Test strongbox after production
        assertTrue(player.getAllResources().equals(new Resource(1,2,1,2)));
        assertTrue(player.getPersonalBoard().getStrongbox().queryAllRes().equals(new Resource(1,2,1,2)));
        assertEquals(2, player.getPlayerFaith());

        //Card card = player.getGame().getMarket().getCard(1, CardColor.PURPLE);
        //DevelopmentCard{cost={{STONE=0, COIN=0, SHIELD=0, SERVANT=3}}, type=PURPLE, level=1,
        //      prodPower={
        //              input={{STONE=0, COIN=1, SHIELD=0, SERVANT=0}},
        //              output={{STONE=1, COIN=0, SHIELD=1, SERVANT=1}}}}
        player.getPersonalBoard().getStrongbox().addResources(new Resource(0,0,2,3));
        playerController.buyDevCard(player,1, CardColor.PURPLE, 2, AbilityChoice.STANDARD,
                new Resource(0,0,0,0),
                new Resource(0,0,0,3));
        assertEquals("Result: OK", controller.getError());

        //TEST ACTIVATE ALL SLOTS
        //strongbox before activateProduction
        assertTrue(player.getAllResources().equals(new Resource(1,2,3,2)));
        assertTrue(player.getPersonalBoard().getStrongbox().queryAllRes().equals(new Resource(1,2,3,2)));
        assertEquals(2, player.getPlayerFaith());

        ArrayList <Integer> all = new ArrayList<>();
        all.add(0);
        all.add(1);
        all.add(2);
        //strongbox : {STONE=1, COIN=2, SHIELD=3, SERVANT=2}
        //input slot 0: input={{STONE=0, COIN=0, SHIELD=2, SERVANT=0}},
        //              output={{STONE=1, COIN=1, SHIELD=0, SERVANT=1}}}}
        //input slot 1: input={{STONE=1, COIN=0, SHIELD=0, SERVANT=0}},
        //              output={{STONE=0, COIN=0, SHIELD=0, SERVANT=0, FAITH=2}}}}
        //slot 2: input={{STONE=0, COIN=1, SHIELD=0, SERVANT=0}},
        //        output={{STONE=1, COIN=0, SHIELD=1, SERVANT=1}}}}

        //Test not enough Resource in the strongbox
        playerController.activateProduction(player,
                new Resource(0,0,0,0),
                new Resource(10,0,10,0),
                all);
        assertEquals("The player hasn't got enough Resource to complete the action", controller.getError());

        //Test not enough Resource passed to controller
        playerController.activateProduction(player,
                new Resource(0,0,0,0),
                new Resource(0,1,2,0),
                all);
        assertEquals("You did not provide enough resources to complete the action", controller.getError());

        //Test correct, passed the correct Resource to controller
        playerController.activateProduction(player,
                new Resource(0,0,0,0),
                new Resource(1,1,2,0),
                all);
        assertEquals("Result: OK", controller.getError());

        //Strongbox after production
        assertTrue(player.getAllResources().equals(new Resource(2,2,2,4)));
        assertTrue(player.getPersonalBoard().getStrongbox().queryAllRes().equals(new Resource(2,2,2,4)));
        assertEquals(4, player.getPlayerFaith());
    }
}