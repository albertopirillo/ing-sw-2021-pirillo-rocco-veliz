package it.polimi.ingsw.model;

import java.util.*;

public abstract class DepotDecorator extends Depot {

    public DepotDecorator() {
    }

    protected Depot depot;



    public Resource getFirstLayer() {
        // TODO implement here
        return null;
    }

    public Resource getSecondLayer() {
        // TODO implement here
        return null;
    }

    public Resource getThirdLayer() {
        // TODO implement here
        return null;
    }

    public void setFirstLayer(Resource resource) {
        // TODO implement here
    }

    public void setSecondLayer(Resource resource) {
        // TODO implement here
    }

    public void setThirdLayer(Resource resource) {
        // TODO implement here
    }

    private void rearrangeResources() {
        // TODO implement here
    }

}