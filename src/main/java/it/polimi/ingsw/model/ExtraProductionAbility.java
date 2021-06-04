package it.polimi.ingsw.model;


import it.polimi.ingsw.exceptions.TooManyLeaderAbilitiesException;

import java.io.Serializable;


/**
 * Leader ability that gives the player a new production power
 */
public class ExtraProductionAbility extends LeaderAbility implements Serializable {

    private ProductionPower production;

    /**
     * Constructs a new object
     * @param production the new production power
     */
    public ExtraProductionAbility(ProductionPower production) {
        this.production = production;
    }

    /**
     * Gets the production power
     * @return a ProductionPower object representing the new power
     */
    public ProductionPower getProduction() {
        return production;
    }

    @Override
    public void activate(Player player) throws TooManyLeaderAbilitiesException {
        player.addProductionStrategy(this);
    }

    @Override
    public String toString(){
        return "\n\t\tType: " + "PRODUCTION" +
                "\n\t\tInput: " + production.getInput().toString() +
                "\n\t\tOutput: " + production.getOutput().toString();
    }

    @Override
    public ExtraProductionAbility clone() {
        ExtraProductionAbility clone;
        clone = (ExtraProductionAbility) super.clone();
        clone.production = this.production.clone();
        return clone;
    }
}