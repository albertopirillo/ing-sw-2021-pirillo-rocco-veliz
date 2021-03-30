package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.*;

import java.util.HashMap;
import java.util.Map;

//Attribute "mapping" is used to give the user the possibility to specify a
//level by its number instead of its full name
//This way only one getter and one setter are needed
public abstract class Depot {

    private final static Map<Integer, DepotLayer> mapping = new HashMap<>();

    public Depot() {
        DepotLayer firstLayer = new DepotLayer(1);
        DepotLayer secondLayer = new DepotLayer(2);
        DepotLayer thirdLayer = new DepotLayer(3);
        mapping.put(1, firstLayer);
        mapping.put(2, secondLayer);
        mapping.put(3, thirdLayer);
    }

    public DepotLayer getLayer(int layerNumber) throws InvalidLayerNumberException {
        if (layerNumber < 0 || layerNumber > 3) throw new InvalidLayerNumberException();
        return mapping.get(layerNumber);
    }

    public void modifyLayer(int layerNumber, ResourceType resource, int addend) throws
            NegativeResAmountException, CannotContainFaithException, LayerNotEmptyException, NotEnoughSpaceException, InvalidLayerNumberException {
        DepotLayer layer = this.getLayer(layerNumber);
        layer.setResource(resource);
        layer.setAmount(layer.getAmount() + addend);
    }

    public void moveResources(int amount, int fromLayerNumber, int toLayerNumber) throws
            NegativeResAmountException, NotEnoughResException, NotEnoughSpaceException, LayerNotEmptyException, CannotContainFaithException, InvalidLayerNumberException {
        DepotLayer fromLayer = this.getLayer(fromLayerNumber);
        DepotLayer toLayer = this.getLayer(toLayerNumber);

        if (amount == 0) return;
        if (amount < 0) throw new NegativeResAmountException();
        if (amount > fromLayer.getAmount()) throw new NotEnoughResException();

        if (toLayer.isEmpty() || fromLayer.getResource() == toLayer.getResource())
            this.moveToLayer(amount, fromLayer, toLayer);
        else this.swapLayers(fromLayer, toLayer);

    }

    private void moveToLayer(int amount, DepotLayer fromLayer, DepotLayer toLayer) throws
            LayerNotEmptyException, CannotContainFaithException, NotEnoughSpaceException, NegativeResAmountException {
        toLayer.setResource(fromLayer.getResource());
        toLayer.setAmount(toLayer.getAmount() + amount);
        fromLayer.resetLayer();
    }

    private void swapLayers(DepotLayer fromLayer, DepotLayer toLayer) throws
            NegativeResAmountException, CannotContainFaithException, LayerNotEmptyException, NotEnoughSpaceException {

        //builds a new temporary DepotLayer object, identical to toLayer
        DepotLayer tempLayer = new DepotLayer(3);
        tempLayer.setResource(toLayer.getResource());
        tempLayer.setAmount(toLayer.getAmount());
        toLayer.resetLayer();

        //copies fromLayer into toLayer
        toLayer.setResource(fromLayer.getResource());
        toLayer.setAmount(fromLayer.getAmount());
        fromLayer.resetLayer();

        //copies tempLayer into fromLayer
        fromLayer.setResource(tempLayer.getResource());
        fromLayer.setAmount(tempLayer.getAmount());
    }

    //Returns a map with all the resources stored in the depot
    public Resource getAllResources() throws NegativeResAmountException, IllegalKeyException {
        Resource res = new Resource(0, 0, 0, 0);
        for (DepotLayer layer: mapping.values()) {
            ResourceType resType = layer.getResource();
            if (resType != null) res.modifyValue(resType, layer.getAmount());
        }
        return res;
    }

}