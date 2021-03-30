package it.polimi.ingsw;

import it.polimi.ingsw.exceptions.CannotContainFaithException;
import it.polimi.ingsw.exceptions.LayerNotEmptyException;
import it.polimi.ingsw.exceptions.NegativeResAmountException;
import it.polimi.ingsw.exceptions.NotEnoughSpaceException;
import it.polimi.ingsw.model.DepotLayer;
import it.polimi.ingsw.model.ResourceType;
import org.junit.Test;


import static org.junit.Assert.*;

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
                () -> layer.setResource(ResourceType.FAITH));
    }

    @Test
    public void resourceTest() throws LayerNotEmptyException, NotEnoughSpaceException, NegativeResAmountException, CannotContainFaithException {
        DepotLayer layer = new DepotLayer(3);
        layer.setResource(ResourceType.COIN);
        assertEquals(ResourceType.COIN, layer.getResource());
        assertThrows(NotEnoughSpaceException.class, () -> layer.setAmount(5));
        layer.setAmount(2);
        assertEquals(2, layer.getAmount());
        assertThrows(NegativeResAmountException.class, () -> layer.setAmount(-2));
    }

    @Test
    public void moveTest() throws NotEnoughSpaceException, NegativeResAmountException, LayerNotEmptyException, CannotContainFaithException {
        DepotLayer layer = new DepotLayer(3);
        layer.setResource(ResourceType.SHIELD);
        layer.setAmount(3);
        assertThrows(LayerNotEmptyException.class,
                () -> layer.setResource(ResourceType.COIN));
        layer.setAmount(0);
        layer.setResource(ResourceType.COIN);
        assertEquals(ResourceType.COIN, layer.getResource());
    }

}
