package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.*;

import java.io.Serializable;

/**
 * <p>Generic structure of a generic depot's layer</p>
 * <p>Layers cannot contain Faith</p>
 */
public abstract class Layer implements Serializable {

    /**
     * Check if the layer is empty or not
     * @return true if no resources are present, false otherwise
     */
    public abstract boolean isEmpty();

    /**
     * Get the type of resource currently stored
     * @return the resource type
     */
    public abstract ResourceType getResource();

    /**
     * Get the amount of resources that is currently stored
     * @return the resource amount
     */
    public abstract int getAmount();

    /**
     * Testing only: set the amount of resources stored in the layer
     * @param amount    the new amount of resources
     * @throws NotEnoughSpaceException  if the maximum amount of the layer is exceeded
     * @throws NegativeResAmountException   if a negative value is specified
     */
    public abstract void setAmount(int amount) throws NotEnoughSpaceException, NegativeResAmountException;

    /**
     * Reset a layer's content, both for the resource type and the amount
     */
    public abstract void resetLayer();

    /**
     * Modifies a layer resource type and amount, checking every exception first
     * @param resource  the new resource type
     * @param amount    the new resource amount
     * @throws CannotContainFaithException  if faith is trying to be stored
     * @throws LayerNotEmptyException   if the layer isn't empty
     * @throws NegativeResAmountException   if a negative amount is specified
     * @throws NotEnoughSpaceException  if the maximum amount of the layer is exceeded
     */
    public abstract void setResAndAmount(ResourceType resource, int amount) throws NegativeResAmountException, NotEnoughSpaceException, InvalidResourceException, CannotContainFaithException, LayerNotEmptyException;

    /**
     * Check if the given resource type and resource amount can be inserted in the current layer
     * @param resource  the resource type
     * @param amount    the resource amount
     * @return  true if it can be done, false otherwise
     */
    public abstract boolean canInsert(ResourceType resource, int amount);
}
