package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

//Test cases of a ConcreteDepot decorated only one time
class DepotDecoratorTest {

    @Test
    public void buildTest() throws CannotContainFaithException, InvalidLayerNumberException, LayerNotEmptyException, NegativeResAmountException, NotEnoughSpaceException, AlreadyInAnotherLayerException, InvalidResourceException {
        Depot depot = new ConcreteDepot();
        ExtraSlot extraSlot = new ExtraSlot(ResourceType.SERVANT);
        depot.modifyLayer(1, ResourceType.COIN, 1);
        depot.modifyLayer(2, ResourceType.STONE, 2);
        DepotDecorator extendedDepot = new ConcreteDepotDecorator(depot, extraSlot);

        assertEquals(1, extendedDepot.getLayer(1).getAmount());
        assertEquals(ResourceType.COIN, extendedDepot.getLayer(1).getResource());
        assertEquals(2, extendedDepot.getLayer(2).getAmount());
        assertEquals(ResourceType.STONE, extendedDepot.getLayer(2).getResource());
        assertEquals(ResourceType.SERVANT, extendedDepot.getLayer(4).getResource());
        assertEquals(0, extendedDepot.getLayer(4).getAmount());
        assertThrows(InvalidLayerNumberException.class, () -> depot.modifyLayer(4, ResourceType.COIN, 1));
    }

    @Test
    public void getLayerTest() throws InvalidLayerNumberException {
        ExtraSlot extraSlot = new ExtraSlot(ResourceType.SERVANT);
        DepotDecorator extendedDepot = new ConcreteDepotDecorator(new ConcreteDepot(), extraSlot);
        assertThrows(InvalidLayerNumberException.class, () -> extendedDepot.getLayer(-3));
        assertThrows(InvalidLayerNumberException.class, () -> extendedDepot.getLayer(5));
    }

    @Test
    public void modifyLayerTest() throws CannotContainFaithException, InvalidLayerNumberException, LayerNotEmptyException, NegativeResAmountException, NotEnoughSpaceException, AlreadyInAnotherLayerException, InvalidResourceException {
        ExtraSlot extraSlot = new ExtraSlot(ResourceType.COIN);
        DepotDecorator extendedDepot = new ConcreteDepotDecorator(new ConcreteDepot(), extraSlot);
        extendedDepot.modifyLayer(4, ResourceType.COIN, 2);
        assertEquals(ResourceType.COIN, extendedDepot.getLayer(4).getResource());
        assertEquals(2, extendedDepot.getLayer(4).getAmount());

        extendedDepot.modifyLayer(3, ResourceType.SHIELD, 3);
        assertEquals(ResourceType.SHIELD, extendedDepot.getLayer(3).getResource());
        assertEquals(3, extendedDepot.getLayer(3).getAmount());
        assertThrows(NotEnoughSpaceException.class,
                () -> extendedDepot.modifyLayer(4, ResourceType.COIN, 3));
    }

    @Test
    public void getAllResTest() throws CannotContainFaithException, LayerNotEmptyException, NegativeResAmountException, NotEnoughSpaceException, InvalidKeyException, InvalidLayerNumberException, AlreadyInAnotherLayerException, InvalidResourceException {
        Depot depot = new ConcreteDepot();
        depot.modifyLayer(1, ResourceType.SERVANT, 1);
        depot.modifyLayer(2, ResourceType.COIN, 2);
        ExtraSlot extraSlot = new ExtraSlot(ResourceType.COIN);
        DepotDecorator extendedDepot = new ConcreteDepotDecorator(depot, extraSlot);
        extendedDepot.modifyLayer(4, ResourceType.COIN, 2);
        Resource actual = extendedDepot.queryAllRes();

        Resource expected = new Resource(0, 4, 0, 1);

        for (ResourceType key : ResourceType.values()) {
            if (key != ResourceType.FAITH) {
                assertEquals(expected.getValue(key), actual.getValue(key));
            }
        }
    }

    @Test
    public void moveTest() throws CannotContainFaithException, InvalidLayerNumberException, LayerNotEmptyException, NegativeResAmountException, NotEnoughSpaceException, NotEnoughResException, AlreadyInAnotherLayerException, InvalidResourceException {
        Depot depot = new ConcreteDepot();
        depot.modifyLayer(1, ResourceType.SERVANT, 1);
        depot.modifyLayer(3, ResourceType.COIN, 3);
        ExtraSlot extraSlot = new ExtraSlot(ResourceType.COIN);
        DepotDecorator extendedDepot = new ConcreteDepotDecorator(depot, extraSlot);
        extendedDepot.moveResources(2, 3, 4);

        assertEquals(1, extendedDepot.getLayer(3).getAmount());
        assertEquals(ResourceType.COIN, extendedDepot.getLayer(3).getResource());
        assertEquals(2, extendedDepot.getLayer(4).getAmount());
        assertEquals(ResourceType.COIN, extendedDepot.getLayer(4).getResource());
    }

    @Test
    public void moveTestExc() throws CannotContainFaithException, InvalidLayerNumberException, LayerNotEmptyException, NegativeResAmountException, NotEnoughSpaceException, AlreadyInAnotherLayerException, InvalidResourceException {
        Depot depot = new ConcreteDepot();
        depot.modifyLayer(1, ResourceType.SERVANT, 1);
        depot.modifyLayer(3, ResourceType.COIN, 3);
        ExtraSlot extraSlot = new ExtraSlot(ResourceType.COIN);
        DepotDecorator extendedDepot = new ConcreteDepotDecorator(depot, extraSlot);

        assertThrows(NotEnoughSpaceException.class,
                () ->  extendedDepot.moveResources(3, 3, 4));

        assertThrows(NotEnoughResException.class,
                () -> extendedDepot.moveResources(2, 1, 4));
    }

    @Test
    public void swapTest () throws CannotContainFaithException, InvalidLayerNumberException, LayerNotEmptyException, NegativeResAmountException, NotEnoughSpaceException, AlreadyInAnotherLayerException, InvalidResourceException {
        Depot depot = new ConcreteDepot();
        depot.modifyLayer(1, ResourceType.SERVANT, 1);
        depot.modifyLayer(3, ResourceType.COIN, 3);

        ExtraSlot extraSlot = new ExtraSlot(ResourceType.SHIELD);
        DepotDecorator extendedDepot = new ConcreteDepotDecorator(depot, extraSlot);
        extendedDepot.modifyLayer(3, ResourceType.COIN, -2);
        assertEquals(1, extendedDepot.getLayer(3).getAmount());

        extendedDepot.modifyLayer(4, ResourceType.SHIELD, 2);
        extendedDepot.modifyLayer(3, ResourceType.COIN, 1);

        assertEquals(2, extendedDepot.getLayer(4).getAmount());
        assertEquals(ResourceType.SHIELD, extendedDepot.getLayer(4).getResource());
        assertEquals(2, extendedDepot.getLayer(3).getAmount());
        assertEquals(ResourceType.COIN, extendedDepot.getLayer(3).getResource());
    }
}

