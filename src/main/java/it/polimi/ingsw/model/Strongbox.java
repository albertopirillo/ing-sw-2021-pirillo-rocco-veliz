package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.CannotContainFaithException;
import it.polimi.ingsw.exceptions.InvalidKeyException;
import it.polimi.ingsw.exceptions.NegativeResAmountException;
import it.polimi.ingsw.exceptions.NotEnoughResException;

import java.util.HashMap;
import java.util.Map;

//Automatically converts faith Resource into playerFaithPoints
public class Strongbox {

    private final Resource resource;
    private final Player player;

    public Strongbox(Player player) {
        this.resource = new Resource(0,0,0,0);
        this.player = player;
    }

    //Returns a copy of all the resources stored
    public Resource queryAllRes() {
        return new Resource(new HashMap<>(resource.getMap()));
    }

    public void addResources(Resource resource) throws NegativeResAmountException, InvalidKeyException {
        Map<ResourceType, Integer> copy = resource.getMap();

        for (ResourceType key: copy.keySet()) {
            if (key == ResourceType.FAITH) this.player.addPlayerFaith(copy.get(key));
            else this.resource.modifyValue(key, copy.get(key));
        }
    }

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