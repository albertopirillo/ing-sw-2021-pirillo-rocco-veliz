package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.InvalidKeyException;
import it.polimi.ingsw.exceptions.NegativeResAmountException;
import it.polimi.ingsw.exceptions.NoLeaderAbilitiesException;
import it.polimi.ingsw.exceptions.TooManyLeaderAbilitiesException;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.util.ArrayList;
import java.util.List;

public class ResourceStrategy {

    /**
     * Array of the player's active change color leader abilities
     */
    private final ChangeWhiteMarbles[] resTypes;
    /**
     * Amount of the active abilities
     */
    private int size;
    /**
     * Maximum amount of leader abilities
     */
    public static final int MAX = 2;

    /**
     * Initializes the array, leaving it empty for now
     */
    public ResourceStrategy() {
        this.resTypes = new ChangeWhiteMarbles[MAX];
        this.size = 0;
    }

    /**
     * Returns all the resources the white marbles can be exchanged with
     * @return a list representing those resource types
     */
    public List<ResourceType> getResType(){
        List<ResourceType> resourceTypes = new ArrayList<>();
        resourceTypes.add(resTypes[0].getResourceType());
        resourceTypes.add(resTypes[1].getResourceType());
        return resourceTypes;
    }

    /**
     * Returns the amount of leader abilities
     * @return an int representing the size
     */
    public int getSize() {
        return this.size;
    }

    /**
     * Adds a new change color ability to the player
     * @param ability   the ability to be added
     * @throws TooManyLeaderAbilitiesException if more than 2 abilities are already present
     */
    public void addAbility(ChangeWhiteMarbles ability) throws TooManyLeaderAbilitiesException {
        if (size > MAX - 1) throw new TooManyLeaderAbilitiesException();
        this.resTypes[size] = ability;
        size++;
    }

    /**
     * Selects the resources the white marbles will be exchange with
     * @param player    the player that wants to perform the action
     * @param amount1   the amount of white marbles to exchange using the first ability
     * @param amount2   the amount of white marbles to exchange using the second ability
     * @param toHandle  where the exchanged resources will be placed
     * @throws NoLeaderAbilitiesException if the player has no leader abilities
     */
    public void changeWhiteMarbles(Player player, int amount1, int amount2, Resource toHandle) throws InvalidKeyException, NegativeResAmountException, NoLeaderAbilitiesException {
        if (this.size == 0) throw new NoLeaderAbilitiesException();
        try {
            toHandle.addResource(resTypes[0].getResourceType(), amount1);
        } catch (KeyAlreadyExistsException e) {
            toHandle.modifyValue(resTypes[0].getResourceType(), amount1);
        }
        try {
            toHandle.addResource(resTypes[1].getResourceType(), amount2);
        } catch (KeyAlreadyExistsException | NegativeResAmountException e) {
            toHandle.modifyValue(resTypes[1].getResourceType(), amount2);
        }
        toHandle.removeResource(ResourceType.ALL);
    }

    /**
     * <p>Algorithm to take resources from the market</p>
     * <p>Automatically handles faith</p>
     * @param player    the player that wants to perform the action
     * @param position  the position of the grid where the remaining marble should be inserted
     * @return  the resources that need to go in TempResource
     */
    public Resource insertMarble(Player player, int position) throws InvalidKeyException, NegativeResAmountException {
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