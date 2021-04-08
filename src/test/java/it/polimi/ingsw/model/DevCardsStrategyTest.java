package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DevCardsStrategyTest {

    @Test
    void addAbility() {
        DevCardsStrategy strategy = new DevCardsStrategy(new Discount(ResourceType.COIN,2));
        assertEquals(ResourceType.COIN, strategy.getDiscount()[0].getResource());
        assertEquals(2, strategy.getDiscount()[0].getAmount());
        strategy.addAbility(new DevCardsStrategy(new Discount(ResourceType.FAITH, 1)));
        assertEquals(ResourceType.COIN, strategy.getDiscount()[0].getResource());
        assertEquals(2, strategy.getDiscount()[0].getAmount());
        assertEquals(ResourceType.FAITH, strategy.getDiscount()[1].getResource());
        assertEquals(1, strategy.getDiscount()[1].getAmount());
    }
}