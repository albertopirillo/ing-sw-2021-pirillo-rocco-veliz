package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.*;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>Contains data and logic to buy a dev card from the Market</p>
 * <p>Part of the strategy pattern</p>
 */
public class DevCardsStrategy {

    /**
     * Array of the player's active discount leader abilities
     */
    private final Discount[] discounts;
    /**
     * Amount of active abilities
     */
    private int size;
    /**
     * Maximum amount of leader abilities
     */
    public static final int MAX = 2;

    /**
     * Initializes the array, leaving it empty for now
     */
    public DevCardsStrategy() {
        this.discounts = new Discount[MAX];
        this.size = 0;
    }

    /**
     * Returns the amount of leader abilities
     * @return an int representing the size
     */
    public int getSize() {
        return this.size;
    }

    /**
     * Adds a new discount ability to the player
     * @param ability   the ability to be added
     * @throws TooManyLeaderAbilitiesException if more than 2 abilities are already present
     */
    public void addAbility(Discount ability) throws TooManyLeaderAbilitiesException {
        if (size > MAX - 1) throw new TooManyLeaderAbilitiesException();
        this.discounts[size] = ability;
        size++;
    }

    /**
     * <p>Algorithm to buy a dev card from the market with a leader ability</p>
     * <p>Modifies the card's cost according to the abilities</p>
     * <p>Then calls BaseStrategies.buyDevCard()</p>
     * @param player the player that wants to perform the action
     * @param level the level of the card to be bought
     * @param color the color of the card to be bought
     * @param numSlot the number of the dev slot the player wants to put the card
     * @param choice identifies which abilities should be used
     * @param fromDepot resources paid from the depot
     * @param fromStrongbox resources paid from the strongbox
     * @throws DeckEmptyException   if the specified card doesnt exist
     * @throws NotEnoughResException if the player hasn't got the provided the resources
     * @throws CostNotMatchingException if the player hasn't provide the correct amount of resources
     * @throws NotEnoughSpaceException  if the selected dev slot is already full
     * @throws NoLeaderAbilitiesException   if the player has no leader abilities
     * @throws InvalidAbilityChoiceException    if the player hasn't got the selected ability
     * @throws InvalidNumSlotException  if the selected dev slot doesnt exist
     */
    public void buyDevCard(Player player, int level, CardColor color, int numSlot, AbilityChoice choice, Resource fromDepot, Resource fromStrongbox) throws DeckEmptyException, NegativeResAmountException, InvalidKeyException, NotEnoughResException, CannotContainFaithException, CostNotMatchingException, NotEnoughSpaceException, NoLeaderAbilitiesException, InvalidAbilityChoiceException, DevSlotEmptyException, InvalidNumSlotException {
        if (this.size == 0) throw new NoLeaderAbilitiesException();
        if (this.size == 1 && choice.ordinal() > 1) throw new InvalidAbilityChoiceException();

        Resource cost = player.getGame().getMarket().getCard(level, color).getCost();
        Map<ResourceType, Integer> discount = new HashMap<>();
        //Modify card's cost according to discounts
        switch (choice) {
            case FIRST:
                discount.put(this.discounts[0].getResource(), this.discounts[0].getAmount());
                break;
            case SECOND:
                discount.put(this.discounts[1].getResource(), this.discounts[1].getAmount());
                break;
            case BOTH:
                discount.put(this.discounts[0].getResource(), this.discounts[0].getAmount());
                discount.put(this.discounts[1].getResource(), this.discounts[1].getAmount());
                break;
        }
        for (ResourceType key: cost.keySet()) {
            if (discount.containsKey(key)){
                if (cost.getValue(key) - discount.get(key) > 0) cost.modifyValue(key, - discount.get(key));
                else cost.modifyValue(key, - cost.getValue(key));
            }
        }

        //Now the basic strategy can be used
        BasicStrategies.buyDevCard(player, level, color, numSlot, cost, fromDepot, fromStrongbox);
    }
}