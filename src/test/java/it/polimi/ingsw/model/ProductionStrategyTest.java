package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductionStrategyTest {

    @Test
    void addAbility() {
        Resource res1 = new Resource(1,2,3,4);
        Resource res2 = new Resource(1,2,3,2);
        ProductionStrategy strategy = new ProductionStrategy(new ExtraProduction(new ProductionPower(res1, res2)));
        assertEquals(res1.getAllRes(), strategy.getProduction()[0].getProduction().getInput().getAllRes());
        assertEquals(res2.getAllRes(), strategy.getProduction()[0].getProduction().getOutput().getAllRes());
        strategy.addAbility(new ProductionStrategy(new ExtraProduction(new ProductionPower(res2, res1))));
        assertEquals(res1.getAllRes(), strategy.getProduction()[0].getProduction().getInput().getAllRes());
        assertEquals(res2.getAllRes(), strategy.getProduction()[0].getProduction().getOutput().getAllRes());
        assertEquals(res2.getAllRes(), strategy.getProduction()[1].getProduction().getInput().getAllRes());
        assertEquals(res1.getAllRes(), strategy.getProduction()[1].getProduction().getOutput().getAllRes());
    }
}