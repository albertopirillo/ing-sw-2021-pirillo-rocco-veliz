package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.InvalidResourceException;
import it.polimi.ingsw.exceptions.NegativeResAmountException;
import it.polimi.ingsw.exceptions.NotEnoughSpaceException;

/**
 * <p>Layer implementation of the slot added by a leader ability</p>
 * <p>Only the specified resources from the leader ability can be inserted</p>
 * <p>A max of two resources can be inserted in every layer</p>
 */
public class ExtraLayer extends Layer  {

    /**
     * The only type of resource that can be inserted in the layer
     */
    private final ResourceType resource;
    /**
     * The amount of that resource currently present in the layer
     */
    private int amount;
    /**
     * The maximum amount of resource that it can contain
     */
    public final int MAX = 2;

    /**
     * Creates a new empty layer from a leader ability
     * @param extraSlot the leader ability to create the layer from
     */
    public ExtraLayer(ExtraSlotAbility extraSlot) {
        this.resource = extraSlot.getResource();
        this.amount = 0;
    }

    @Override
    public boolean isEmpty() {
        return (this.amount == 0);
    }

    @Override
    public ResourceType getResource() {
        return resource;
    }

    @Override
    public int getAmount() {
        return amount;
    }

    @Override
    public void setAmount(int amount) throws NotEnoughSpaceException, NegativeResAmountException {
        if (amount < 0) throw new NegativeResAmountException();
        if (amount > MAX) throw new NotEnoughSpaceException();
        this.amount = amount;
    }

    @Override
    public void resetLayer() {
        this.amount = 0;
    }

    //This method should be the only one used to modify a layer's ResourceType, because it checks every exception
    @Override
    public void setResAndAmount(ResourceType resource, int amount) throws NegativeResAmountException, NotEnoughSpaceException, InvalidResourceException {
        if (this.resource != resource) throw new InvalidResourceException();
        else this.setAmount(amount);
    }

    @Override
    public boolean canInsert(ResourceType resource, int amount) {
        return (amount >= 0) && (amount <= MAX) && (this.resource == resource);
    }
}

