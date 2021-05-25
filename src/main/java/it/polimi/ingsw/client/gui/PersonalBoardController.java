package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.exceptions.InvalidKeyException;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.ResourceType;
import it.polimi.ingsw.network.DepotSetting;
import it.polimi.ingsw.network.requests.ReorderDepotGUIRequest;
import it.polimi.ingsw.network.requests.Request;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;

import java.net.URL;
import java.util.*;

public class PersonalBoardController implements Initializable {

    @FXML
    ImageView depot1_1, depot2_1, depot2_2, depot3_1, depot3_2, depot3_3;
    @FXML
    ImageView extra1_1, extra1_2, extra2_1, extra2_2;
    @FXML
    Label sb_stone, sb_servant, sb_shield, sb_coin;
    @FXML
    Button reorderButton;

    /**
     * The location where the current drag started
     */
    private ImageView dragSource;
    /**
     * Whether the ReorderButton was pressed before or not
     */
    private boolean isReorderDepotPressed;
    /**
     * If it is currently possible to move a resource between depot's layers
     */
    private boolean canReorder;
    /**
     * The corresponding MainController
     */
    private MainController mainController;
    /**
     * A map to get a list of all the corresponding images from a given layer
     */
    private final Map<Integer, List<ImageView>> layerMapping = new HashMap<>();

    /**
     * Sets the MainController
     * @param mainController  the MainController to associate with this controller
     */
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    /**
     * <p>Initializes @FXML fields, organizing them in multiple lists and then in a single map</p>
     * <p>Called automatically when an entity is injected from FXML</p>
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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
                slot.setImage(Util.resToImage(res));
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

    //TODO: javadoc
    public void reorder(Event event) {
        if(!isReorderDepotPressed) {
            reorderButton.setText("End reorder");
            isReorderDepotPressed = true;
            canReorder = true;
        }
        else {
            reorderButton.setText("Reorder Depot");
            isReorderDepotPressed = false;
            canReorder = false;
            //Read the Depot and send a Request
            Request request = new ReorderDepotGUIRequest(convertToDepotSetting());
            this.mainController.sendMessage(request);
        }
    }

    private List<DepotSetting> convertToDepotSetting() {
        List<DepotSetting> settings = new ArrayList<>();
        for(Integer layerNum: layerMapping.keySet()) {
            List<ImageView> currentLayer = layerMapping.get(layerNum);
            ResourceType lastRes = Util.imageToRes(currentLayer.get(0).getImage());
            int amount = 0;
            for(ImageView slot: currentLayer) {
                ResourceType checkRes = Util.imageToRes(slot.getImage());
                if (checkRes != null && checkRes != lastRes) {
                    //Invalid setting, abort
                    return null;
                }
                else {
                    amount++;
                }
            }
            settings.add(new DepotSetting(layerNum, lastRes, amount));
        }
        return settings;
    }

    //Method that makes an entity draggable
    public void dragDetection(MouseEvent event) {
        if (canReorder) {
            this.dragSource = (ImageView) event.getSource();
            Dragboard db = this.dragSource.startDragAndDrop(TransferMode.ANY);
            ClipboardContent cb = new ClipboardContent();
            cb.putImage(this.dragSource.getImage());
            db.setContent(cb);
            event.consume();
        }
    }

    //TODO: useless?
    //Method to call when an entity is hovering on the drop area
    public void dragOver(DragEvent event) {
        System.out.println("DRAGGING OVER");
        if(event.getDragboard().hasImage()) {
            event.acceptTransferModes(TransferMode.ANY);
        }
    }

    //Method to call when the entity is released
    public void dragDrop(DragEvent event) {
        System.out.println("DRAG DROPPED");
        ImageView destination = (ImageView) event.getSource();
        if (destination.getImage() != null) {
            this.dragSource.setImage(destination.getImage());
        }
        else {
            this.dragSource.setImage(null);
        }
        Image img = event.getDragboard().getImage();
        destination.setImage(img);
    }

    //TODO: useless?
    //Method to call when the drag finished successfully
    public void dragDone(DragEvent event) {
        System.out.println("DRAG DONE");
    }
}
