package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.*;

public class ExtraLayer implements Layer {

    private final ResourceType resource;
    private int amount;
    public final int MAX = 2;

    public ExtraLayer(ExtraSlot extraSlot) {
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
}

