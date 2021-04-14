package it.polimi.ingsw.model;


import it.polimi.ingsw.exceptions.TooManyLeaderAbilitiesException;

public class ExtraProduction extends LeaderAbility {

    private final ProductionPower production;

    public ExtraProduction(ProductionPower production) {
        this.production = production;
    }

    public ProductionPower getProduction() {
        return production;
    }

    public void activate(Player player) throws TooManyLeaderAbilitiesException {
        player.addProductionStrategy(this);
    }

}