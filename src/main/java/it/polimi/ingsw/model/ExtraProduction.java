package it.polimi.ingsw.model;


public class ExtraProduction extends LeaderAbility {

    private final ProductionPower production;

    public ExtraProduction(ProductionPower production) {
        this.production = production;
    }

    public void activate(Player player) { player.changeProductionStrategy(new ProductionStrategy(this)); }

    public ProductionPower getProduction() {
        return production;
    }
}