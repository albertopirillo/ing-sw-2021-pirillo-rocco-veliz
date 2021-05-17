package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.*;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StrongboxTest {

    @Test
    public void queryAllRes() {
        Strongbox strongbox = new Strongbox(null);
        Map<ResourceType, Integer> res = strongbox.queryAllRes().getMap();
        assertEquals(4, res.size());
        for(ResourceType key: res.keySet()) {
            assertEquals(0, res.get(key));
        }
    }

    @Test
    public void addResources() throws NegativeResAmountException, InvalidKeyException {
        Strongbox strongbox = new Strongbox(null);
        Resource res = new Resource(2, 3, 4, 5);
        strongbox.addResources(res);
        Map<ResourceType, Integer> content = strongbox.queryAllRes().getMap();
        for (ResourceType key: content.keySet()) {
            assertEquals(res.getValue(key), content.get(key));
        }
    }

    @Test
    public void faithTest() throws InvalidKeyException, NegativeResAmountException, FullCardDeckException {
        Player player = new Player( "abc");
        Game game = new MultiGame(true);
        player.setGame(game);
        Strongbox strongbox = new Strongbox(player);
        Resource res = new Resource(1,1,1,3);
        res.addResource(ResourceType.FAITH, 3);
        strongbox.addResources(res);

        assertEquals(new Resource(1,1,1,3), strongbox.queryAllRes());
        assertEquals(3, player.getPlayerFaith());
    }

    @Test
    public void retrieveResources() throws CannotContainFaithException, NegativeResAmountException, InvalidKeyException, NotEnoughResException {
        Strongbox strongbox = new Strongbox(null);
        strongbox.addResources(new Resource(2, 3, 4, 0));
        Resource resOK = new Resource(2, 1, 3, 0);
        Resource resKO = new Resource(2, 1, 5, 0);
        assertThrows(NotEnoughResException.class, () -> strongbox.retrieveRes(resKO));

        strongbox.retrieveRes(resOK);
        Map<ResourceType, Integer> content = strongbox.queryAllRes().getMap();
        Resource checkRes = new Resource(0, 2, 1, 0);
        for (ResourceType key: content.keySet()) {
            assertEquals(checkRes.getValue(key), content.get(key));
        }
    }
}