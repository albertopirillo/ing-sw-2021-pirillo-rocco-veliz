package it.polimi.ingsw.model;


public class ExtraProduction extends LeaderAbility {

    private final ProductionPower production;

    public ExtraProduction(ProductionPower production) {
        this.production = production;
    }

    public ProductionPower getProduction() {
        return production;
    }
}