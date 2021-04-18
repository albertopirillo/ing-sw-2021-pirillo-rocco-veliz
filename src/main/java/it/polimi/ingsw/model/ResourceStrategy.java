package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.*;

import javax.management.openmbean.KeyAlreadyExistsException;

public class ResourceStrategy {

    private final ChangeWhiteMarbles[] resTypes;
    private int size;
    public static final int MAX = 2;

    public ResourceStrategy() {
      this.resTypes = new ChangeWhiteMarbles[MAX];
      this.size = 0;
    }

    public int getSize() {
        return this.size;
    }

    public void addAbility(ChangeWhiteMarbles ability) throws TooManyLeaderAbilitiesException {
        if (size > MAX - 1) throw new TooManyLeaderAbilitiesException();
        this.resTypes[size] = ability;
        size++;
    }

    //The player can decide whether to convert the color of every single marble
    public Resource takeResources(Player player, int position, AbilityChoice choice, int amount1, int amount2) throws InvalidKeyException, NegativeResAmountException, NoLeaderAbilitiesException, InvalidAbilityChoiceException {
        if (this.size == 0) throw new NoLeaderAbilitiesException();
        if ((this.size == 1 && (choice == AbilityChoice.SECOND || choice == AbilityChoice.BOTH)))
            throw new InvalidAbilityChoiceException();

        //Check if amount1+amount2 is equal to the number of white marbles
        Market market = player.getGame().getMarket();
        Marbles marbles = market.getMarketTray().insertMarble(position);
        if (amount1 + amount2 != marbles.getValue(MarblesColor.WHITE)) throw new InvalidAbilityChoiceException();

        //Selects the requested abilities and modifies the output resource
        Resource outputRes = marbles.getResources();
        Resource finalRes = new Resource();
        if (choice == AbilityChoice.FIRST || choice == AbilityChoice.BOTH) {
            ResourceType modRes1 = resTypes[0].getResourceType();
             try {
                 outputRes.addResource(modRes1, amount1);
             } catch (KeyAlreadyExistsException e) {
                 outputRes.modifyValue(modRes1, amount1);
             }
        }
        if (choice == AbilityChoice.SECOND || choice == AbilityChoice.BOTH) {
            ResourceType modRes2 = resTypes[1].getResourceType();
            outputRes.modifyValue(modRes2, amount2);
            try {
                outputRes.addResource(modRes2, amount2);
            } catch (KeyAlreadyExistsException e) {
                outputRes.modifyValue(modRes2, amount2);
            }
        }
        //Handles faith and returns a map with no faith in it
        for (ResourceType key: outputRes.keySet()) {
            if (key == ResourceType.FAITH) player.addPlayerFaith(outputRes.getValue(key));
            else finalRes.addResource(key, outputRes.getValue(key));
        }
        return finalRes;
    }
}