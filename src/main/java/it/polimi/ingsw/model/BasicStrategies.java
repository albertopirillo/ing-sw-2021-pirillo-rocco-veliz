package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.*;

public abstract class BasicStrategies {

    //Output resources can only go in the depot
    public static Resource insertMarble(Player player, int position) throws InvalidKeyException, NegativeResAmountException {
       Market market = player.getGame().getMarket();
       Marbles marbles = market.getMarketTray().insertMarble(position);
       Resource temp = marbles.getResources();
       Resource outputRes = new Resource(0,0,0,0);

       //Handles faith and returns a map with no faith in it
       for (ResourceType key: temp.keySet()) {
           if (key == ResourceType.FAITH) player.addPlayerFaith(temp.getValue(key));
           else outputRes.modifyValue(key, temp.getValue(key));
       }
       return outputRes;
    }

    //Input resources can be paid from either Depot or Strongbox
    public static void buyDevCard(Player player, int level, CardColor color, int numSlot, Resource cardCost, Resource fromDepot, Resource fromStrongbox) throws NotEnoughResException, DeckEmptyException, NegativeResAmountException, InvalidKeyException, NotEnoughSpaceException, CannotContainFaithException, CostNotMatchingException, DevSlotEmptyException, InvalidNumSlotException {
        Market market = player.getGame().getMarket();
        Depot depot = player.getPersonalBoard().getDepot();
        Strongbox strongbox = player.getPersonalBoard().getStrongbox();

        BasicStrategies.checkAndCompare(depot, strongbox, fromDepot, fromStrongbox, cardCost);

        //Give the card to the player
        DevelopmentCard card = market.buyCards(level, color);
        player.getPersonalBoard().addDevCard(card, numSlot);
        player.addVictoryPoints(card.getVictoryPoints());

        //Remove those resources from the player
        depot.retrieveRes(fromDepot);
        strongbox.retrieveRes(fromStrongbox);
    }

    //Input resources can be paid from either Depot or Strongbox
    //Output resources have to go in the Strongbox
    public static void basicProduction(Player player, ResourceType input1, ResourceType input2, ResourceType output, Resource fromDepot, Resource fromStrongbox) throws NotEnoughResException, NegativeResAmountException, InvalidKeyException, CostNotMatchingException, CannotContainFaithException, NotEnoughSpaceException {
        Depot depot = player.getPersonalBoard().getDepot();
        Strongbox strongbox = player.getPersonalBoard().getStrongbox();

        Resource expectedRes = new Resource(0,0,0,0);
        expectedRes.modifyValue(input1, 1);
        expectedRes.modifyValue(input2, 1);
        BasicStrategies.checkAndCompare(depot, strongbox, fromDepot, fromStrongbox, expectedRes);

        //Give the resources to the player
        Resource outputRes = new Resource();
        outputRes.addResource(output, 1);
        player.getPersonalBoard().getStrongbox().addResources(outputRes);

        //Remove those resources from the player
        depot.retrieveRes(fromDepot);
        strongbox.retrieveRes(fromStrongbox);
    }

    public static void checkAndCompare(Depot depot, Strongbox strongbox, Resource fromDepot, Resource fromStrongbox, Resource expectedRes) throws NegativeResAmountException, InvalidKeyException, NotEnoughResException, CostNotMatchingException {
        //Check if the player really has those resources
        if (!depot.queryAllRes().compare(fromDepot) || !strongbox.queryAllRes().compare(fromStrongbox))
            throw new NotEnoughResException();

        //Sum up those resources and checks if they are enough to perform the action
        if (!fromDepot.sum(fromStrongbox).equals(expectedRes)) throw new CostNotMatchingException();
    }
}
