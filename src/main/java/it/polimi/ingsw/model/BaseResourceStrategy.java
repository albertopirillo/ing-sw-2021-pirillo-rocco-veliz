package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.InvalidChoiceException;
import it.polimi.ingsw.exceptions.InvalidKeyException;
import it.polimi.ingsw.exceptions.NegativeResAmountException;

public class BaseResourceStrategy {

    public ChangeWhiteMarbles[] getResType() {
        return null;
    }

    public void addAbility(ResourceStrategy ability){

    }

    public Resource takeResources(int position, Market market, boolean standard, int choice) throws InvalidKeyException, NegativeResAmountException, InvalidChoiceException {
        Marbles marbles = market.getMarketTray().insertMarble(position);
        return marbles.getResources();
    }
}
