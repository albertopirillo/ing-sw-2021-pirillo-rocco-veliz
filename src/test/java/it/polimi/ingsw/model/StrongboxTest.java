package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.*;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class StrongboxTest {

    @Test
    public void queryAllRes() {
        Strongbox strongbox = new Strongbox();
        Map<ResourceType, Integer> res = strongbox.queryAllRes();
        assertEquals(4, res.size());
        for(ResourceType key: res.keySet()) {
            assertEquals(0, res.get(key));
        }
    }

    @Test
    public void addResources() throws CannotContainFaithException, NegativeResAmountException, InvalidKeyException {
        Strongbox strongbox = new Strongbox();
        Resource res = new Resource(2, 3, 4, 5);
        strongbox.addResources(res);
        Map<ResourceType, Integer> content = strongbox.queryAllRes();
        for (ResourceType key: content.keySet()) {
            assertEquals(res.getValue(key), content.get(key));
        }
        Resource res2 = new Resource();
        res2.addResource(ResourceType.FAITH, 3);
        assertThrows(CannotContainFaithException.class, () -> strongbox.addResources(res2));
    }

    @Test
    public void retrieveResources() throws CannotContainFaithException, NegativeResAmountException, InvalidKeyException, NotEnoughResException {
        Strongbox strongbox = new Strongbox();
        strongbox.addResources(new Resource(2, 3, 4, 0));
        Resource resOK = new Resource(2, 1, 3, 0);
        Resource resKO = new Resource(2, 1, 5, 0);
        assertThrows(NotEnoughResException.class, () -> strongbox.retrieveResources(resKO));

        strongbox.retrieveResources(resOK);
        Map<ResourceType, Integer> content = strongbox.queryAllRes();
        Resource checkRes = new Resource(0, 2, 1, 0);
        for (ResourceType key: content.keySet()) {
            assertEquals(checkRes.getValue(key), content.get(key));
        }
    }

    @Test
    public void moveToDepot() throws CannotContainFaithException, NegativeResAmountException, InvalidKeyException, NotEnoughSpaceException, InvalidLayerNumberException, LayerNotEmptyException, NotEnoughResException, AlreadyInAnotherLayerException, InvalidResourceException {
        Strongbox strongbox = new Strongbox();
        strongbox.addResources(new Resource(2, 3, 4, 0));
        Strongbox strongbox2 = new Strongbox();
        strongbox2.addResources(new Resource(2, 3, 2, 0));
        Depot depot = new ConcreteDepot();
        depot.modifyLayer(3, ResourceType.SERVANT, 1);

        assertThrows(NotEnoughResException.class, () -> strongbox2.moveToDepot(ResourceType.SHIELD, depot, 2));
        assertThrows(LayerNotEmptyException.class, () -> strongbox.moveToDepot(ResourceType.SHIELD, depot, 3));
        strongbox.moveToDepot(ResourceType.SHIELD, depot, 2);
        assertEquals(2, depot.getLayer(2).getAmount());
        assertEquals(ResourceType.SHIELD, depot.getLayer(2).getResource());
        Map<ResourceType, Integer> content = strongbox.queryAllRes();
        assertEquals(1, content.get(ResourceType.SHIELD));
    }
}