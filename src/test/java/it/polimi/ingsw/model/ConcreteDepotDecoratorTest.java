package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

//Test cases of a ConcreteDepot decorated two times
class ConcreteDepotDecoratorTest {

    @Test
    public void buildTest() throws InvalidLayerNumberException, CannotContainFaithException, LayerNotEmptyException, NegativeResAmountException, NotEnoughSpaceException, AlreadyInAnotherLayerException, InvalidResourceException {
        Depot depot = new ConcreteDepot();
        depot.modifyLayer(1, ResourceType.COIN, 1);
        depot.modifyLayer(2, ResourceType.SERVANT, 1);
        depot.modifyLayer(3, ResourceType.SHIELD, 3);

        ExtraSlot extraSlot = new ExtraSlot(ResourceType.COIN);
        DepotDecorator extendedDepot = new ConcreteDepotDecorator(depot, extraSlot);
        extendedDepot.modifyLayer(4, ResourceType.COIN, 2);
        ExtraSlot extraSlot2 = new ExtraSlot(ResourceType.SERVANT);
        DepotDecorator doubleExtendedDepot = new ConcreteDepotDecorator(extendedDepot, extraSlot2);

        for (int i = 1; i <= 3; i++) {
            assertEquals(depot.getLayer(i), doubleExtendedDepot.getLayer(i));
        }
        assertEquals(extendedDepot.getLayer(4), doubleExtendedDepot.getLayer(4));
        assertEquals(0, doubleExtendedDepot.getLayer(5).getAmount());
        assertEquals(ResourceType.SERVANT, doubleExtendedDepot.getLayer(5).getResource());
    }

    @Test
    public void invalidBuildTest() throws InvalidLayerNumberException {
        Depot depot = new ConcreteDepot();
        ExtraSlot extraSlot = new ExtraSlot(ResourceType.SERVANT);
        DepotDecorator extendedDepot = new ConcreteDepotDecorator(depot, extraSlot);
        ExtraSlot extraSlot2 = new ExtraSlot(ResourceType.COIN);
        DepotDecorator doubleExtendedDepot = new ConcreteDepotDecorator(extendedDepot, extraSlot2);
        assertThrows(InvalidLayerNumberException.class,
                () -> new ConcreteDepotDecorator(doubleExtendedDepot, extraSlot));
    }


    @Test
    public void modifyAndGetTest() throws InvalidLayerNumberException, LayerNotEmptyException, NegativeResAmountException, NotEnoughSpaceException, AlreadyInAnotherLayerException, InvalidResourceException, CannotContainFaithException {
        Depot depot = new ConcreteDepot();
        depot.modifyLayer(1, ResourceType.COIN, 1);
        ExtraSlot extraSlot = new ExtraSlot(ResourceType.COIN);
        DepotDecorator extendedDepot = new ConcreteDepotDecorator(depot, extraSlot);
        extendedDepot.modifyLayer(4, ResourceType.COIN, 2);
        ExtraSlot extraSlot2 = new ExtraSlot(ResourceType.COIN);
        DepotDecorator doubleExtendedDepot = new ConcreteDepotDecorator(extendedDepot, extraSlot2);

        assertThrows(InvalidLayerNumberException.class,
                () -> extendedDepot.modifyLayer(5, ResourceType.COIN, 2));

        assertThrows(InvalidResourceException.class,
                () -> doubleExtendedDepot.modifyLayer(5, ResourceType.FAITH, 2));

        assertThrows(NotEnoughSpaceException.class,
                () -> doubleExtendedDepot.modifyLayer(5, ResourceType.COIN, 3));

        assertThrows(NegativeResAmountException.class,
                () -> doubleExtendedDepot.modifyLayer(5, ResourceType.COIN, -1));
    }

