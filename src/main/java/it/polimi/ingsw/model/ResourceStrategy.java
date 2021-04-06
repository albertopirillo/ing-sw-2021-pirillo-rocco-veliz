package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.InvalidChoiceException;
import it.polimi.ingsw.exceptions.InvalidKeyException;
import it.polimi.ingsw.exceptions.NegativeResAmountException;

public class ResourceStrategy extends BaseResourceStrategy {

    private ChangeWhiteMarbles[] resType;

    public ResourceStrategy() {
        resType = new ChangeWhiteMarbles[2];
    }

    @Override
    public Resource takeResources(int position, Market market, boolean standard, int choice) throws InvalidKeyException, NegativeResAmountException, InvalidChoiceException {
        if (choice <= 0 || choice > 2) throw new InvalidChoiceException();
        Resource resource;
        if (standard) resource = super.takeResources(position, market, true, choice);
        else {
            Marbles marbles = market.getMarketTray().insertMarble(position);
            resource = marbles.getResources();
            int whiteMarbles = marbles.getValue(MarblesColor.WHITE);
            resource.addResource(resType[choice].getResourceType(), whiteMarbles);
        }
        return resource;
    }
}