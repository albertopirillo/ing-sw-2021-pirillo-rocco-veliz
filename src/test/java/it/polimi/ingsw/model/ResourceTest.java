package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.InvalidKeyException;
import it.polimi.ingsw.exceptions.NegativeResAmountException;
import org.junit.jupiter.api.Test;

import javax.management.openmbean.KeyAlreadyExistsException;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


public class ResourceTest
{
    @Test
    public void emptyMapTest() {
        Resource res = new Resource();
        for (final ResourceType resType: ResourceType.values()) {
            assertThrows(InvalidKeyException.class, () -> res.getValue(resType));
        }
    }

    @Test
    public void fourElemMapTest() throws InvalidKeyException {
        int stone = 5;
        int coin = 2;
        int shield = 4;
        int servant = 1;
        Resource res = new Resource(stone, coin, shield, servant);
        assertEquals(res.getValue(ResourceType.STONE), stone);
        assertEquals(res.getValue(ResourceType.COIN), coin);
        assertEquals(res.getValue(ResourceType.SHIELD), shield);
        assertEquals(res.getValue(ResourceType.SERVANT), servant);
        assertThrows(InvalidKeyException.class, () -> res.getValue(ResourceType.FAITH));
    }

    @Test
    public void setTest() throws NegativeResAmountException, InvalidKeyException {
        Resource res = new Resource();
        res.addResource(ResourceType.COIN, 5);
        assertEquals(5, res.getValue(ResourceType.COIN));
        assertThrows(KeyAlreadyExistsException.class, () -> res.addResource(ResourceType.COIN, 2));
        assertThrows(NegativeResAmountException.class, () -> res.addResource(ResourceType.COIN, -3));
    }

    @Test
    public void modifyTest() throws InvalidKeyException, NegativeResAmountException {
        Resource res = new Resource(0, 3, 5, 6);
        int addend = 3;
        int oldValue = res.getValue(ResourceType.SHIELD);
        res.modifyValue(ResourceType.SHIELD, addend);
        assertEquals(oldValue + addend, res.getValue(ResourceType.SHIELD));
        res.modifyValue(ResourceType.SERVANT, -4);
        assertEquals(2, res.getValue(ResourceType.SERVANT));
        assertThrows(InvalidKeyException.class, () -> res.modifyValue(ResourceType.FAITH, 5));
        assertThrows(NegativeResAmountException.class, () -> res.modifyValue(ResourceType.COIN, -7));
    }

    @Test
    public void copyTest() throws NegativeResAmountException, InvalidKeyException {
        Resource res = new Resource(0, 3, 5, 6);
        Map<ResourceType, Integer> copy = res.getAllRes();
        assertEquals(0, copy.get(ResourceType.STONE));
        assertEquals(3, copy.get(ResourceType.COIN));
        assertEquals(5, copy.get(ResourceType.SHIELD));
        assertEquals(6, copy.get(ResourceType.SERVANT));

        res.modifyValue(ResourceType.SHIELD, 3);
        assertEquals(8, res.getValue(ResourceType.SHIELD));
        assertEquals(5, copy.get(ResourceType.SHIELD));
    }

    @Test
    public void compareTest() throws NegativeResAmountException, InvalidKeyException {
        Resource res1 = new Resource(1, 0, 2, 5);
        Resource res2 = new Resource(1, 2, 3, 1);
        Resource res3 = new Resource(3, 2, 4, 1);
        assertTrue(res1.compare(res1));
        assertFalse(res1.compare(res2));
        assertTrue(res3.compare(res2));

        res3.addResource(ResourceType.FAITH, 2);
        assertTrue(res3.compare(res2));

        res2.addResource(ResourceType.FAITH, 3);
        assertFalse(res2.compare(res3));

        Resource res4 = new Resource(1, 2, 3, 1);
        assertFalse(res4.compare(res2));

    }
}