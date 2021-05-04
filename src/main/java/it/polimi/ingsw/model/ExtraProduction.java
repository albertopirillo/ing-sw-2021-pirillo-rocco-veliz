package it.polimi.ingsw.model;


import it.polimi.ingsw.exceptions.TooManyLeaderAbilitiesException;

import java.io.Serializable;


public class ExtraProduction extends LeaderAbility implements Serializable {

    private final ProductionPower production;

    public ExtraProduction(ProductionPower production) {
        this.production = production;
    }

    public ProductionPower getProduction() {
        return production;
    }

    @Override
    public LeaderAbilityType getLeaderAbilityType() {
        return LeaderAbilityType.PRODUCTION;
    }

    public void activate(Player player) throws TooManyLeaderAbilitiesException {
        player.addProductionStrategy(this);
    }

}