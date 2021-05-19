package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.exceptions.InvalidKeyException;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.ResourceType;
import it.polimi.ingsw.network.DepotSetting;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PersonalBoardController {

    @FXML
    ImageView depot1_1, depot2_1, depot2_2, depot3_1, depot3_2, depot3_3;
    @FXML
    ImageView extra1_1, extra1_2, extra2_1, extra2_2;
    @FXML
    Label sb_stone, sb_servant, sb_shield, sb_coin;

    /**
     * A map to get a list of all the corresponding images from a given layer
     */
    private final Map<Integer, List<ImageView>> layerMapping = new HashMap<>();

    /**
     * Initializes @FXML fields, organizing them in multiple lists and then in a single map
     */
    public void init() {
        List<ImageView> firstLayer = new ArrayList<>();
        firstLayer.add(depot1_1);
        this.layerMapping.put(1, firstLayer);
        List<ImageView> secondLayer = new ArrayList<>();
        secondLayer.add(depot2_1);
        secondLayer.add(depot2_2);
        this.layerMapping.put(2, secondLayer);
        List<ImageView> thirdLayer = new ArrayList<>();
        thirdLayer.add(depot3_1);
        thirdLayer.add(depot3_2);
        thirdLayer.add(depot3_3);
        this.layerMapping.put(3, thirdLayer);
        List<ImageView> firstExtraLayer = new ArrayList<>();
        firstExtraLayer.add(extra1_1);
        firstExtraLayer.add(extra1_2);
        this.layerMapping.put(4, firstExtraLayer);
        List<ImageView> secondExtraLayer = new ArrayList<>();
        secondExtraLayer.add(extra2_1);
        secondExtraLayer.add(extra2_2);
        this.layerMapping.put(5, secondExtraLayer);
    }

    /**
     * Updates the strongbox
     * @param resource the resources to update the strongbox with
     */
    public void setStrongbox(Resource resource) {
        for(ResourceType key: resource.keySet()) {
            try {
                switch (key) {
                    case STONE: sb_stone.setText("x" + resource.getValue(key)); break;
                    case SERVANT: sb_servant.setText("x" + resource.getValue(key)); break;
                    case SHIELD: sb_shield.setText("x" + resource.getValue(key)); break;
                    case COIN: sb_coin.setText("x" + resource.getValue(key)); break;
                    default: break;
                }
            } catch (InvalidKeyException e) {
                System.out.println("Invalid key");
            }
        }
    }

    /**
     * Updates a single Depot layer
     * @param layerNumber   the number of the layer
     * @param res   the resource type to be stored in the layer
     * @param amount    the amount of resources to be stored in the layer
     */
    public void setLayer(int layerNumber, ResourceType res, int amount) {
        int i = 0;
        List<ImageView> layer = this.layerMapping.get(layerNumber);
        for (ImageView slot: layer) {
            if (i < amount) {
                slot.setImage(Util.getResImage(res));
                i++;
            }
            else {
                slot.setImage(null);
            }
        }
    }

    /**
     * Update the whole Depot using a list of DepotSetting
     * @param settings  a list of DepotSetting with instructions to update the Depot
     */
    public void setDepot(List<DepotSetting> settings) {
        for(DepotSetting setting: settings) {
            setLayer(setting.getLayerNumber(), setting.getResType(), setting.getAmount());
        }
    }

    public void dragStart() {
        System.out.println("DRAG STARTED");
    }

    public void dragStop() {
        System.out.println("DRAG STOPPED");
    }
}
