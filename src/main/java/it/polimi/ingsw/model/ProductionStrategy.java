package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.CannotContainFaithException;
import it.polimi.ingsw.exceptions.InvalidKeyException;
import it.polimi.ingsw.exceptions.NegativeResAmountException;

public class ProductionStrategy extends BaseProductionStrategy {

    private final ExtraProduction[] production;

    public ProductionStrategy(ExtraProduction[] production) {
        this.production = production;
    }

    //Input resources can be paid from either Depot or Strongbox
    //Output resources have to got in the Strongbox
    @Override
    public void extraProduction(int choice, Player player) throws NegativeResAmountException, InvalidKeyException, CannotContainFaithException {
        Resource input = production[choice].getProduction().getInput();
        Resource output = production[choice].getProduction().getOutput();
        if (player.getAllResources().compare(input)) {
            //TODO: removes those resources from player
            player.getPersonalBoard().getStrongbox().addResources(output);
            //TODO: how is faith handled?
        }
    }

}