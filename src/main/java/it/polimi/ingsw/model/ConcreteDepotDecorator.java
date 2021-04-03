package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.InvalidLayerNumberException;

import java.util.Map;

public class ConcreteDepotDecorator extends DepotDecorator {

    //inherited from Depot, but cannot access directly because it's private:
    //private final Map<Integer, DepotLayer> mapping = new HashMap<>();

    //protected Depot depot <== inherited from the DepotDecorator, has direct access

    //The builder just adds another element to the superclass' mapping
    public ConcreteDepotDecorator(Depot depot) throws InvalidLayerNumberException {
        super(depot);
        Map<Integer, DepotLayer> copy = this.depot.getMapCopy();
        for (int key: copy.keySet()) {
            this.addToMap(key, copy.get(key));
        }
        this.addToMap(copy.size() + 1, new DepotLayer(2));
    }
}