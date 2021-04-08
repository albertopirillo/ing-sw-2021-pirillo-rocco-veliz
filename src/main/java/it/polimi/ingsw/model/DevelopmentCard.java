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
    public Resource getResource(){
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
                && that.cost.getAllRes().equals(this.cost.getAllRes())
                && getType().equals(that.getType())
                && prodPower.getInput().getAllRes().equals(that.prodPower.getInput().getAllRes())
                && prodPower.getOutput().getAllRes().equals(that.prodPower.getOutput().getAllRes());
    }

   /* @Override
    public int hashCode() {
        return Objects.hash(cost, getType(), getLevel());
    }*/
}