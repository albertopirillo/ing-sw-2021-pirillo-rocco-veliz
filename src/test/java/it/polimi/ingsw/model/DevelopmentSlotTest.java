package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.DevSlotEmptyException;
import it.polimi.ingsw.exceptions.FullCardDeckException;
import it.polimi.ingsw.exceptions.InvalidKeyException;
import it.polimi.ingsw.exceptions.NegativeResAmountException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DevelopmentSlotTest {
    //stub
    Resource res1 = new Resource();
    Resource res2 = new Resource();
    Resource res3 = new Resource();

    @Test
    void getCards() throws InvalidKeyException, NegativeResAmountException {
        res1.addResource(ResourceType.SHIELD,2);
        res2.addResource(ResourceType.FAITH, 1);
        res3.addResource(ResourceType.COIN,1);
        DevelopmentCard dev1 = new DevelopmentCard(1, res1, CardColor.GREEN, 3, new ProductionPower(res2, res3));
        //copy of dev1
        DevelopmentCard test = new DevelopmentCard(1, res1, CardColor.GREEN, 3, new ProductionPower(res2, res3));

        DevelopmentCard dev2 = new DevelopmentCard(1, res1, CardColor.GREEN, 2, new ProductionPower(res2, res3));
        DevelopmentCard dev3 = new DevelopmentCard(1, res1, CardColor.GREEN, 1, new ProductionPower(res2, res3));
        DevelopmentSlot devSlot = new DevelopmentSlot();
        devSlot.addCard(dev1);
        devSlot.addCard(dev2);
        devSlot.addCard(dev3);
        List<DevelopmentCard> devCards = devSlot.getCards();
        assertTrue(dev3.equals(devCards.get(0)));
        assertTrue(dev2.equals(devCards.get(1)));
        assertTrue(dev1.equals(devCards.get(2)));
        assertTrue(test.equals(devCards.get(2)));
        assertEquals(3, devCards.size());
    }

    @Test
    void addCard() throws InvalidKeyException, NegativeResAmountException {
        //first element of devCards is..
        res1.addResource(ResourceType.SHIELD,2);
        res2.addResource(ResourceType.FAITH, 1);
        res3.addResource(ResourceType.COIN,1);
        DevelopmentCard dev1 = new DevelopmentCard(1, res1, CardColor.GREEN, 2, new ProductionPower(res2, res3));
        DevelopmentCard dev2 = new DevelopmentCard(1, res1, CardColor.GREEN, 2, new ProductionPower(res2, res3));
        DevelopmentCard dev3 = new DevelopmentCard(1, res1, CardColor.GREEN, 2, new ProductionPower(res2, res3));

        DevelopmentSlot devSlot = new DevelopmentSlot();
        assertEquals(0, devSlot.getCards().size());
        devSlot.addCard(dev1);
        assertEquals(1, devSlot.getCards().size());
        devSlot.addCard(dev2);
        assertEquals(2, devSlot.getCards().size());
        devSlot.addCard(dev3);
        assertEquals(3, devSlot.getCards().size());
    }

    @Test
    void getLevelSlot() throws InvalidKeyException, NegativeResAmountException, DevSlotEmptyException {
        res1.addResource(ResourceType.SHIELD,2);
        res2.addResource(ResourceType.FAITH, 1);
        res3.addResource(ResourceType.COIN,1);
        DevelopmentCard dev1 = new DevelopmentCard(1, res1, CardColor.GREEN, 3, new ProductionPower(res2, res3));
        DevelopmentCard dev2 = new DevelopmentCard(1, res1, CardColor.GREEN, 2, new ProductionPower(res2, res3));
        DevelopmentCard dev3 = new DevelopmentCard(1, res1, CardColor.GREEN, 1, new ProductionPower(res2, res3));
        DevelopmentSlot devSlot = new DevelopmentSlot();
        assertThrows(DevSlotEmptyException.class, () -> devSlot.getLevelSlot());
        devSlot.addCard(dev1);
        assertEquals(3, devSlot.getLevelSlot());
        devSlot.addCard(dev2);
        assertEquals(2, devSlot.getLevelSlot());
        devSlot.addCard(dev3);
        assertEquals(1, devSlot.getLevelSlot());
    }

    @Test
    void getTopCard() throws InvalidKeyException, NegativeResAmountException{
        res1.addResource(ResourceType.SHIELD,2);
        res2.addResource(ResourceType.FAITH, 1);
        res3.addResource(ResourceType.COIN,1);
        DevelopmentCard dev1 = new DevelopmentCard(1, res1, CardColor.GREEN, 3, new ProductionPower(res2, res3));
        //copy of dev1
        DevelopmentCard test = new DevelopmentCard(1, res1, CardColor.GREEN, 3, new ProductionPower(res2, res3));

        DevelopmentCard dev2 = new DevelopmentCard(1, res1, CardColor.GREEN, 2, new ProductionPower(res2, res3));
        DevelopmentCard dev3 = new DevelopmentCard(1, res1, CardColor.GREEN, 1, new ProductionPower(res2, res3));
        DevelopmentSlot devSlot = new DevelopmentSlot();
        assertThrows(DevSlotEmptyException.class, () -> devSlot.getLevelSlot());
        devSlot.addCard(dev1);
        assertTrue(dev1.equals(devSlot.getTopCard()));
        assertFalse(dev2.equals(devSlot.getTopCard()));
        assertTrue(test.equals(devSlot.getTopCard()));
        devSlot.addCard(dev2);
        assertEquals(dev2, devSlot.getTopCard());
        devSlot.addCard(dev3);
        assertEquals(dev3, devSlot.getTopCard());
    }

    @Test
    void canBeAdded() throws InvalidKeyException, NegativeResAmountException, DevSlotEmptyException {
        res1.addResource(ResourceType.SHIELD,2);
        res2.addResource(ResourceType.FAITH, 1);
        res3.addResource(ResourceType.COIN,1);
        DevelopmentCard dev1 = new DevelopmentCard(1, res1, CardColor.GREEN, 3, new ProductionPower(res2, res3));
        DevelopmentCard dev2 = new DevelopmentCard(1, res1, CardColor.GREEN, 2, new ProductionPower(res2, res3));
        DevelopmentCard dev3 = new DevelopmentCard(1, res1, CardColor.GREEN, 1, new ProductionPower(res2, res3));
        DevelopmentSlot devSlot = new DevelopmentSlot();

        assertTrue(devSlot.canBeAdded(dev1));
        devSlot.addCard(dev1);
        assertFalse(devSlot.canBeAdded(dev1));
        assertFalse(devSlot.canBeAdded(dev3));
        assertTrue(devSlot.canBeAdded(dev2));
        devSlot.addCard(dev2);
        assertFalse(devSlot.canBeAdded(dev1));
        assertFalse(devSlot.canBeAdded(dev2));
        assertTrue(devSlot.canBeAdded(dev3));
        devSlot.addCard(dev3);
        assertFalse(devSlot.canBeAdded(dev1));
        assertFalse(devSlot.canBeAdded(dev2));
        assertFalse(devSlot.canBeAdded(dev3));
    }
}