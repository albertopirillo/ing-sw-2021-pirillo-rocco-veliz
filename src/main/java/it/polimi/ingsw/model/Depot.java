package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.*;

import java.util.HashMap;
import java.util.Map;

//Attribute "mapping" is used to give the user the possibility to specify a
//level by its number instead of its full name
//This way only one getter and one setter are needed
public abstract class Depot {

    private final Map<Integer, Layer> mapping = new HashMap<>();

    public Depot() {
        Layer firstLayer = new DepotLayer(1);
        Layer secondLayer =new DepotLayer(2);
        Layer thirdLayer = new DepotLayer(3);
        mapping.put(1, firstLayer);
        mapping.put(2, secondLayer);
        mapping.put(3, thirdLayer);
    }

    //Returns a copy of mapping field
    protected Map<Integer, Layer> getMapCopy() {
        return new HashMap<>(mapping);
    }

    protected void addToMap(int key, Layer depotLayer) throws InvalidLayerNumberException {
        if (this.mapping.size() >= 5) throw new InvalidLayerNumberException();
        else this.mapping.put(key, depotLayer);
    }

    public Layer getLayer(int layerNumber) throws InvalidLayerNumberException {
        //cannot create a Depot with more than 5 layers
        if (layerNumber < 0 || layerNumber > this.mapping.size()) throw new InvalidLayerNumberException();
        return mapping.get(layerNumber);
    }

    public void modifyLayer(int layerNumber, ResourceType resType, int addend) throws
            NegativeResAmountException, CannotContainFaithException, LayerNotEmptyException, NotEnoughSpaceException, InvalidLayerNumberException, AlreadyInAnotherLayerException, InvalidResourceException {
        if (addend == 0 ) return;

        //need to check that no other layers already contain that ResourceType
        if (layerNumber <= 3) {
            for (int i = 1; i <= 3; i++) {
                if (i != layerNumber)
                    if (this.getLayer(i).getResource() == resType) throw new AlreadyInAnotherLayerException();
            }
        }
        Layer layer = this.getLayer(layerNumber);
        layer.setResAndAmount(resType, layer.getAmount() + addend);
    }

    public void moveResources(int amount, int fromLayerNumber, int toLayerNumber) throws
            NegativeResAmountException, NotEnoughResException, NotEnoughSpaceException, LayerNotEmptyException, CannotContainFaithException, InvalidLayerNumberException, InvalidResourceException {
        if (amount == 0) return;
        Layer fromLayer = this.getLayer(fromLayerNumber);
        if (amount > fromLayer.getAmount()) throw new NotEnoughResException();
        Layer toLayer = this.getLayer(toLayerNumber);

        if (toLayer.isEmpty() || fromLayer.getResource() == toLayer.getResource())
            this.moveToLayer(amount, fromLayer, toLayer);
        else this.swapLayers(fromLayer, toLayer);
    }

    private void moveToLayer(int amount, Layer fromLayer, Layer toLayer) throws
            LayerNotEmptyException, CannotContainFaithException, NotEnoughSpaceException, NegativeResAmountException, InvalidResourceException {
        toLayer.setResAndAmount(fromLayer.getResource(), toLayer.getAmount() + amount);
        fromLayer.setAmount(fromLayer.getAmount() - amount);
    }

    private void swapLayers(Layer fromLayer, Layer toLayer) throws
            NegativeResAmountException, CannotContainFaithException, LayerNotEmptyException, NotEnoughSpaceException, InvalidResourceException {

        //builds a new temporary DepotLayer object, identical to toLayer
        DepotLayer tempLayer = new DepotLayer(3);
        tempLayer.setResAndAmount(toLayer.getResource(), toLayer.getAmount());
        toLayer.resetLayer();

        //copies fromLayer into toLayer
        toLayer.setResAndAmount(fromLayer.getResource(), fromLayer.getAmount());
        fromLayer.resetLayer();

        //copies tempLayer into fromLayer
        fromLayer.setResAndAmount(tempLayer.getResource(), tempLayer.getAmount());
    }

    //Returns a map with all the resources stored in the depot
    public Resource queryAllRes() throws NegativeResAmountException, InvalidKeyException {
        Resource res = new Resource(0, 0, 0, 0);
        for (Layer layer: mapping.values()) {
            ResourceType resType = layer.getResource();
            if (resType != null) res.modifyValue(resType, layer.getAmount());
        }
        return res;
    }

    public void retrieveRes(Resource res) throws NegativeResAmountException, InvalidKeyException, NotEnoughResException, NotEnoughSpaceException {
        if (!this.queryAllRes().compare(res)) throw new NotEnoughResException();
        Map<ResourceType, Integer> toTake = res.getMap();

        //retrieves that resources
        for (Layer layer : this.mapping.values()) {
            for (ResourceType resType : toTake.keySet()) {
                if ((toTake.get(resType) != 0) && (layer.getResource() == resType)) {
                    int available = layer.getAmount();
                    int needed = toTake.get(resType);
                    if (available >= needed) {
                        toTake.put(resType, 0);
                        layer.setAmount(available - needed);
                    } else {
                        toTake.put(resType, needed - available);
                        layer.resetLayer();
                    }
                }
            }
        }
    }

    //A resource can be discarded from tge Depot, but all other players will receive a Faith Point
    public void discardRes(DepotLayer layer) {
        //TODO implement here
    }
}