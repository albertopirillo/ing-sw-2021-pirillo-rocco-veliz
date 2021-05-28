package it.polimi.ingsw.controller;

import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.network.DepotSetting;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ResourceControllerTest {

    @Test
    public void isEmptyTest() {
        ResourceController resourceController = new ResourceController(null, new TempResource());
        assertTrue(resourceController.getTempRes().isEmpty());

        resourceController.getTempRes().setToHandle(new Resource(0,0,0,0));
        assertFalse(resourceController.getTempRes().isEmpty());

        resourceController.getTempRes().setToHandle(new Resource(2,3,6,0));
        assertFalse(resourceController.getTempRes().isEmpty());
    }

    @Test
    public void handleResourceOKTest() throws FullCardDeckException, InvalidResourceException, WrongDepotInstructionsException, LayerNotEmptyException, NotEnoughSpaceException, InvalidLayerNumberException, CannotContainFaithException, AlreadyInAnotherLayerException, InvalidKeyException, NegativeResAmountException, CloneNotSupportedException {
        Game game = new MultiGame(true);
        MasterController masterController = new MasterController(game);
        Player activePlayer = masterController.getGame().getActivePlayer();
        ResourceController resourceController = masterController.getResourceController();
        activePlayer.setGame(game);

        Resource toPlace = new Resource(1,1,3,0);
        Resource toDiscard = new Resource(1,1,0,0);
        Resource toHandle = toPlace.sum(toDiscard);
        resourceController.getTempRes().setToHandle(toHandle);

        List<DepotSetting> settings = new ArrayList<>();
        settings.add(new DepotSetting(1, ResourceType.STONE, 1));
        settings.add(new DepotSetting(2, ResourceType.COIN, 1));
        settings.add(new DepotSetting(3, ResourceType.SHIELD, 3));

        resourceController.handleResource(toDiscard, settings, false);
        assertTrue(resourceController.getTempRes().isEmpty());
        assertEquals(toPlace, activePlayer.getPersonalBoard().getDepot().queryAllRes());
    }

    @Test
    public void handleResourceKOTest() throws FullCardDeckException {
        Game game = new MultiGame(true);
        MasterController masterController = new MasterController(game);
        Player activePlayer = masterController.getGame().getActivePlayer();
        ResourceController resourceController = masterController.getResourceController();
        activePlayer.setGame(game);

        //Resource toPlace = new Resource(1, 1, 3, 0);
        Resource toDiscard = new Resource(3,1,2,0);
        Resource toHandle = new Resource(0,1,2,0);
        resourceController.getTempRes().setToHandle(toHandle);

        List<DepotSetting> settings = new ArrayList<>();
        settings.add(new DepotSetting(1, ResourceType.STONE, 1));
        settings.add(new DepotSetting(3, ResourceType.SHIELD, 3));

        assertThrows(WrongDepotInstructionsException.class, () -> resourceController.handleResource(toDiscard, settings, false));

        Resource toHandle2 = new Resource(1,1,3,1);
        resourceController.getTempRes().setToHandle(toHandle2);

        assertThrows(WrongDepotInstructionsException.class,
                () -> resourceController.handleResource(new Resource(0,0,0,1), settings, false));
    }

    @Test
    public void fullDepotTest() throws FullCardDeckException {
        Game game = new MultiGame(true);
        MasterController masterController = new MasterController(game);
        Player activePlayer = masterController.getGame().getActivePlayer();
        ResourceController resourceController = masterController.getResourceController();
        activePlayer.setGame(game);

        Resource toDiscard = new Resource(0,1,2,0);
        Resource toHandle = new Resource(0,1,2,0);
        resourceController.getTempRes().setToHandle(toHandle);

        List<DepotSetting> settings = new ArrayList<>();
        settings.add(new DepotSetting(1, ResourceType.STONE, 1));
        settings.add(new DepotSetting(3, ResourceType.SHIELD, 2));

        assertThrows(WrongDepotInstructionsException.class, () -> resourceController.handleResource(toDiscard, settings, true));

        Resource toHandle2 = new Resource(1,1,3,1);
        resourceController.getTempRes().setToHandle(toHandle2);

        List<DepotSetting> settings2 = new ArrayList<>();
        settings2.add(new DepotSetting(1, ResourceType.STONE, 1));
        settings2.add(new DepotSetting(2, ResourceType.COIN, 1));

        assertDoesNotThrow(() -> resourceController.handleResource(new Resource(0,0,3,1), settings2, true));
    }
}