package it.polimi.ingsw;

import static org.junit.jupiter.api.Assertions.*;

import it.polimi.ingsw.model.MarblesColor;
import it.polimi.ingsw.model.ResourceType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MarblesColorTest {

    @Test
    public void getResourceType() {
        MarblesColor marble = MarblesColor.WHITE;
        MarblesColor mar = MarblesColor.RED;
        assertThrows(IllegalArgumentException.class, () -> marble.getResourceType());
        Assertions.assertEquals(mar.getResourceType(), ResourceType.FAITH);
        assertNotEquals(mar.getResourceType(), ResourceType.SERVANT);
    }
}