    @Test
    void moveResources() throws CannotContainFaithException, InvalidLayerNumberException, LayerNotEmptyException, NegativeResAmountException, NotEnoughSpaceException, NotEnoughResException, AlreadyInAnotherLayerException, InvalidResourceException {
        Depot depot = new ConcreteDepot();
        depot.modifyLayer(1, ResourceType.SHIELD, 1);
        ExtraSlot extraSlot = new ExtraSlot(ResourceType.COIN);
        DepotDecorator extendedDepot = new ConcreteDepotDecorator(depot, extraSlot);
        extendedDepot.modifyLayer(4, ResourceType.COIN, 2);
        ExtraSlot extraSlot2 = new ExtraSlot(ResourceType.SERVANT);
        DepotDecorator doubleExtendedDepot = new ConcreteDepotDecorator(extendedDepot, extraSlot2);

        doubleExtendedDepot.moveResources(1, 4, 2);
        assertEquals(1, doubleExtendedDepot.getLayer(2).getAmount());
        assertEquals(1, doubleExtendedDepot.getLayer(4).getAmount());
        assertEquals(ResourceType.COIN, doubleExtendedDepot.getLayer(2).getResource());
        assertEquals(ResourceType.COIN, doubleExtendedDepot.getLayer(4).getResource());
    }

    @Test
    public void getAllResTest() throws CannotContainFaithException, LayerNotEmptyException, NegativeResAmountException, NotEnoughSpaceException, InvalidKeyException, InvalidLayerNumberException, AlreadyInAnotherLayerException, InvalidResourceException {
        Depot depot = new ConcreteDepot();
        depot.modifyLayer(1, ResourceType.SERVANT, 1);
        depot.modifyLayer(2, ResourceType.COIN, 2);
        depot.modifyLayer(3, ResourceType.STONE, 3);
        ExtraSlot extraSlot = new ExtraSlot(ResourceType.SHIELD);
        DepotDecorator extendedDepot = new ConcreteDepotDecorator(depot, extraSlot);
        ExtraSlot extraSlot2 = new ExtraSlot(ResourceType.STONE);
        DepotDecorator doubleExtendedDepot = new ConcreteDepotDecorator(extendedDepot, extraSlot2);
        doubleExtendedDepot.modifyLayer(5, ResourceType.STONE, 1);
        Resource actual = doubleExtendedDepot.queryAllRes();

        Resource expected = new Resource(4, 2, 0, 1);

        for (ResourceType key : ResourceType.values()) {
            if (key != ResourceType.FAITH && key != ResourceType.ALL) {
                assertEquals(expected.getValue(key), actual.getValue(key));
            }
        }
    }

    @Test
    public void realTest() throws AlreadyInAnotherLayerException, CannotContainFaithException, NotEnoughSpaceException, NegativeResAmountException, LayerNotEmptyException, InvalidLayerNumberException, NotEnoughResException, InvalidKeyException, InvalidResourceException {
        Depot depot = new ConcreteDepot();
        depot.modifyLayer(2, ResourceType.COIN, 1);
        depot.moveResources(1, 2, 1);
        assertEquals(1, depot.getLayer(1).getAmount());
        assertNull(depot.getLayer(2).getResource());

        depot.modifyLayer(3, ResourceType.SHIELD, 1);
        depot.moveResources(1, 3, 1);
        assertEquals(ResourceType.SHIELD, depot.getLayer(1).getResource());
        assertEquals(ResourceType.COIN, depot.getLayer(3).getResource());

        depot.retrieveRes(new Resource(0, 1, 0, 0));
        assertNull(depot.getLayer(3).getResource());

        ExtraSlot extraSlot = new ExtraSlot(ResourceType.SERVANT);
        DepotDecorator extendedDepot = new ConcreteDepotDecorator(depot, extraSlot);
        assertEquals(ResourceType.SHIELD, extendedDepot.getLayer(1).getResource());
        extendedDepot.modifyLayer(4, ResourceType.SERVANT, 2);
        extendedDepot.retrieveRes(new Resource(0, 0, 0, 2));
        assertEquals(ResourceType.SERVANT, extendedDepot.getLayer(4).getResource());

        extendedDepot.modifyLayer(3, ResourceType.SERVANT, 2);
        ExtraSlot extraSlot2 = new ExtraSlot(ResourceType.SERVANT);
        DepotDecorator doubleExtendedDepot = new ConcreteDepotDecorator(extendedDepot, extraSlot2);
        assertEquals(2, doubleExtendedDepot.getLayer(3).getAmount());
        doubleExtendedDepot.moveResources(2, 3, 5);
        assertEquals(ResourceType.SERVANT, doubleExtendedDepot.getLayer(5).getResource());
        assertNull(doubleExtendedDepot.getLayer(3).getResource());
    }
}