package it.polimi.ingsw.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class MarblesColorTest {

    @Test
    public void getResourceType() {
        MarblesColor mar = MarblesColor.RED;
        Assertions.assertEquals(mar.getResourceType(), ResourceType.FAITH);
        assertNotEquals(mar.getResourceType(), ResourceType.SERVANT);
    }
}