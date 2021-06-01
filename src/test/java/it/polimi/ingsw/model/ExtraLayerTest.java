package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExtraLayerTest {
    final ExtraSlotAbility extraSlot1 = new ExtraSlotAbility(ResourceType.SERVANT);
    final ExtraSlotAbility extraSlot2 = new ExtraSlotAbility(ResourceType.COIN);
    final ExtraSlotAbility extraSlot3 = new ExtraSlotAbility(ResourceType.SHIELD);
    final ExtraSlotAbility extraSlot4 = new ExtraSlotAbility(ResourceType.STONE);


    @Test
    public void buildTest() {
        Layer extraLayer = new ExtraLayer(extraSlot1);
        assertEquals(0, extraLayer.getAmount());
        assertEquals(ResourceType.SERVANT, extraLayer.getResource());
    }

    @Test
    void isEmpty() throws NotEnoughSpaceException, NegativeResAmountException {
        Layer extraLayer = new ExtraLayer(extraSlot2);
        assertTrue(extraLayer.isEmpty());
        extraLayer.setAmount(1);
        assertFalse(extraLayer.isEmpty());
    }

    @Test
    void setAmount() {
        Layer extraLayer = new ExtraLayer(extraSlot3);
        assertThrows(NotEnoughSpaceException.class, () -> extraLayer.setAmount(3));
        assertThrows(NegativeResAmountException.class, () -> extraLayer.setAmount(-1));
    }

    @Test
    void resetLayer() throws NotEnoughSpaceException, NegativeResAmountException {
        Layer extraLayer = new ExtraLayer(extraSlot3);
        extraLayer.setAmount(1);
        extraLayer.resetLayer();
        assertEquals(0, extraLayer.getAmount());
        assertEquals(ResourceType.SHIELD, extraLayer.getResource());
    }

    @Test
    void setResAndAmount() throws CannotContainFaithException, InvalidResourceException, LayerNotEmptyException, NegativeResAmountException, NotEnoughSpaceException {
        Layer extraLayer = new ExtraLayer(extraSlot4);
        assertThrows(InvalidResourceException.class, () -> extraLayer.setResAndAmount(ResourceType.SERVANT, 2));
        extraLayer.setResAndAmount(ResourceType.STONE, 1);
        assertEquals(ResourceType.STONE, extraLayer.getResource());
        assertEquals(1, extraLayer.getAmount());
        extraLayer.setResAndAmount(ResourceType.STONE, 2);
        assertEquals(ResourceType.STONE, extraLayer.getResource());
        assertEquals(2, extraLayer.getAmount());
    }
}