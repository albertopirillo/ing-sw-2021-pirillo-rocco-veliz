package it.polimi.ingsw;

import it.polimi.ingsw.exceptions.IllegalKeyException;
import it.polimi.ingsw.exceptions.NegativeResAmountException;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.ResourceType;
import org.junit.Test;

import static org.junit.Assert.*;

public class ResourceTest
{
    @Test
    public void emptyMapTest() {
        Resource res = new Resource();
        for (final ResourceType resType: ResourceType.values()) {
            assertThrows(IllegalKeyException.class, () -> res.getValue(resType));
        }
    }

    @Test
    public void fourElemMapTest() throws IllegalKeyException {
        int stone = 5;
        int coin = 2;
        int shield = 4;
        int servant = 1;
        Resource res = new Resource(stone, coin, shield, servant);
        assertEquals(res.getValue(ResourceType.STONE), stone);
        assertEquals(res.getValue(ResourceType.COIN), coin);
        assertEquals(res.getValue(ResourceType.SHIELD), shield);
        assertEquals(res.getValue(ResourceType.SERVANT), servant);
        assertThrows(IllegalKeyException.class, () -> res.getValue(ResourceType.FAITH));
    }

    @Test
    public void setTest() throws NegativeResAmountException, IllegalKeyException {
        Resource res = new Resource();
        res.setValue(ResourceType.COIN, 5);
        assertEquals(5, res.getValue(ResourceType.COIN));
        assertThrows(NegativeResAmountException.class, () -> res.setValue(ResourceType.STONE, -3));
    }

    @Test
    public void increaseTest() throws IllegalKeyException {
        Resource res = new Resource(0, 3, 5, 6);
        int addend = 3;
        int oldValue = res.getValue(ResourceType.SHIELD);
        res.increaseValue(ResourceType.SHIELD, addend);
        assertEquals(oldValue + addend, res.getValue(ResourceType.SHIELD));
        assertThrows(IllegalKeyException.class, () -> res.increaseValue(ResourceType.FAITH, 5));
    }

    @Test
    public void decreaseTest() throws NegativeResAmountException, IllegalKeyException {
        Resource res = new Resource(0, 3, 5, 6);
        int sub = 3;
        int oldValue = res.getValue(ResourceType.SHIELD);
        res.decreaseValue(ResourceType.SHIELD, sub);
        assertEquals(oldValue - sub, res.getValue(ResourceType.SHIELD));
        assertThrows(IllegalKeyException.class, () -> res.decreaseValue(ResourceType.FAITH, 5));
        assertThrows(NegativeResAmountException.class, () -> res.decreaseValue(ResourceType.STONE, 5));
    }
}