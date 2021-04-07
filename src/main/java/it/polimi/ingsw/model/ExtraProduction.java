package it.polimi.ingsw.model;

import java.util.*;

public class ExtraProduction extends LeaderAbility {

    private ProductionPower production;

    public ExtraProduction(ProductionPower production) {
        this.production = production;
    }

    public void activate(Player player) { player.changeProductionStrategy(new ProductionStrategy(this)); }

    public ProductionPower getProduction() {
        return production;
    }
}