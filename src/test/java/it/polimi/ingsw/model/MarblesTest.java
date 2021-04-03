package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.InvalidKeyException;
import it.polimi.ingsw.exceptions.NegativeResAmountException;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class MarblesTest {

    @Test
    public void equals() throws InvalidKeyException {
        Marbles mar = new Marbles();
        Marbles mar1 = new Marbles();
        mar.add(MarblesColor.BLUE);
        mar1.add(MarblesColor.BLUE);
        assertTrue(mar1.equals(mar));
        mar.add(MarblesColor.WHITE);
        assertFalse(mar1.equals(mar));
    }

    @Test
    public void getValue() throws InvalidKeyException {
        Marbles marbles = new Marbles();
        marbles.add(MarblesColor.WHITE);
        assertThrows(InvalidKeyException.class, () -> marbles.getValue(MarblesColor.RED));
        assertEquals(marbles.getValue(MarblesColor.WHITE), 1);
    }

    @Test
    public void add() throws InvalidKeyException {
        Marbles marbles = new Marbles();
        for (int i=0; i<3; i++) {
            marbles.add(MarblesColor.WHITE);
            assertEquals(marbles.getValue(MarblesColor.WHITE), i + 1);
        }
        assertThrows(InvalidKeyException.class, () -> marbles.getValue(MarblesColor.RED));
        assertThrows(InvalidKeyException.class, () -> marbles.getValue(MarblesColor.YELLOW));
        assertThrows(InvalidKeyException.class, () -> marbles.getValue(MarblesColor.PURPLE));
        assertThrows(InvalidKeyException.class, () -> marbles.getValue(MarblesColor.BLUE));
        assertThrows(InvalidKeyException.class, () -> marbles.getValue(MarblesColor.GREY));

    }

    @Test
    public void getResources() throws NegativeResAmountException, InvalidKeyException {
        Marbles marbles = new Marbles();
        marbles.add(MarblesColor.WHITE);
        marbles.add(MarblesColor.BLUE);
        marbles.add(MarblesColor.RED);
        marbles.add(MarblesColor.BLUE);
        Resource res = marbles.getResources();
        assertEquals(res.getValue(MarblesColor.BLUE.getResourceType()), 2);
        assertEquals(res.getValue(MarblesColor.RED.getResourceType()), 1);
        assertThrows(InvalidKeyException.class, () -> res.getValue(MarblesColor.GREY.getResourceType()));
        assertThrows(InvalidKeyException.class, () -> res.getValue(MarblesColor.YELLOW.getResourceType()));
        assertThrows(InvalidKeyException.class, () -> res.getValue(MarblesColor.PURPLE.getResourceType()));
    }
}