package it.polimi.ingsw.controller;

import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.network.DepotSetting;
import it.polimi.ingsw.network.requests.ChangeMarblesRequest;
import it.polimi.ingsw.network.requests.Request;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class PlayerControllerTest {

    @Test
    public void basicProductionTest() throws FullCardDeckException, NegativeResAmountException, InvalidKeyException {
        Game game = new MultiGame(true);
        MasterController controller = new MasterController(game);
        PlayerController playerController = controller.getPlayerController();
        playerController.setTesting(true);
        Player activePlayer = game.getActivePlayer();
        activePlayer.setGame(game);
        Resource fromDepot = new Resource();
        Resource fromStrongbox = new Resource(1,1,0,0);

        controller.resetException();
        playerController.basicProduction(ResourceType.COIN, ResourceType.STONE, ResourceType.SERVANT, fromDepot, fromStrongbox);
        assertEquals("The player hasn't got enough Resource to complete the action", controller.getError());

        controller.resetException();
        activePlayer.getPersonalBoard().getStrongbox().addResources(new Resource(2,2,2,2));
        playerController.basicProduction(ResourceType.SHIELD, ResourceType.STONE, ResourceType.SERVANT, fromDepot, fromStrongbox);
        assertEquals("You did not provide enough resources to complete the action", controller.getError());

        controller.resetException();
        playerController.basicProduction(ResourceType.COIN, ResourceType.STONE, ResourceType.SERVANT, fromDepot, fromStrongbox);
        assertEquals("Result: OK", controller.getError());
    }

    @Test
    public void extraProductionTest() throws TooManyLeaderAbilitiesException, FullCardDeckException, NegativeResAmountException, InvalidKeyException {
        Game game = new MultiGame(true);
        MasterController controller = new MasterController(game);
        PlayerController playerController = controller.getPlayerController();
        Player activePlayer = game.getActivePlayer();
        playerController.setTesting(true);
        activePlayer.setGame(game);
        playerController.extraProduction(AbilityChoice.FIRST, new Resource(), new Resource(), ResourceType.COIN);
        assertEquals("The player has no leader ability of that type already active", controller.getError());

        Resource res = new Resource(0,0,0,0);
        res.addResource(ResourceType.ALL,1);
        res.addResource(ResourceType.FAITH,1);
        ProductionPower productionPower = new ProductionPower(
                new Resource(1,0,1,0),
                res);
        ExtraProduction extraProduction = new ExtraProduction(productionPower);
        extraProduction.activate(activePlayer);

        activePlayer.getPersonalBoard().getStrongbox().addResources(new Resource(3,3,3,3));
        playerController.extraProduction(AbilityChoice.SECOND, new Resource(), new Resource(), ResourceType.COIN);
        assertEquals("You selected an invalid leader ability", controller.getError());
        controller.resetException();
        playerController.extraProduction(AbilityChoice.FIRST,
                new Resource(), new Resource(1,0,1,0), ResourceType.COIN);
        assertEquals("Result: OK", controller.getError());
        assertEquals(new Resource(2,4,2,3), activePlayer.getAllResources());
        assertEquals(1, activePlayer.getPlayerFaith());
    }

    @Test
    public void insertMarbleTest() throws FullCardDeckException, TooManyLeaderAbilitiesException{
        Game game = new MultiGame(true);
        MasterController controller = new MasterController(game);
        PlayerController playerController = controller.getPlayerController();
        playerController.setTesting(true);
        Player activePlayer = game.getActivePlayer();
        activePlayer.setGame(game);

        playerController.insertMarble(1);
        //assertEquals("The player has no leader ability of that type already active", controller.getError());

        //  PURPLE  PURPLE  YELLOW  YELLOW
        //  GREY  GREY  BLUE  BLUE
        //  WHITE  WHITE  WHITE  WHITE
        //  Remaining marble = RED

        ChangeWhiteMarbles changeWhiteMarbles = new ChangeWhiteMarbles(ResourceType.SHIELD);
        changeWhiteMarbles.activate(activePlayer);
        controller.resetException();
        playerController.insertMarble(1);
        assertEquals("Result: OK", controller.getError());

        //  PURPLE  GREY  YELLOW  YELLOW
        //  GREY  WHITE  BLUE  BLUE
        //  WHITE  RED  WHITE  WHITE
        //  Remaining marble = PURPLE
        ChangeWhiteMarbles changeWhiteMarbles2 = new ChangeWhiteMarbles(ResourceType.COIN);
        changeWhiteMarbles2.activate(activePlayer);

        playerController.insertMarble(0);
        Resource output = controller.getResourceController().getTempRes().getToHandle();
        System.out.println(output);
    }

    @Test
    public void changeMarbleTest() throws FullCardDeckException, TooManyLeaderAbilitiesException, NegativeResAmountException, InvalidKeyException {
        Game game = new MultiGame(true);
        MasterController controller = new MasterController(game);
        PlayerController playerController = controller.getPlayerController();
        Player activePlayer = game.getActivePlayer();
        playerController.setTesting(true);
        activePlayer.setGame(game);

        ChangeWhiteMarbles changeWhiteMarbles = new ChangeWhiteMarbles(ResourceType.SHIELD);
        changeWhiteMarbles.activate(activePlayer);
        ChangeWhiteMarbles changeWhiteMarbles2 = new ChangeWhiteMarbles(ResourceType.COIN);
        changeWhiteMarbles2.activate(activePlayer);

        playerController.insertMarble(4);

        //  PURPLE  PURPLE  YELLOW  YELLOW
        //  GREY  GREY  BLUE  BLUE
        //  WHITE  WHITE  WHITE  WHITE
        //  Remaining marble = RED

        //OUTPUT ALLx4
        Resource output = controller.getResourceController().getTempRes().getToHandle();
        Resource check = new Resource(0,0,0,0);
        check.addResource(ResourceType.ALL,4);
        //OUTPUT ALLx4
        assertEquals(check, output);
        //System.out.println(output);

        Request changeMarble = new ChangeMarblesRequest(3,1);
        changeMarble.activateRequest(controller);
        Resource check2 = new Resource(0,0,0,0);
        check2.modifyValue(ResourceType.SHIELD,3);
        check2.modifyValue(ResourceType.COIN,1);
        //System.out.println(output);
        assertEquals(check2, output);

        //  PURPLE  PURPLE  YELLOW  YELLOW
        //  GREY  GREY  BLUE  BLUE
        //  WHITE  WHITE  WHITE  RED
        //  Remaining marble = WHITE

        playerController.insertMarble(4);
        Resource output2 = controller.getResourceController().getTempRes().getToHandle();
        Resource check3 = new Resource(0,0,0,0);
        check3.addResource(ResourceType.ALL,3);
        assertEquals(check3, output2);
        assertEquals(1, activePlayer.getPlayerFaith());

        changeMarble = new ChangeMarblesRequest(1,1);
        changeMarble.activateRequest(controller);
        assertEquals(check3, output2);
        assertEquals("The number of white marbles does not match", controller.getError());

    }
    @Test
    public void endTurnOKTest() throws FullCardDeckException {
        Game game = new MultiGame(true);
        MasterController controller = new MasterController(game);
        PlayerController playerController = controller.getPlayerController();
        playerController.setTesting(true);
        Player activePlayer = game.getActivePlayer();
        activePlayer.setGame(game);

        playerController.endTurn();
        assertEquals("Result: OK", controller.getError());
    }

    @Test
    public void endTurnKOTest() throws FullCardDeckException {
        Game game = new MultiGame(true);
        MasterController controller = new MasterController(game);
        PlayerController playerController = controller.getPlayerController();
        playerController.setTesting(true);
        Player activePlayer = game.getActivePlayer();

        activePlayer.setGame(game);
        controller.getResourceController().getTempRes().setToHandle(new Resource(2,3,4,5));

        playerController.endTurn();
        assertEquals("There are still resources to be placed", controller.getError());
        controller.getResourceController().getTempRes().setToHandle(null);
    }

    @Test
    public void placeResourceTest() throws FullCardDeckException, InvalidResourceException, LayerNotEmptyException, NotEnoughSpaceException, InvalidLayerNumberException, CannotContainFaithException, AlreadyInAnotherLayerException, NegativeResAmountException, InvalidKeyException {
        Game game = new MultiGame(true);
        MasterController controller = new MasterController(game);
        PlayerController playerController = controller.getPlayerController();
        playerController.setTesting(true);
        Player activePlayer = game.getActivePlayer();
        activePlayer.setGame(game);

        ResourceController resourceController = controller.getResourceController();
        Resource toDiscard = new Resource(0,0,0,0);
        Resource toHandle = new Resource(1,0,3,0);
        resourceController.getTempRes().setToHandle(toHandle);

        Depot depot = activePlayer.getPersonalBoard().getDepot();
        depot.modifyLayer(3, ResourceType.SERVANT, 1);

        List<DepotSetting> settings = new ArrayList<>();
        settings.add(new DepotSetting(1, ResourceType.STONE, 1));
        settings.add(new DepotSetting(3, ResourceType.SHIELD, 3));

        //Error: instruction are correct but there is not enough space to insert them
        playerController.placeResource(toDiscard, settings);
        assertEquals("You provided incorrect instructions to place those resources", controller.getError());
        assertFalse(resourceController.getTempRes().isEmpty());
        assertEquals(new Resource(0,0,0,1), activePlayer.getPersonalBoard().getDepot().queryAllRes());
    }

    @Test
    public void buyDevCardTest() throws FullCardDeckException, TooManyLeaderAbilitiesException, NegativeResAmountException, InvalidKeyException {
        Game game = new MultiGame(true);
        MasterController controller = new MasterController(game);
        PlayerController playerController = controller.getPlayerController();
        playerController.setTesting(true);
        Player activePlayer = game.getActivePlayer();
        activePlayer.setGame(game);

        //Card card = player.getGame().getMarket().getCard(1, CardColor.BLUE);
        //DevelopmentCard{cost={{STONE=0, COIN=3, SHIELD=0, SERVANT=0}}, type=BLUE, level=1,
        //      prodPower={
        //              input={{STONE=2, COIN=0, SHIELD=0, SERVANT=0}},
        //              output={{STONE=0, SHIELD=1, COIN=1, SERVANT=1}}}}


        playerController.buyDevCard(1, CardColor.BLUE, 0, AbilityChoice.FIRST, null, null);
        assertEquals("The player has no leader ability of that type already active", controller.getError());

        Discount discount = new Discount(ResourceType.COIN, 2);
        discount.activate(activePlayer);
        activePlayer.getPersonalBoard().getStrongbox().addResources(new Resource(0,1,0,0));
        controller.resetException();
        playerController.buyDevCard(1, CardColor.BLUE, 0, AbilityChoice.FIRST,
                new Resource(0,0,0,0),
                new Resource(0,1,0,0));
        assertEquals("Result: OK", controller.getError());
    }

    @Test
    public void reorderDepotTest() throws FullCardDeckException, InvalidResourceException, LayerNotEmptyException, NotEnoughSpaceException, InvalidLayerNumberException, CannotContainFaithException, NegativeResAmountException, AlreadyInAnotherLayerException {
        Game game = new MultiGame(true);
        MasterController controller = new MasterController(game);
        PlayerController playerController = controller.getPlayerController();
        playerController.setTesting(true);
        Player activePlayer = game.getActivePlayer();
        activePlayer.setGame(game);

        Depot depot = activePlayer.getPersonalBoard().getDepot();
        depot.modifyLayer(3, ResourceType.COIN, 2);
        playerController.reorderDepot(3, 2, 3);
        assertEquals("The player hasn't got enough Resource to complete the action", controller.getError());
        controller.resetException();
        playerController.reorderDepot(3, 2, 1);
        assertEquals("Result: OK", controller.getError());
    }

    @Test
    public void reorderDepotGUITest() throws FullCardDeckException, InvalidLayerNumberException, InvalidResourceException, LayerNotEmptyException, NotEnoughSpaceException, CannotContainFaithException, AlreadyInAnotherLayerException, NegativeResAmountException {
        Game game = new MultiGame(true);
        MasterController controller = new MasterController(game);
        PlayerController playerController = controller.getPlayerController();
        playerController.setTesting(true);
        Player activePlayer = game.getActivePlayer();
        activePlayer.setGame(game);

        Depot depot = activePlayer.getPersonalBoard().getDepot();
        ExtraSlot extraSlot = new ExtraSlot(ResourceType.SERVANT);
        depot  = new ConcreteDepotDecorator(depot, extraSlot);
        activePlayer.getPersonalBoard().upgradeDepot(depot);
        depot.modifyLayer(2, ResourceType.COIN, 1);
        depot.modifyLayer(3, ResourceType.SHIELD, 2);
        depot.modifyLayer(4, ResourceType.SERVANT, 1);

        List<DepotSetting> settings = new ArrayList<>();
        settings.add(new DepotSetting(1, ResourceType.COIN, 1));
        settings.add(new DepotSetting(2, ResourceType.SHIELD, 2));
        settings.add(new DepotSetting(4, ResourceType.SERVANT, 1));
        playerController.reorderDepot(settings);
        assertEquals("Result: OK", controller.getError());
        controller.resetException();
    }

    @Test
    public void useLeaderTest() throws FullCardDeckException {
        Game game = new MultiGame(true);
        MasterController controller = new MasterController(game);
        PlayerController playerController = controller.getPlayerController();
        playerController.setTesting(true);
        Player activePlayer = game.getActivePlayer();
        activePlayer.setGame(game);

        LeaderCard leader = new ResLeaderCard(2, null, new Resource(0,2,3,1));
        activePlayer.addLeaderCard(leader);
        //controller.resetException();
        playerController.useLeader(0, LeaderAction.USE_ABILITY);
        assertEquals("LeaderCard requirements not satisfied", controller.getError());
        controller.resetException();
        playerController.useLeader(0, LeaderAction.DISCARD);
        assertEquals("Result: OK", controller.getError());

    }

    @Test
    public void activateProductionTest() throws FullCardDeckException, NegativeResAmountException, InvalidKeyException{
        Game game = new MultiGame(true);
        MasterController controller = new MasterController(game);
        PlayerController playerController = controller.getPlayerController();
        playerController.setTesting(true);
        playerController.setTesting(true);
        Player activePlayer = game.getActivePlayer();
        activePlayer.setGame(game);
        //player.getPersonalBoard().getStrongbox().addResources(new Resource(0,2,0,0));
        List<Integer> numSlots = new ArrayList<>();
        numSlots.add(1);

        controller.resetException();
        playerController.activateProduction(null, null, numSlots);
        assertEquals("The slot is empty or the slot number is invalid", controller.getError());
        //Card card = player.getGame().getMarket().getCard(1, CardColor.BLUE);
        //DevelopmentCard{cost={{STONE=0, COIN=3, SHIELD=0, SERVANT=0}}, type=BLUE, level=1,
        //      prodPower={
        //              input={{STONE=2, COIN=0, SHIELD=0, SERVANT=0}},
        //              output={{STONE=0, SHIELD=1, COIN=1, SERVANT=1}}}}
        activePlayer.getPersonalBoard().getStrongbox().addResources(new Resource(2,3,0,0));
        assertEquals(activePlayer.getPersonalBoard().getStrongbox().queryAllRes(), new Resource(2, 3, 0, 0));

        controller.resetException();
        playerController.buyDevCard(1, CardColor.BLUE, 1, AbilityChoice.STANDARD,
                new Resource(0,0,0,0),
                new Resource(0,3,0,0));
        assertEquals("Result: OK", controller.getError());
        //This devCard is in slot number 1
        controller.resetException();
        playerController.activateProduction(
                new Resource(0,1,0,0),
                new Resource(0,3,0,0),
                numSlots);
        //cost not matching
        assertEquals("The player hasn't got enough Resource to complete the action", controller.getError());
        controller.resetException();
        playerController.activateProduction(
                new Resource(0,0,0,0),
                new Resource(2,0,0,0),
                numSlots);
        assertEquals("Result: OK", controller.getError());
        assertEquals(activePlayer.getAllResources(), new Resource(0, 1, 1, 1));
        assertEquals(activePlayer.getPersonalBoard().getStrongbox().queryAllRes(), new Resource(0, 1, 1, 1));

        //Test activate two devCards
        //Card card = player.getGame().getMarket().getCard(2, CardColor.GREEN);
        //DevelopmentCard{cost={{STONE=0, COIN=0, SHIELD=4, SERVANT=0}}, type=GREEN, level=2,
        //  prodPower={
        //      input={{STONE=1, COIN=0, SHIELD=0, SERVANT=0}},
        //      output={{STONE=0, COIN=0, SHIELD=0, SERVANT=0, FAITH=2}}}}
        activePlayer.getPersonalBoard().getStrongbox().addResources(new Resource(1,0,4,0));

        //test invalid number of slot
        controller.resetException();
        playerController.buyDevCard(2, CardColor.GREEN, 2, AbilityChoice.STANDARD,
                new Resource(0,0,0,0),
                new Resource(0,0,4,0));
        assertEquals("The slot is empty or the slot number is invalid", controller.getError());

        controller.resetException();
        playerController.buyDevCard(2, CardColor.GREEN, 1, AbilityChoice.STANDARD,
                new Resource(0,0,0,0),
                new Resource(0,0,4,0));
        assertEquals("Result: OK", controller.getError());

        //Test strongbox before production
        assertEquals(activePlayer.getAllResources(), new Resource(1, 1, 1, 1));
        assertEquals(activePlayer.getPersonalBoard().getStrongbox().queryAllRes(), new Resource(1, 1, 1, 1));
        assertEquals(0, activePlayer.getPlayerFaith());

        controller.resetException();
        playerController.activateProduction(
                new Resource(0,0,0,0),
                new Resource(1,0,0,0),
                numSlots);
        assertEquals("Result: OK", controller.getError());

        //Test strongbox after production
        assertEquals(activePlayer.getAllResources(), new Resource(0, 1, 1, 1));
        assertEquals(activePlayer.getPersonalBoard().getStrongbox().queryAllRes(), new Resource(0, 1, 1, 1));
        assertEquals(2, activePlayer.getPlayerFaith());

        //Testing num activate slot empty
        controller.resetException();
        playerController.activateProduction(
                new Resource(0,0,0,0),
                new Resource(1,0,0,0),
                new ArrayList<>(Collections.singletonList(2)));
        assertEquals("The slot is empty or the slot number is invalid", controller.getError());

        //Card card = player.getGame().getMarket().getCard(1, CardColor.YELLOW);
        //DevelopmentCard{cost={{STONE=3, COIN=0, SHIELD=0, SERVANT=0}}, type=YELLOW, level=1,
        //     prodPower={
        //          input={{STONE=0, COIN=0, SHIELD=2, SERVANT=0}},
        //          output={{STONE=1, COIN=1, SHIELD=0, SERVANT=1}}}}
        activePlayer.getPersonalBoard().getStrongbox().addResources(new Resource(3,0,2,0));
        controller.resetException();
        playerController.buyDevCard(1, CardColor.YELLOW, 0, AbilityChoice.STANDARD,
                new Resource(0,0,0,0),
                new Resource(3,0,0,0));
        assertEquals("Result: OK", controller.getError());
        controller.resetException();
        playerController.activateProduction(
                new Resource(0,0,0,0),
                new Resource(0,0,2,0),
                new ArrayList<>(Arrays.asList(0,1)));
        assertEquals("You did not provide enough resources to complete the action", controller.getError());
        controller.resetException();
        playerController.activateProduction(
                new Resource(0,0,0,0),
                new Resource(0,0,2,0),
                new ArrayList<>(Collections.singletonList(0)));
        assertEquals("Result: OK", controller.getError());

        //Test strongbox after production
        assertEquals(activePlayer.getAllResources(), new Resource(1, 2, 1, 2));
        assertEquals(activePlayer.getPersonalBoard().getStrongbox().queryAllRes(), new Resource(1, 2, 1, 2));
        assertEquals(2, activePlayer.getPlayerFaith());

        //Card card = player.getGame().getMarket().getCard(1, CardColor.PURPLE);
        //DevelopmentCard{cost={{STONE=0, COIN=0, SHIELD=0, SERVANT=3}}, type=PURPLE, level=1,
        //      prodPower={
        //              input={{STONE=0, COIN=1, SHIELD=0, SERVANT=0}},
        //              output={{STONE=1, COIN=0, SHIELD=1, SERVANT=1}}}}
        activePlayer.getPersonalBoard().getStrongbox().addResources(new Resource(0,0,2,3));
        controller.resetException();
        playerController.buyDevCard(1, CardColor.PURPLE, 2, AbilityChoice.STANDARD,
                new Resource(0,0,0,0),
                new Resource(0,0,0,3));
        assertEquals("Result: OK", controller.getError());

        //TEST ACTIVATE ALL SLOTS
        //strongbox before activateProduction
        assertEquals(activePlayer.getAllResources(), new Resource(1, 2, 3, 2));
        assertEquals(activePlayer.getPersonalBoard().getStrongbox().queryAllRes(), new Resource(1, 2, 3, 2));
        assertEquals(2, activePlayer.getPlayerFaith());

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
        controller.resetException();
        playerController.activateProduction(
                new Resource(0,0,0,0),
                new Resource(10,0,10,0),
                all);
        assertEquals("The player hasn't got enough Resource to complete the action", controller.getError());

        //Test not enough Resource passed to controller
        controller.resetException();
        playerController.activateProduction(
                new Resource(0,0,0,0),
                new Resource(0,1,2,0),
                all);
        assertEquals("You did not provide enough resources to complete the action", controller.getError());

        //Test correct, passed the correct Resource to controller
        controller.resetException();
        playerController.activateProduction(
                new Resource(0,0,0,0),
                new Resource(1,1,2,0),
                all);
        assertEquals("Result: OK", controller.getError());

        //Strongbox after production
        assertEquals(activePlayer.getAllResources(), new Resource(2, 2, 2, 4));
        assertEquals(activePlayer.getPersonalBoard().getStrongbox().queryAllRes(), new Resource(2, 2, 2, 4));
        assertEquals(4, activePlayer.getPlayerFaith());
    }
}