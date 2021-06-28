package it.polimi.ingsw.model;

/**
 * <p>The concept of Development Card</p>
 * <p>Each Development Card has a color(or type), a cost, a level and a Production Power</p>
 */
public class DevelopmentCard extends Card implements Cloneable {

    /**
     * The resources corresponding to the card's cost
     */
    private Resource cost;
    /**
     * The card's color
     */
    private final CardColor type;
    /**
     * The identifier of the image's path
     * */
    private final String img;
    /**
     * The card level, from one to three
     */
    private final int level;
    /**
     * The ProductionPower object associated to the card
     */
    private ProductionPower prodPower;

    /**
     * Create a Development Card
     * @param victoryPoints the victory points of the cards
     * @param cost the resources associated to the card's cost
     * @param type card color
     * @param level card level
     * @param prodPower ProductionPower object
     * @param img image's path identifier
     */
    public DevelopmentCard(int victoryPoints, Resource cost, CardColor type, int level, ProductionPower prodPower, String img) {
        super(victoryPoints);
        this.cost = cost;
        this.type = type;
        this.level = level;
        this.prodPower = prodPower;
        this.img = img;
    }

    public String getImg(){
        return this.img;
    }

    public int getLevel(){
        return this.level;
    }

    public Resource getCost(){
        return cost;
    }

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
                "\n\tVictory points: " + getVictoryPoints() +
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