package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.DeckEmptyException;
import it.polimi.ingsw.exceptions.InvalidKeyException;
import it.polimi.ingsw.exceptions.NegativeResAmountException;
import it.polimi.ingsw.exceptions.NotEnoughResException;

import java.util.Map;

public class DevCardsStrategy extends BaseDevCardsStrategy {

    private final Discount[] discount;

    public DevCardsStrategy() {
        this.discount = new Discount[2];
    }

    @Override
    public void buyDevCard(int level, CardColor color, Market market, boolean standard, Player player) throws DeckEmptyException, NegativeResAmountException, InvalidKeyException, NotEnoughResException {
        if (standard) super.buyDevCard(level, color, market, true, player);
        else {
            Resource playerRes = player.getAllResources();
            DevelopmentCard card = market.getCard(level, color);
            Map<ResourceType, Integer> cost = card.getResource();

            for (Discount element : discount) {
                //TODO: if the player wants
                ResourceType resType = element.getResource();
                cost.put(resType, Math.max(cost.get(resType) - element.getAmount(), 0));
            }
            if (playerRes.compare(new Resource(cost))) {
                market.buyCards(level, color);
                player.getPersonalBoard().addDevCard(card);
                //TODO: removes those resources from player
            }
            else throw new NotEnoughResException();
        }
    }
}