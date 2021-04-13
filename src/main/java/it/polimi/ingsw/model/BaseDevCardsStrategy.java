package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.DeckEmptyException;
import it.polimi.ingsw.exceptions.InvalidKeyException;
import it.polimi.ingsw.exceptions.NegativeResAmountException;
import it.polimi.ingsw.exceptions.NotEnoughResException;

public class BaseDevCardsStrategy {

    public void addAbility(DevCardsStrategy ability) {
    }

    public Discount[] getDiscounts(){
        return null;
    }

    public void buyDevCard(Player player, int level, boolean standard, CardColor color, Resource fromDepot, Resource fromStrongbox) throws DeckEmptyException, NegativeResAmountException, InvalidKeyException, NotEnoughResException {
        Market market = player.getGame().getMarket();
        DevelopmentCard card = market.getCard(level, color);

        //Check if the player really has those resources
        if (!player.getPersonalBoard().getDepot().queryAllRes().compare(fromDepot) ||
                !player.getPersonalBoard().getStrongbox().queryAllRes().compare(fromStrongbox))
                throw new NotEnoughResException();

        //Sum up those resources and check if it is enough to buy the card

        //Give the card to the player

        //Remove those resources from the player

        //What if more?


        if (card.canBeBought(player.getAllResources())) {
            market.buyCards(level, color);
            player.getPersonalBoard().addDevCard(card);
            Resource cost = card.getCost();
            //TODO: removes those resources from player
        }
        else throw new NotEnoughResException();
    }
}
