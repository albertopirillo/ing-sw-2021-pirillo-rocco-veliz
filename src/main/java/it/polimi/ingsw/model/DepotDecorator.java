package it.polimi.ingsw.model;

public abstract class DepotDecorator extends Depot {

    protected Depot depot;

    public DepotDecorator(Depot depot) {
        this.depot = depot;
    }

    //No methods are overridden
}

