package it.polimi.ingsw.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MarblesColorTest {

    @Test
    public void getResourceType() {
        MarblesColor marble = MarblesColor.WHITE;
        MarblesColor mar = MarblesColor.RED;
        assertThrows(IllegalArgumentException.class, marble::getResourceType);
        Assertions.assertEquals(mar.getResourceType(), ResourceType.FAITH);
        assertNotEquals(mar.getResourceType(), ResourceType.SERVANT);
    }
}