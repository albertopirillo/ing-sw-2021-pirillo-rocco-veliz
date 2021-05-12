package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.*;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.util.ArrayList;
import java.util.List;

public class ResourceStrategy {

    private final ChangeWhiteMarbles[] resTypes;
    private int size;
    public static final int MAX = 2;

    public ResourceStrategy() {
        this.resTypes = new ChangeWhiteMarbles[MAX];
        this.size = 0;
    }

    public List<ResourceType> getResType(){
        List<ResourceType> resourceTypes = new ArrayList<>();
        resourceTypes.add(resTypes[0].getResourceType());
        resourceTypes.add(resTypes[1].getResourceType());
        return resourceTypes;
    }

    public int getSize() {
        return this.size;
    }

    public void addAbility(ChangeWhiteMarbles ability) throws TooManyLeaderAbilitiesException {
        if (size > MAX - 1) throw new TooManyLeaderAbilitiesException();
        this.resTypes[size] = ability;
        size++;
    }

    public void changeWhiteMarbles(Player player, int amount1, int amount2, Resource toHandle) throws InvalidKeyException, NegativeResAmountException, NoLeaderAbilitiesException, CostNotMatchingException {
        if (this.size == 0) throw new NoLeaderAbilitiesException();
        try {
            toHandle.addResource(resTypes[0].getResourceType(), amount1);
        } catch (KeyAlreadyExistsException e) {
            toHandle.modifyValue(resTypes[0].getResourceType(), amount1);
        }
        try {
            toHandle.addResource(resTypes[1].getResourceType(), amount2);
        } catch (KeyAlreadyExistsException | InvalidKeyException | NegativeResAmountException e) {
            toHandle.modifyValue(resTypes[1].getResourceType(), amount2);
        }
        toHandle.removeResource(ResourceType.ALL);
    }

    //The player can decide whether to convert the color of every single marble
    public Resource insertMarble(Player player, int position) throws InvalidKeyException, NegativeResAmountException, NoLeaderAbilitiesException, InvalidAbilityChoiceException, CostNotMatchingException {
        if (this.size == 0) return BasicStrategies.insertMarble(player, position);

        Market market = player.getGame().getMarket();
        Marbles marbles = market.getMarketTray().insertMarble(position);

        //Selects the requested abilities and modifies the output resource
        Resource outputRes = marbles.getResources();
        Resource finalRes = new Resource();
        if (marbles.containWhiteMarbles()) {
            int amount1 = marbles.numWhiteMarbles();
            if (this.size == 1) {
                ResourceType modRes1 = resTypes[0].getResourceType();
                try {
                    outputRes.addResource(modRes1, amount1);
                } catch (KeyAlreadyExistsException e) {
                    outputRes.modifyValue(modRes1, amount1);
                }
            }
            if (this.size == 2) {
                try {
                    outputRes.addResource(ResourceType.ALL, amount1);
                } catch (KeyAlreadyExistsException e) {
                    outputRes.modifyValue(ResourceType.ALL, amount1);
                }
            }
        }

        //Handles faith and returns a map with no faith in it
        for (ResourceType key : outputRes.keySet()) {
            if (key == ResourceType.FAITH) player.addPlayerFaith(outputRes.getValue(key));
            else finalRes.addResource(key, outputRes.getValue(key));
        }
        return finalRes;
    }
}