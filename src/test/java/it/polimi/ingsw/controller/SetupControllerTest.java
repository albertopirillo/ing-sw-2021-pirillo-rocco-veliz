package it.polimi.ingsw.controller;

import it.polimi.ingsw.exceptions.FullCardDeckException;
import it.polimi.ingsw.exceptions.InvalidLayerNumberException;
import it.polimi.ingsw.exceptions.NegativeResAmountException;
import it.polimi.ingsw.model.*;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class SetupControllerTest {

    @Test
    void setupGame() throws FullCardDeckException {
        List<String> nicknames = Arrays.asList("Aldair", "Alberto", "Riccardo");
        int numPlayer = nicknames.size();
        Game game = new MultiGame();
        SetupController controller = new SetupController(new MasterController(game));
        controller.setupGame(nicknames, numPlayer);
        assertEquals(game.getPlayerAmount(), numPlayer);
        assertEquals(game.getPlayersList().size(), numPlayer);
        List<Player> players = game.getPlayersList();
        for (Player p : players){
            assertTrue(nicknames.contains(p.getNickname()));
        }
    }

    @Test
    void placeInitialResource() throws FullCardDeckException, NegativeResAmountException, InvalidLayerNumberException {
        List<String> nicknames = Arrays.asList("Aldair", "Alberto", "Riccardo");
        int numPlayer = nicknames.size();
        Game game = new MultiGame();
        MasterController masterController = new MasterController(game);
        SetupController controller = masterController.getSetupController();
        controller.setupGame(nicknames, numPlayer);
        Map<ResourceType, Integer> map = new HashMap<>();
        map.put(ResourceType.STONE,1);
        Player p = game.getActivePlayer();
        controller.placeInitialResource(map,1, p.getNickname());
        assertEquals(p.getPlayerFaith(), 0);
        assertEquals(p.getAllResources(),new Resource(1,0,0,0));
        assertEquals(p.getPersonalBoard().getDepot().getLayer(1).getResource(), ResourceType.STONE);

        p = game.getActivePlayer();
        controller.placeInitialResource(map,2, p.getNickname());
        assertEquals(p.getPlayerFaith(), 1);
        assertEquals(p.getAllResources(),new Resource(1,0,0,0));
        assertEquals(p.getPersonalBoard().getDepot().getLayer(1).getResource(), ResourceType.STONE);

        p = game.getActivePlayer();
        map.put(ResourceType.STONE,2);
        controller.placeInitialResource(map,3, p.getNickname());
        assertEquals(p.getPlayerFaith(), 1);
        assertEquals(p.getAllResources(),new Resource(2,0,0,0));
        assertEquals(p.getPersonalBoard().getDepot().getLayer(2).getResource(), ResourceType.STONE);
    }

    @Test
    void placeInitialResourceSoloGame() throws FullCardDeckException, NegativeResAmountException {
        List<String> nicknames = Collections.singletonList("Aldair");
        int numPlayer = nicknames.size();
        Player p = new Player(nicknames.get(0));
        Game game = new SoloGame(p);
        MasterController masterController = new MasterController(game);
        masterController.getSetupController().placeInitialResource(new HashMap<>(),0,p.getNickname());
        assertEquals(p.getPlayerFaith(), 0);
        assertEquals(p.getAllResources(),new Resource(0,0,0,0));
    }

    @Test
    void setInitialLeaderCards() throws FullCardDeckException {
        List<String> nicknames = Arrays.asList("Aldair", "Alberto", "Riccardo");
        int numPlayer = nicknames.size();
        Game game = new MultiGame();
        MasterController masterController = new MasterController(game);
        SetupController controller = masterController.getSetupController();
        controller.setupGame(nicknames, numPlayer);
        Player p = game.getActivePlayer();
        assertEquals(p.getLeaderCards().size(),4);
        controller.setInitialLeaderCards(1,2,p.getNickname());
        assertEquals(p.getLeaderCards().size(),2);
        assertNotNull(p.getLeaderCards().get(0));
        assertNotNull(p.getLeaderCards().get(1));
    }
}