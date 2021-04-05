package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DevelopmentCardTest {

    @Test
    public void equals() {
        //stub
        Resource res1 = new Resource(1,2,3,4);
        Resource res3 = new Resource(1,2,3,1);
        DevelopmentCard dev1 = new DevelopmentCard(res1, CardColor.BLUE, 2);
        DevelopmentCard dev2 = new DevelopmentCard(res1, CardColor.BLUE, 2);
        DevelopmentCard dev3 = new DevelopmentCard(res3, CardColor.BLUE, 2);
        assertTrue(dev1.equals(dev2));
        assertFalse(dev1.equals(dev3));

    }
}