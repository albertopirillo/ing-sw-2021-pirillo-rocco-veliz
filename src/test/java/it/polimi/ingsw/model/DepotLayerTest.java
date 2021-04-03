package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.CannotContainFaithException;
import it.polimi.ingsw.exceptions.LayerNotEmptyException;
import it.polimi.ingsw.exceptions.NegativeResAmountException;
import it.polimi.ingsw.exceptions.NotEnoughSpaceException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DepotLayerTest {

    @Test
    public void buildTest() {
        DepotLayer layer = new DepotLayer(2);
        assertNull(layer.getResource());
        assertEquals(0, layer.getAmount());
    }

    @Test
    public void faithTest() {
        DepotLayer layer = new DepotLayer(3);
        assertThrows(CannotContainFaithException.class,
                () -> layer.setResAndAmount(ResourceType.FAITH, 2));
    }

    @Test
    public void resourceTest() throws LayerNotEmptyException, NotEnoughSpaceException, NegativeResAmountException, CannotContainFaithException {
        DepotLayer layer = new DepotLayer(3);
        layer.setResAndAmount(ResourceType.COIN, 1);
        assertEquals(ResourceType.COIN, layer.getResource());
        assertThrows(NotEnoughSpaceException.class, () -> layer.setAmount(5));
        layer.setAmount(2);
        assertEquals(2, layer.getAmount());
        assertThrows(NegativeResAmountException.class, () -> layer.setAmount(-2));
    }

    @Test
    public void moveTest() throws NotEnoughSpaceException, NegativeResAmountException, LayerNotEmptyException, CannotContainFaithException {
        DepotLayer layer = new DepotLayer(3);
        layer.setResAndAmount(ResourceType.SHIELD, 3);
        assertThrows(LayerNotEmptyException.class,
                () -> layer.setResAndAmount(ResourceType.COIN, 2));
        layer.resetLayer();
        layer.setResAndAmount(ResourceType.COIN, 2);
        assertEquals(ResourceType.COIN, layer.getResource());
    }

}
