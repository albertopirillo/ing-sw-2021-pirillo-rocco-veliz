package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.*;

import java.util.HashMap;
import java.util.Map;

public class DevCardsStrategy {

    private final Discount[] discounts;
    private int size;
    public static final int MAX = 2;

    public DevCardsStrategy() {
        this.discounts = new Discount[MAX];
        this.size = 0;
    }

    public int getSize() {
        return this.size;
    }

    public void addAbility(Discount ability) throws TooManyLeaderAbilitiesException {
        if (size > MAX - 1) throw new TooManyLeaderAbilitiesException();
        this.discounts[size] = ability;
        size++;
    }

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