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

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("\n\t\tType: ").append(getLeaderAbilityType().toString());
        sb.append("\n\t\tInput: ").append(production.getInput().toString());
        sb.append("\n\t\tOutput: ").append(production.getOutput().toString());
        return sb.toString();
    }


}