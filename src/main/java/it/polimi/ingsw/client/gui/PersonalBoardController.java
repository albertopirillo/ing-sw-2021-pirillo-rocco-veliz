package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.exceptions.InvalidKeyException;
import it.polimi.ingsw.model.FaithTrack;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.ResourceType;
import it.polimi.ingsw.network.DepotSetting;
import it.polimi.ingsw.network.requests.ReorderDepotGUIRequest;
import it.polimi.ingsw.network.requests.Request;
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
    private ImageView depot1_1, depot2_1, depot2_2, depot3_1, depot3_2, depot3_3;
    @FXML
    private ImageView depot4_1, depot4_2, depot5_1, depot5_2;
    @FXML
    private Label sb_stone, sb_servant, sb_shield, sb_coin;
    @FXML
    private ImageView resSupply, tempRes1, tempRes2, tempRes3, tempRes4;
    @FXML
    private Button reorderButton;
    @FXML
    private ImageView cell0, cell1, cell2, cell3, cell4, cell5, cell6, cell7, cell8, cell9, cell10, cell11, cell12;
    @FXML
    private ImageView cell13, cell14, cell15, cell16, cell17, cell18, cell19, cell20, cell21, cell22, cell23, cell24;
    @FXML
    private ImageView popeFavor01, popeFavor02, popeFavor03;

    /**
     * The lists containing the relative cells of the faithtrack and the pope favor tiles
     */
    private final List<ImageView> faithTrackCells = new ArrayList<>();
    private final List<ImageView> popeFavorTiles = new ArrayList<>();

    private int playerFaith;
    /**
     * The location where the current drag started
     */
    private ImageView dragSource;

    private String dragSourceId;
    /**
     * Whether the ReorderButton was pressed before or not
     */
    private boolean isReorderDepotPressed;
    /**
     * If it is currently possible to move a resource between depot's layers
     */
    private boolean canReorder;
    /**
     * If the player is placing the temp resources
     */
    private boolean isPlacing;
    /**
     * The corresponding MainController
     */
    private MainController mainController;
    /**
     * A map to get a list of all the corresponding images from a given layer
     */
    private final Map<Integer, List<ImageView>> layerMapping = new HashMap<>();
    /**
     * List of all the corresponding images in tempResource
     */
    private final List<ImageView> tempResources = new ArrayList<>();

    private final Depot model = new Depot();

    /**
     * Gets the ReorderButton
     * @return  a Button representing the reorder button
     */
    public Button getReorderButton() {
        return this.reorderButton;
    }

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
        firstExtraLayer.add(depot4_1);
        depot4_1.setDisable(true);
        firstExtraLayer.add(depot4_2);
        depot4_2.setDisable(true);
        this.layerMapping.put(4, firstExtraLayer);
        List<ImageView> secondExtraLayer = new ArrayList<>();
        secondExtraLayer.add(depot5_1);
        depot5_1.setDisable(true);
        secondExtraLayer.add(depot5_2);
        depot5_2.setDisable(true);
        this.layerMapping.put(5, secondExtraLayer);

        //TODO: disable extraLayer at the beginning
        resSupply.setVisible(false);
        this.tempResources.add(tempRes1);
        tempRes1.setDisable(true);
        this.tempResources.add(tempRes2);
        tempRes2.setDisable(true);
        this.tempResources.add(tempRes3);
        tempRes3.setDisable(true);
        this.tempResources.add(tempRes4);
        tempRes4.setDisable(true);

        initFaithTrack();
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
                model.setSlot(layerNumber, i + 1, res);
            }
            else {
                slot.setImage(null);
                model.setSlot(layerNumber, i + 1, null);
            }
            i++;
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

    /**
     * <p>When called, the player can start reordering is depot</p>
     * <p>When the button is pressed again, reads the depot and sends a Request </p>
     */
    public void reorder() {
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
            Request request = new ReorderDepotGUIRequest(model.convertToDepotSetting());
            this.mainController.sendMessage(request);
            if (isPlacing) resetResSupply();
        }
    }

    //Method that makes an entity draggable
    public void dragDetection(MouseEvent event) {
        if (canReorder) {
            this.dragSource = (ImageView) event.getSource();
            this.dragSourceId = this.dragSource.getId();
            Dragboard db = this.dragSource.startDragAndDrop(TransferMode.ANY);
            ClipboardContent cb = new ClipboardContent();
            cb.putImage(this.dragSource.getImage());
            db.setContent(cb);
            event.consume();
        }
    }

    //Method to call when an entity is hovering on the drop area
    public void dragOver(DragEvent event) {
        //System.out.println("DRAGGING OVER");
        if(event.getDragboard().hasImage()) {
            event.acceptTransferModes(TransferMode.ANY);
        }
    }

    //Method to call when the entity is released
    public void dragDrop(DragEvent event) {
        System.out.println("DRAG DROPPED");
        ResourceType lastRes = null;
        ImageView destination = (ImageView) event.getSource();
        if (!dragSourceId.contains("tempRes")) {
            lastRes = model.getSlot(this.dragSourceId);
        }
        if (destination.getImage() != null) {
            this.dragSource.setImage(destination.getImage());
            if (!dragSourceId.contains("tempRes")) {
                ResourceType resourceType = model.getSlot(destination.getId());
                model.setSlot(this.dragSourceId, resourceType);
            }
        }
        else {
            this.dragSource.setImage(null);
            if (!dragSourceId.contains("tempRes")) {
                model.setSlot(this.dragSourceId, null);
            }
        }
        Image img = event.getDragboard().getImage();
        destination.setImage(img);
        if (!dragSourceId.contains("tempRes")) {
            model.setSlot(destination.getId(), lastRes);
        }
    }

    //Method to call when the drag finished successfully
    public void dragDone() {
        System.out.println("DRAG DONE");
    }

    //TODO: clean this and take care of discarded res
    public void updateTempResources(Resource tempRes) {
        resSupply.setVisible(true);
        int currentImage = 0;
        try {
            for (ResourceType res : tempRes.keySet()) {
                int amount = tempRes.getValue(res);
                for (int j = 0; j < amount; j++) {
                    ImageView imageView = tempResources.get(currentImage);
                    currentImage++;
                    imageView.setDisable(false);
                    imageView.setImage(Util.resToImage(res));
                }
            }
            //Now placement can start
            reorderButton.setText("End placement");
            isReorderDepotPressed = true;
            canReorder = true;
            isPlacing = true;
        } catch (InvalidKeyException e) {
            System.out.println("Invalid key");
        }
    }

    private void resetResSupply() {
        for(ImageView imageView: this.tempResources) {
            imageView.setImage(null);
            imageView.setDisable(true);
        }
        resSupply.setVisible(false);
    }

    /**
     * Initializes faith track cells
     */
    public void initFaithTrack(){
        Collections.addAll(this.faithTrackCells, cell0, cell1, cell2, cell3, cell4, cell5, cell6, cell7, cell8, cell9, cell10, cell11, cell12, cell13, cell14, cell15, cell16, cell17, cell18, cell19, cell20, cell21, cell22, cell23, cell24);
        this.playerFaith = 0;
        this.faithTrackCells.get(this.playerFaith).setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/png/faithtrack/faith.png"))));

        Collections.addAll(this.popeFavorTiles, popeFavor01, popeFavor02, popeFavor03);
        for (int i=1; i<=3; i++){
            popeFavorTiles.get(i-1).setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/png/faithtrack/popeFavor0" + i + "_fd.png"))));
        }
    }

    /**
     * Updates the position of the cross on the player's personal board
     * @param faithTrack the player's faithtrack object from the server update
     */
    public void updateFaithTrack(FaithTrack faithTrack){
        faithTrackCells.get(this.playerFaith).setImage(null);
        this.playerFaith = faithTrack.getPlayerFaith();
        this.faithTrackCells.get(faithTrack.getPlayerFaith()).setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/png/faithtrack/faith.png"))));
    }
}
