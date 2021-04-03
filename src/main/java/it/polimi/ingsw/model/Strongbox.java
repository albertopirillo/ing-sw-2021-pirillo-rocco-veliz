package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.*;

import java.util.*;

//Cannot contain Faith
public class Strongbox {

    private final Resource resource;

    public Strongbox() {
        this.resource = new Resource(0,0,0,0);
    }

    //Returns a copy of all the resources stored
    public Map<ResourceType, Integer> queryAllRes() {
        return new HashMap<>(resource.getAllRes());
    }

    public void addResources(Resource resource) throws CannotContainFaithException, NegativeResAmountException, InvalidKeyException {
        Map<ResourceType, Integer> copy = resource.getAllRes();
        if ((copy.get(ResourceType.FAITH) != null)) throw new CannotContainFaithException();

        for (ResourceType key: copy.keySet()) {
            this.resource.modifyValue(key, copy.get(key));
        }
    }

    public void retrieveResources(Resource resource) throws CannotContainFaithException, NotEnoughResException, NegativeResAmountException, InvalidKeyException {
        Map<ResourceType, Integer> toTake = resource.getAllRes();
        if ((toTake.get(ResourceType.FAITH) != null)) throw new CannotContainFaithException();

        if (!this.resource.compare(resource)) throw new NotEnoughResException();

        //removes the requested resources from the strongbox
        for (ResourceType key: toTake.keySet()) {
            this.resource.modifyValue(key, - toTake.get(key));
        }
    }

    //3x resources can be moved from Strongbox to Depot, at the cost of 1x resource
    public void moveToDepot(ResourceType resType, Depot depot, int layer) throws NegativeResAmountException, InvalidKeyException, CannotContainFaithException, InvalidLayerNumberException, LayerNotEmptyException, NotEnoughSpaceException, NotEnoughResException, AlreadyInAnotherLayerException {
        if (this.resource.getValue(resType) < 3) throw new NotEnoughResException();
        depot.modifyLayer(layer, resType, 2);
        this.resource.modifyValue(resType, -3);
    }

}