package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.*;

/**
 * <p>Contains the algorithms to perform the main actions with no leader abilities</p>
 * <p>Part of the strategy pattern</p>
 * <p>Static: the algorithms are the same for every player</p>
 */
public abstract class BasicStrategies {

    /**
     * <p>Algorithm to perform the take resources from Market action</p>
     * <p>Output resources can only go in the depot</p>
     * @param player    the player that is performing the action
     * @param position  the position of the grid where the remaining marble should be inserted
     * @return  the resources that need to go in TempResource
     */
    public static Resource insertMarble(Player player, int position) throws InvalidKeyException, NegativeResAmountException {
       Market market = player.getGame().getMarket();
       Marbles marbles = market.getMarketTray().insertMarble(position);
       Resource temp = marbles.getResources();
       Resource outputRes = new Resource(0,0,0,0);

       //Handles faith and returns a map with no faith in it
       for (ResourceType key: temp.keySet()) {
           if (key == ResourceType.FAITH){
               player.addPlayerFaith(temp.getValue(key));
               player.getGame().updateFaithTrack();
           }
           else outputRes.modifyValue(key, temp.getValue(key));
       }
       return outputRes;
    }

    /**
     * <p>Algorithm to perform the buy card from Market action</p>
     * <p>Input resources can be paid from either Depot or Strongbox</p>
     * @param player    the player that is performing the action
     * @param level the level of the card the player wants to buy
     * @param color the color of the card the player wants to buy
     * @param numSlot the number of the dev slot where the player wants to place the card
     * @param cardCost  the resource cost of the card
     * @param fromDepot the amount of resources the player is paying from the depot
     * @param fromStrongbox the amount of resources the player is paying from the strongbox
     * @throws NotEnoughResException    if not enough resources were given
     * @throws DeckEmptyException   if the chosen card doesnt exist
     * @throws NotEnoughSpaceException  if the chosen dev slot is already full
     * @throws CannotContainFaithException  if the player is trying to pay with Faith
     * @throws CostNotMatchingException if the player has enough resources but didnt provide the exact amount
     * @throws InvalidNumSlotException  if the player has chosen a dev slot that doesnt exist
     */
    public static void buyDevCard(Player player, int level, CardColor color, int numSlot, Resource cardCost, Resource fromDepot, Resource fromStrongbox) throws NotEnoughResException, DeckEmptyException, NegativeResAmountException, NotEnoughSpaceException, CannotContainFaithException, CostNotMatchingException, DevSlotEmptyException, InvalidNumSlotException {
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

    /**
     * <p>Algorithm to perform basic production action</p>
     * <p>Input resources can be paid from either Depot or Strongbox</p>
     * <p>Output resources have to go in the Strongbox</p>
     * @param player    the player that is performing the action
     * @param input1    the first resource type paid
     * @param input2    the second resource type paid
     * @param output    the wanted output resource type
     * @param fromDepot the amount of resources the player is paying from the depot
     * @param fromStrongbox the amount of resources the player is paying from the strongbox
     * @throws NotEnoughResException    if the player hasn't got that resources
     * @throws CostNotMatchingException if the player hasn't provided the exact amount of resources
     * @throws CannotContainFaithException  if the player is trying to get faith in output
     */
    public static void basicProduction(Player player, ResourceType input1, ResourceType input2, ResourceType output, Resource fromDepot, Resource fromStrongbox) throws NotEnoughResException, NegativeResAmountException, CostNotMatchingException, CannotContainFaithException, NotEnoughSpaceException {
        if(output == ResourceType.FAITH || output == ResourceType.ALL) throw new CannotContainFaithException();
        Depot depot = player.getPersonalBoard().getDepot();
        Strongbox strongbox = player.getPersonalBoard().getStrongbox();

        Resource expectedRes = new Resource(0,0,0,0);
        expectedRes.modifyValue(input1, 1);
        expectedRes.modifyValue(input2, 1);
        BasicStrategies.checkAndCompare(depot, strongbox, fromDepot, fromStrongbox, expectedRes);

        //Give the resources to the player
        Resource outputRes = new Resource();
        outputRes.addResource(output, 1);
        player.getPersonalBoard().getStrongbox().addTempResources(outputRes);

        //Remove those resources from the player
        depot.retrieveRes(fromDepot);
        strongbox.retrieveRes(fromStrongbox);
    }

    /**
     * Checks if the player has the provided resources and they are enough to complete the action
     * @param depot the player's depot
     * @param strongbox the player's strongbox
     * @param fromDepot the resources paid from the depot
     * @param fromStrongbox the resources paid from the strongbox
     * @param expectedRes   the resources required to complete the action
     * @throws NotEnoughResException   if the player hasn't got the provided resources
     * @throws CostNotMatchingException if the provided resources aren't enough to complete the action
     */
    public static void checkAndCompare(Depot depot, Strongbox strongbox, Resource fromDepot, Resource fromStrongbox, Resource expectedRes) throws NegativeResAmountException, NotEnoughResException, CostNotMatchingException {
        //Check if the player really has those resources
        if (!depot.queryAllRes().compare(fromDepot) || !strongbox.queryAllRes().compare(fromStrongbox))
            throw new NotEnoughResException();

        //Sum up those resources and checks if they are enough to perform the action
        if (!fromDepot.sum(fromStrongbox).equals(expectedRes)) throw new CostNotMatchingException();
    }
}
