package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.network.DepotSetting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Depot implements Cloneable {

    /**
     * <p>Map used to query a layer by its number</p>
     * <p>Only one getter and one setter are needed this way</p>
     * <p>mapping cannot contain more than 5 layers</p>
     */
    private Map<Integer, Layer> mapping = new HashMap<>();

    /**
     * <p>Creates a standard 3 layers depot</p>
     * <p>Every layer has a different maximum amount</p>
     */
    public Depot() {
        Layer firstLayer = new DepotLayer(1);
        Layer secondLayer =new DepotLayer(2);
        Layer thirdLayer = new DepotLayer(3);
        mapping.put(1, firstLayer);
        mapping.put(2, secondLayer);
        mapping.put(3, thirdLayer);
    }

    /**
     * Returns a copy of the number-layer mapping
     * @return a copy of the internal map
     */
    protected Map<Integer, Layer> getMapCopy() {
        return new HashMap<>(mapping);
    }

    /**
     * Adds a layer to the mapping, used in subclasses
     * @param key   the number of the layer to add
     * @param depotLayer    the layer to add
     * @throws InvalidLayerNumberException  if more than 5 layers are already present
     */
    protected void addToMap(int key, Layer depotLayer) throws InvalidLayerNumberException {
        if (this.mapping.size() >= 5) throw new InvalidLayerNumberException();
        else this.mapping.put(key, depotLayer);
    }

    /**
     * Returns the layer with the corresponding number
     * @param layerNumber  the number of the layer
     * @return a Layer object representing the corresponding layer
     * @throws InvalidLayerNumberException if layerNumber isn't between 1 and 5
     */
    public Layer getLayer(int layerNumber) throws InvalidLayerNumberException {
        if (layerNumber < 0 || layerNumber > this.mapping.size()) throw new InvalidLayerNumberException();
        return mapping.get(layerNumber);
    }

    /**
     * Modifies the content of a layer, checking the others to see if that's valid
     * @param layerNumber   the layer to be modified
     * @param resType   the resource type to insert in that layer
     * @param addend    the amount of resource to add in that layer
     * @throws CannotContainFaithException  if faith is trying to be inserted
     * @throws LayerNotEmptyException   if trying to insert a different type of resource from the one present
     * @throws NotEnoughSpaceException  if the maximum amount of the layer is exceeded
     * @throws InvalidLayerNumberException  if layerNumber isn't between 1 and 5
     * @throws AlreadyInAnotherLayerException   if that resource type is already present in another layer
     * @throws InvalidResourceException if trying to insert an invalid resource type in a ExtraLayer
     */
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

    /**
     * Check if a List of DepotSetting can be inserted in the Depot
     * @param settings the list of the DepotSetting to check
     * @return  true if no exceptions will be thrown, false otherwise
     * @throws InvalidLayerNumberException if layerNumber isn't between 1 and 5
     */
    public boolean canInsertInLayer(List<DepotSetting> settings) throws InvalidLayerNumberException {
        for(DepotSetting setting: settings) {
            if (setting.getAmount() != 0) {
                Layer layer = this.getLayer(setting.getLayerNumber());
                    if (!layer.canInsert(setting.getResType(), setting.getAmount())) return false;
                }
            }
        return true;
    }

    /**
     * Moves resources from a depot layer to another
     * @param fromLayerNumber   the layer to take the resources from
     * @param toLayerNumber   the layer to put the resources in
     * @param amount    the amount of resources to move
     * @throws NotEnoughResException     if fromLayer doesnt contain the specified resources
     * @throws NotEnoughSpaceException  if toLayer's maximum amount is exceeded
     * @throws LayerNotEmptyException   if toLayer is not empty and a different type of resource is specified
     * @throws CannotContainFaithException  if faith is trying to be moved
     * @throws InvalidLayerNumberException   if layerNumber isn't between 1 and 5
     * @throws InvalidResourceException  if trying to insert an invalid resource type in a ExtraLayer
     */
    public void moveResources(int fromLayerNumber, int toLayerNumber, int amount) throws
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

    /**
     *  Returns a map with all the resources stored in the depot
     * @return  a Resource object with all the resources
     */
    public Resource queryAllRes() throws NegativeResAmountException {
        Resource res = new Resource(0, 0, 0, 0);
        for (Layer layer: mapping.values()) {
            ResourceType resType = layer.getResource();
            if (resType != null) res.modifyValue(resType, layer.getAmount());
        }
        return res;
    }

    /**
     * Removes the requested resources from the depot
     * @param res   the resources to be taken
     * @throws NotEnoughResException    if the depot doesnt contain that resources
     */
    public void retrieveRes(Resource res) throws NegativeResAmountException, NotEnoughResException, NotEnoughSpaceException {
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

    /**
     * <p>Discards the specified resources, giving one faithPoint for each resource to every other player</p>
     * <p>Resources can only be discarded when taken from the Market</p>
     * @param player    the player that wants to discard the resources
     * @param resource  the amount of resources to be discarded
     * @throws CannotContainFaithException  if faith is trying to be discarded
     */
    public void discardRes(Player player, Resource resource) throws CannotContainFaithException, NegativeResAmountException {
        if (resource.keySet().contains(ResourceType.FAITH))
            throw new CannotContainFaithException("You cannot discard faith points");

        List<Player> playerList = player.getGame().getPlayersList();
        for(Player p: playerList) {
            if (p != player) {
                p.addPlayerFaith(resource.getTotalAmount());
            }
        }
    }

    /**
     * Converts the content of the depot to a List of DepotSetting
     * @return  the corresponding list of DepotSetting
     */
    public List<DepotSetting> toDepotSetting() {
        List<DepotSetting> depotSettings = new ArrayList<>();
        int layerNum = 1;
        for(Layer layer: this.mapping.values()) {
            depotSettings.add(new DepotSetting(layerNum, layer.getResource(), layer.getAmount()));
            layerNum++;
        }
        return depotSettings;
    }

    /**
     * Resets the content of the depot and sets it from the given List of DepotSetting
     * @param settings  the list of settings to rebase the Depot from
     * @throws WrongDepotInstructionsException if incorrect or no instructions at all where provided
     */
    public void setFromDepotSetting(List<DepotSetting> settings) throws WrongDepotInstructionsException, CloneNotSupportedException {
        if (settings == null) throw new WrongDepotInstructionsException();
        //Clone the current depot
        Map<Integer, Layer> mapClone = new HashMap<>();
        for(Integer layerNum: this.mapping.keySet()) {
            mapClone.put(layerNum, this.mapping.get(layerNum).clone());
        }
        //Clear the depot
        for(Layer layer: this.mapping.values()) {
            layer.resetLayer();
        }
        //Store the new resources
        try {
            for (DepotSetting setting : settings) {
                this.modifyLayer(setting.getLayerNumber(), setting.getResType(), setting.getAmount());
            }
        } catch (InvalidResourceException | LayerNotEmptyException | NotEnoughSpaceException | InvalidLayerNumberException | CannotContainFaithException | AlreadyInAnotherLayerException | NegativeResAmountException e) {
            //Restore the old depot
            this.mapping = mapClone;
            throw new WrongDepotInstructionsException();
        }
    }
}