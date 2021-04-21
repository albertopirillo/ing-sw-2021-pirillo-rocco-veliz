package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.*;

public interface Layer {

    boolean isEmpty();
    ResourceType getResource();
    int getAmount();
    void setAmount(int amount) throws NotEnoughSpaceException, NegativeResAmountException;
    void resetLayer();
    void setResAndAmount(ResourceType resource, int amount) throws NegativeResAmountException, NotEnoughSpaceException, InvalidResourceException, CannotContainFaithException, LayerNotEmptyException;
    boolean canInsert(ResourceType resource, int amount);
}
