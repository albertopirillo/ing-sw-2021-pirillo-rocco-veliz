package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DevelopmentCardTest {

    @Test
    public void equals() {
        //stub
        Resource res1 = new Resource(1,2,3,4);
        Resource res2 = new Resource(1,2,3,1);
        DevelopmentCard dev1 = new DevelopmentCard(10, res1, CardColor.BLUE, 2, new ProductionPower(res1, res2));
        DevelopmentCard dev2 = new DevelopmentCard(10, res1, CardColor.BLUE, 2, new ProductionPower(res1, res2));
        DevelopmentCard dev3 = new DevelopmentCard(11, res2, CardColor.BLUE, 2, new ProductionPower(res2, res1));
        assertEquals(dev1, dev2);
        assertNotEquals(dev1, dev3);
    }
}