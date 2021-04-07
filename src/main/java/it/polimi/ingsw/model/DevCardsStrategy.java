package it.polimi.ingsw.model;

public class DevCardsStrategy extends BaseDevCardsStrategy {

    private Discount[] discount;

    public DevCardsStrategy(Discount discount) {
        this.discount = new Discount[2];
        this.discount[0] = discount;
    }

    public Discount[] getDiscount() {
        return discount;
    }

    public void addAbility(DevCardsStrategy ability){
        this.discount[1] = ability.discount[0];
    }

    public void buyDevCard(DevelopmentCard devCard) {
        // TODO implement here
    }


}