package it.polimi.ingsw;

import it.polimi.ingsw.exceptions.IllegalKeyException;
import it.polimi.ingsw.exceptions.NegativeResourceAmountException;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.ResourceType;
import org.junit.Test;

import static org.junit.Assert.*;

public class ResourceTest
{
    Resource res = new Resource();
    ResourceType resType = ResourceType.STONE;

    @Test
    public void getTest() throws IllegalKeyException {
        for (ResourceType current : ResourceType.values()) {
            int value = res.getValue(current);
            assertEquals(0, value);
        }
    }

    @Test
    public void setTest() throws IllegalKeyException, NegativeResourceAmountException {
        int newValue = 5;

        res.setValue(resType, newValue);
        assertEquals(newValue, res.getValue(resType));
    }

    @Test
    public void setNegativeTest() throws IllegalKeyException {
        boolean exception = false;

        try {
            res.setValue(resType, -1);
        } catch (NegativeResourceAmountException e) {
           exception = true;
        }
        assertTrue(exception);
    }

    @Test
    public void increaseTest() throws NegativeResourceAmountException, IllegalKeyException {
        int addend = 3;

        int oldValue = res.getValue(resType);
        res.increaseValue(resType, addend);
        assertEquals(oldValue + addend, res.getValue(resType));
    }

}