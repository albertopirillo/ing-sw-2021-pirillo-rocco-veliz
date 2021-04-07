package it.polimi.ingsw.model;

public class ProductionStrategy extends BaseProductionStrategy {

    private ExtraProduction[] production;

    public ProductionStrategy(ExtraProduction production) {
        this.production = new ExtraProduction[2];
        this.production[0] = production;
    }

    public void addAbility(ProductionStrategy ability){
        this.production[1] = ability.production[0];
    }

    public ExtraProduction[] getProduction() {
        return production;
    }

    public ResourceType extraProduction() {
        // TODO implement here
        return null;
    }

}