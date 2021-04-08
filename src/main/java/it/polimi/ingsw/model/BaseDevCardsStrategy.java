package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.DeckEmptyException;
import it.polimi.ingsw.exceptions.InvalidKeyException;
import it.polimi.ingsw.exceptions.NegativeResAmountException;
import it.polimi.ingsw.exceptions.NotEnoughResException;

public class BaseDevCardsStrategy {

    /*public DevelopmentCard buyDevCard(int level, CardColor color, Market market, boolean choice) throws DeckEmptyException {
        DevelopmentCard card = market.buyCards(level, color);
    }*/

    public void addAbility(DevCardsStrategy ability) {
    }

    public Discount[] getDiscounts(){
        return null;
    }

    public void buyDevCard(int level, CardColor color, Market market, boolean standard, Player player) throws DeckEmptyException, NegativeResAmountException, InvalidKeyException, NotEnoughResException {
        DevelopmentCard card = market.getCard(level, color);
        if (card.canBeBought(player.getAllResources())) {
            market.buyCards(level, color);
            player.getPersonalBoard().addDevCard(card);
            Resource cost = card.getResource();
            //TODO: removes those resources from player
        }
        else throw new NotEnoughResException();
    }
}
