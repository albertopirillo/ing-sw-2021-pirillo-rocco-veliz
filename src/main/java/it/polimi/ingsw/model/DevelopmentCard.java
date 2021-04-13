package it.polimi.ingsw.model;

public class DevelopmentCard extends Card {

    private final Resource cost;
    private final CardColor type;
    private final int level;
    private final ProductionPower prodPower;

    public DevelopmentCard(int victoryPoints, Resource cost, CardColor type, int level, ProductionPower prodPower) {
        super(victoryPoints);
        this.cost = cost;
        this.type = type;
        this.level = level;
        this.prodPower = prodPower;
    }

    //check if the card(this) can be bought
    public boolean canBeBought(Resource playerResource){
        return playerResource.compare(this.cost);
    }
    //get level
    public int getLevel(){
        return this.level;
    }

    //get cost
    public Resource getCost(){
        return cost;
    }

    //get type
    public CardColor getType(){
        return this.type;
    }

    public ProductionPower getProdPower() {
        return prodPower;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DevelopmentCard)) return false;
        DevelopmentCard that = (DevelopmentCard) o;
        return this.getVictoryPoints() == that.getVictoryPoints()
                && this.level == that.level
                && that.cost.getMap().equals(this.cost.getMap())
                && getType().equals(that.getType())
                && prodPower.getInput().getMap().equals(that.prodPower.getInput().getMap())
                && prodPower.getOutput().getMap().equals(that.prodPower.getOutput().getMap());
    }

    @Override
    public String toString() {
        return "DevelopmentCard{" +
                "cost=" + cost +
                ", type=" + type +
                ", level=" + level +
                ", prodPower=" + prodPower +
                '}';
    }
}