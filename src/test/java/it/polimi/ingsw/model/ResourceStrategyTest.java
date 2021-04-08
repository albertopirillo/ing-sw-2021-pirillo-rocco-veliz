package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResourceStrategyTest {

    @Test
    void addAbility() {
        ResourceStrategy strategy = new ResourceStrategy(new ChangeWhiteMarbles(ResourceType.COIN));
        assertEquals(ResourceType.COIN, strategy.getResType()[0].getResourceType());
        strategy.addAbility(new ResourceStrategy(new ChangeWhiteMarbles(ResourceType.FAITH)));
        assertEquals(ResourceType.COIN, strategy.getResType()[0].getResourceType());
        assertEquals(ResourceType.FAITH, strategy.getResType()[1].getResourceType());
    }
}