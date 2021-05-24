package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.CannotContainFaithException;
import it.polimi.ingsw.exceptions.InvalidKeyException;
import it.polimi.ingsw.exceptions.NegativeResAmountException;
import it.polimi.ingsw.exceptions.NotEnoughResException;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>Implements the strongbox, a special depot with no restrictions</p>
 * <p>In the strongbox you can store any number and type of Resources together.</p>
 * <p>Place the Resources received from the production in the Strongbox</p>
 * <p>The strongbox automatically converts Faith into playerFaithPoints</p>
 */
public class Strongbox {

    /**
     * The resources stored in the strongbox
     */
    private final Resource resource;
    /**
     * The temp resources stored in the strongbox at each turn
     */
    private Resource tempResource;
    /**
     * Reference to the player that owns the strongbox
     */
    private final Player player;

    /**
     * The only constructor that should be used
     * @param player    the player that owns the strongbox
     */
    public Strongbox(Player player) {
        this.resource = new Resource(0,0,0,0);
        this.tempResource = new Resource(0,0,0,0);
        this.player = player;
    }

    /**
     * Returns a copy of all the resources store in the strongbox
     * @return  a copy of all the resources stored in the strongbox
     */
    public Resource queryAllRes() {
        return new Resource(new HashMap<>(resource.getMap()));
    }

    /**
     * Returns a copy of all the temp resources store in the strongbox.
     * @return  a copy of all the temp resources stored in the strongbox
     */
    public Resource queryAllTempRes() {
        return new Resource(new HashMap<>(tempResource.getMap()));
    }

    /**
     * Resets the tempResource to its empty initial value.
     */
    public void resetTempRes(){
        tempResource = new Resource(0,0,0,0);
    }

    /**
     * Transfers the resources stored into the tempResource into the resource
     */
    public void transferTempRes() throws NegativeResAmountException, InvalidKeyException {
        Resource resource = queryAllTempRes();
        addResources(resource);
        resetTempRes();
    }


    /**
     * Adds resources into the strongbox, and converts faith automatically.
     * @param resource  the resources you want to add
     * @throws NegativeResAmountException   if a resource will end up with a negative value
     * @throws InvalidKeyException  if a resource different from faith or the storable ones is passed
     */
    public void addResources(Resource resource) throws NegativeResAmountException, InvalidKeyException {
        Map<ResourceType, Integer> copy = resource.getMap();

        for (ResourceType key: copy.keySet()) {
            if (key == ResourceType.FAITH) {
                this.player.addPlayerFaith(copy.get(key));
                this.player.getGame().updateFaithTrack();
            }
            else this.resource.modifyValue(key, copy.get(key));
        }
    }

    /**
     * Adds temp resources into the strongbox
     * @param resource  the resources you want to add as temp resources
     * @throws NegativeResAmountException  if a resource will end up with a negative value
     * @throws InvalidKeyException  if a resource different from faith or the storable ones is passed
     */
    public void addTempResources(Resource resource) throws InvalidKeyException, NegativeResAmountException {
        Map<ResourceType, Integer> copy = resource.getMap();
        for (ResourceType key: copy.keySet()) {
            this.tempResource.modifyValue(key, copy.get(key));
        }
    }

    /**
     * Take resource from the strongbox, to use them to e.g. buy Development Cards
     * @param resource  the resources you want to take from the strongbox
     * @throws CannotContainFaithException  if faith is trying to be taken
     * @throws NotEnoughResException    if the strongbox hasn't got the requested resources
     * @throws NegativeResAmountException   if a resource will end up with a negative value
     * @throws InvalidKeyException  if a resource different from faith or the storable ones is passed
     */
    public void retrieveRes(Resource resource) throws CannotContainFaithException, NotEnoughResException, NegativeResAmountException, InvalidKeyException {
        Map<ResourceType, Integer> toTake = resource.getMap();
        if ((toTake.get(ResourceType.FAITH) != null)) throw new CannotContainFaithException();

        if (!this.resource.compare(resource)) throw new NotEnoughResException();

        //removes the requested resources from the strongbox
        for (ResourceType key: toTake.keySet()) {
            this.resource.modifyValue(key, - toTake.get(key));
        }
    }
}