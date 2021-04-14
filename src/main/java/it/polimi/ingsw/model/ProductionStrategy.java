package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.*;

public class ProductionStrategy {

    private final ExtraProduction[] productions;
    private int size;
    public static final int MAX = 2;

    public ProductionStrategy() {
        this.productions = new ExtraProduction[2];
        this.size = 0;
    }

    public int getSize() {
        return this.size;
    }

    public void addAbility(ExtraProduction ability) throws TooManyLeaderAbilitiesException {
        if (size > MAX - 1) throw new TooManyLeaderAbilitiesException();
        this.productions[size] = ability;
        size++;
    }

    public void extraProduction(Player player, AbilityChoice choice, Resource fromDepot, Resource fromStrongbox) throws NegativeResAmountException, InvalidKeyException, CannotContainFaithException, NoLeaderAbilitiesException, InvalidAbilityChoiceException, NotEnoughResException, CostNotMatchingException, NotEnoughSpaceException {
      if (this.size == 0) throw new NoLeaderAbilitiesException();
        if ((this.size == 1 && choice == AbilityChoice.SECOND) || (choice == AbilityChoice.BOTH))
            throw new InvalidAbilityChoiceException();

        Depot depot = player.getPersonalBoard().getDepot();
        Strongbox strongbox = player.getPersonalBoard().getStrongbox();

        //Note: FIRST.ordinal() == 1 and SECOND.ordinal() == 2
        Resource input = productions[choice.ordinal() - 1].getProduction().getInput();
        Resource output = productions[choice.ordinal() - 1].getProduction().getOutput();
        BasicStrategies.checkAndCompare(depot, strongbox, fromDepot, fromStrongbox, input);

        //Give the resources to the player
        player.getPersonalBoard().getStrongbox().addResources(output);

        //Remove those resources from the player
        depot.retrieveRes(fromDepot);
        strongbox.retrieveRes(fromStrongbox);
    }

}
