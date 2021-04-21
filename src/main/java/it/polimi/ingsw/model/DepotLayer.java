package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.CannotContainFaithException;
import it.polimi.ingsw.exceptions.LayerNotEmptyException;
import it.polimi.ingsw.exceptions.NegativeResAmountException;
import it.polimi.ingsw.exceptions.NotEnoughSpaceException;

//DepotLayer cannot contain ResourceType.FAITH
public class DepotLayer implements Layer {

    private ResourceType resource;
    private int amount;
    public final int MAX;

    public DepotLayer(int MAX) {
        this.resource = null;
        this.amount = 0;
        this.MAX = MAX;
    }

    @Override
    public boolean isEmpty() {
        return (this.resource == null && this.amount == 0);
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
        if (amount == 0) this.resource = null;
    }

    @Override
    public void resetLayer() {
        this.resource = null;
        this.amount = 0;
    }

    //This method should be the only one used to modify a layer's ResourceType, because it checks every exception
    @Override
    public void setResAndAmount(ResourceType resource, int amount) throws CannotContainFaithException, LayerNotEmptyException, NegativeResAmountException, NotEnoughSpaceException {
        if (resource == ResourceType.FAITH) throw new CannotContainFaithException();
        if (amount < 0) throw new NegativeResAmountException();
        if (amount > MAX) throw new NotEnoughSpaceException();
        if (this.resource != null && this.resource != resource) throw new LayerNotEmptyException();

        this.amount = amount;
        if (amount == 0) this.resource = null;
        else this.resource = resource;
    }

    @Override
    public boolean canInsert(ResourceType resource, int amount) {
        return (resource != ResourceType.FAITH) && (amount >= 0) && (amount <= MAX) &&
                (this.resource == null || this.resource == resource);
    }
}
