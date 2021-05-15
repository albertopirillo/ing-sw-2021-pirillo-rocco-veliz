package it.polimi.ingsw.model;

/**
 * <p>Generic depot decorator, adds no functionalities</p>
 * <p>Should not override anything from the superclass</p>
 */
public abstract class DepotDecorator extends Depot {

    /**
     * The depot to be decorated
     */
    protected Depot depot;

    /**
     * Takes a depot and applies a generic decorator
     * @param depot the depot to be decorated
     */
    public DepotDecorator(Depot depot) {
        this.depot = depot;
    }
}

