package it.polimi.ingsw.model;

public class DevelopmentCard extends Card implements Cloneable {

    private Resource cost;
    private final CardColor type;
    private final String img;
    private final int level;
    private ProductionPower prodPower;

    public DevelopmentCard(int victoryPoints, Resource cost, CardColor type, int level, ProductionPower prodPower, String img) {
        super(victoryPoints);
        this.cost = cost;
        this.type = type;
        this.level = level;
        this.prodPower = prodPower;
        this.img = img;
    }

    //check if the card(this) can be bought
    public boolean canBeBought(Resource playerResource){
        return playerResource.compare(this.cost);
    }

    public String getImg(){
        return this.img;
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
        return "\tCost: " + cost.toString() +
                "\n\tColor: " + type +
                "\n\tLevel: " + level +
                "\n\tProduction power: " + prodPower;
    }

    @Override
    public DevelopmentCard clone() {
        DevelopmentCard clone = null;
        try {
            clone = (DevelopmentCard) super.clone();
            clone.cost = this.cost.clone();
            clone.prodPower = this.prodPower.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return clone;
    }
}