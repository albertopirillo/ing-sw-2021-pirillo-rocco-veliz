package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.DeckEmptyException;
import it.polimi.ingsw.exceptions.InvalidKeyException;
import it.polimi.ingsw.exceptions.NegativeResAmountException;
import it.polimi.ingsw.exceptions.NotEnoughResException;

import java.util.Map;

public class BaseDevCardsStrategy {

    public void buyDevCard(int level, CardColor color, Market market, boolean standard, Player player) throws DeckEmptyException, NegativeResAmountException, InvalidKeyException, NotEnoughResException {
        DevelopmentCard card = market.getCard(level, color);
        if (card.canBeBought(player.getAllResources())) {
            market.buyCards(level, color);
            player.getPersonalBoard().addDevCard(card);
            Map<ResourceType, Integer> cost = card.getResource();
            //TODO: removes those resources from player
        }
        else throw new NotEnoughResException();
    }
}
