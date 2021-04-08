package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.DeckEmptyException;
import it.polimi.ingsw.exceptions.InvalidKeyException;
import it.polimi.ingsw.exceptions.NegativeResAmountException;
import it.polimi.ingsw.exceptions.NotEnoughResException;

import java.util.Map;

public class DevCardsStrategy extends BaseDevCardsStrategy {

    private final Discount[] discounts;

    public DevCardsStrategy(Discount discount) {
        this.discounts = new Discount[2];
        this.discounts[0] = discount;
    }

    public Discount[] getDiscounts() {
        return discounts;
    }

    public void addAbility(DevCardsStrategy ability){
        this.discounts[1] = ability.discounts[0];
    }

    public void buyDevCard(DevelopmentCard devCard) {
        // TODO implement here
    }

    @Override
    public void buyDevCard(int level, CardColor color, Market market, boolean standard, Player player) throws DeckEmptyException, NegativeResAmountException, InvalidKeyException, NotEnoughResException {
        if (standard) super.buyDevCard(level, color, market, true, player);
        else {
            Resource playerRes = player.getAllResources();
            DevelopmentCard card = market.getCard(level, color);
            Resource cost = card.getResource();

            for (Discount element : discounts) {
                //TODO: if the player wants
                ResourceType resType = element.getResource();
                cost.modifyValue(resType, - Math.max(cost.getValue(resType) - element.getAmount(), 0));
            }
            if (playerRes.compare(cost)) {
                market.buyCards(level, color);
                player.getPersonalBoard().addDevCard(card);
                //TODO: removes those resources from player
            }
            else throw new NotEnoughResException();
        }
    }
}