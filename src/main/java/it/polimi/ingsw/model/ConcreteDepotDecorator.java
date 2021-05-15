package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.InvalidLayerNumberException;

import java.util.Map;

/**
 * Depot decorator that adds one more slot, thanks to a leader ability
 */
public class ConcreteDepotDecorator extends DepotDecorator {

    //private final Map<Integer, DepotLayer> mapping = new HashMap<>();
    //protected Depot depot <== inherited from the DepotDecorator, has direct access

    /**
     * Adds another layer to the generic decorator mapping
     * @param depot the depot to decorate
     * @param extraSlot the slot to add
     * @throws InvalidLayerNumberException  if the maximum number of layers (5) is already reached
     */
    public ConcreteDepotDecorator(Depot depot, ExtraSlot extraSlot) throws InvalidLayerNumberException {
        super(depot);
        Map<Integer, Layer> copy = this.depot.getMapCopy();
        for (int key: copy.keySet()) {
            this.addToMap(key, copy.get(key));
        }
        this.addToMap(copy.size() + 1, new ExtraLayer(extraSlot));
    }
}