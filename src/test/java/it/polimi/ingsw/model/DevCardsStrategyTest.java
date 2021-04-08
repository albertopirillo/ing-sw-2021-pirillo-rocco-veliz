package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DevCardsStrategyTest {

    @Test
    void addAbility() {
        DevCardsStrategy strategy = new DevCardsStrategy(new Discount(ResourceType.COIN,2));
        assertEquals(ResourceType.COIN, strategy.getDiscounts()[0].getResource());
        assertEquals(2, strategy.getDiscounts()[0].getAmount());
        strategy.addAbility(new DevCardsStrategy(new Discount(ResourceType.FAITH, 1)));
        assertEquals(ResourceType.COIN, strategy.getDiscounts()[0].getResource());
        assertEquals(2, strategy.getDiscounts()[0].getAmount());
        assertEquals(ResourceType.FAITH, strategy.getDiscounts()[1].getResource());
        assertEquals(1, strategy.getDiscounts()[1].getAmount());
    }
}