package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.*;

/**
 * <p>Contains data and logic to activate a leader card's production</p>
 * <p>Part of the strategy pattern</p>
 */
public class ProductionStrategy {

    /**
     * Array of the player's active extra production abilities
     */
    private final ExtraProductionAbility[] productions;
    /**
     * Amount of active leader abilities
     */
    private int size;
    /**
     * Maximum amount of leader abilities
     */
    public static final int MAX = 2;

    /**
     * Initializes the array, leaving it empty for now
     */
    public ProductionStrategy() {
        this.productions = new ExtraProductionAbility[2];
        this.size = 0;
    }

    /**
     * Returns the amount of leader abilities
     * @return  an int representing the size
     */
    public int getSize() {
        return this.size;
    }

    /**
     * Adds a new extra production ability yo the player
     * @param ability   the ability to be added
     * @throws TooManyLeaderAbilitiesException if more than 2 abilities are already present
     */
    public void addAbility(ExtraProductionAbility ability) throws TooManyLeaderAbilitiesException {
        if (size > MAX - 1) throw new TooManyLeaderAbilitiesException();
        this.productions[size] = ability;
        size++;
    }

    /**
     * <p>Algorithm to activate a leader ability extra production</p>
     * <p>Every ability gives one faith and one resource of choice</p>
     * @param player    the player that wants to perform the action
     * @param choice    identifies which ability should be used
     * @param fromDepot resources paid from the depot
     * @param fromStrongbox resources paid from the strongbox
     * @param res   the resource type the player wants to receive
     * @throws NoLeaderAbilitiesException  if the player has no leader abilities
     * @throws InvalidAbilityChoiceException  the player hasn't got the selected ability
     * @throws NotEnoughResException if the player hasn't got the provided the resources
     * @throws CostNotMatchingException  the player hasn't provide the correct amount of resources
     */
    public void extraProduction(Player player, AbilityChoice choice, Resource fromDepot, Resource fromStrongbox, ResourceType res) throws NegativeResAmountException, CannotContainFaithException, NoLeaderAbilitiesException, InvalidAbilityChoiceException, NotEnoughResException, CostNotMatchingException, NotEnoughSpaceException {
      if (this.size == 0) throw new NoLeaderAbilitiesException();
        if ((this.size == 1 && choice == AbilityChoice.SECOND) || (choice == AbilityChoice.BOTH))
            throw new InvalidAbilityChoiceException();

        Depot depot = player.getPersonalBoard().getDepot();
        Strongbox strongbox = player.getPersonalBoard().getStrongbox();

        //Note: FIRST.ordinal() == 1 and SECOND.ordinal() == 2
        Resource input = productions[choice.ordinal() - 1].getProduction().getInput();
        Resource out = productions[choice.ordinal() - 1].getProduction().getOutput();
        BasicStrategies.checkAndCompare(depot, strongbox, fromDepot, fromStrongbox, input);
        Resource output = new Resource(out.getMap());
        output.removeResource(ResourceType.ALL);
        output.modifyValue(res, 1);

        //Give the resources to the player
        player.getPersonalBoard().getStrongbox().addTempResources(output);

        //Remove those resources from the player
        depot.retrieveRes(fromDepot);
        strongbox.retrieveRes(fromStrongbox);
    }

}
