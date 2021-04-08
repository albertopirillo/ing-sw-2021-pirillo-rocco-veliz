package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.CannotContainFaithException;
import it.polimi.ingsw.exceptions.InvalidKeyException;
import it.polimi.ingsw.exceptions.NegativeResAmountException;
import it.polimi.ingsw.exceptions.NoExtraProductionsException;

public class BaseProductionStrategy {

    //By default, the player has no ExtraProduction abilities available
    public void extraProduction(int choice, Player player) throws NoExtraProductionsException, NegativeResAmountException, InvalidKeyException, CannotContainFaithException {
        throw new NoExtraProductionsException();
    }

    public void addAbility(ProductionStrategy ability) {
    }

    public ExtraProduction[] getProduction() {
        return null;
    }
}
