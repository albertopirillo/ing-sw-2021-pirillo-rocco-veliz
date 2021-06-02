package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.client.model.StorageModel;
import it.polimi.ingsw.exceptions.InvalidKeyException;
import it.polimi.ingsw.exceptions.NegativeResAmountException;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.network.DepotSetting;
import it.polimi.ingsw.network.requests.PlaceResourceRequest;
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
    private Label sb_stone, sb_servant, sb_shield, sb_coin, leaderLabel;
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
    @FXML
    private ImageView slot1_1, slot1_2, slot1_3, slot2_1, slot2_2, slot2_3, slot3_1, slot3_2, slot3_3;
    @FXML
    private ImageView leaderCard0, leaderCard1;

    /**
     * The lists containing the relative cells of the faithtrack and the pope favor tiles
     */
    private final List<ImageView> faithTrackCells = new ArrayList<>();
    private final List<ImageView> popeFavorTiles = new ArrayList<>();

    private int playerFaith, blackCrossPosition;
    /**
     * The location where the current drag started
     */
    private ImageView dragSource;
    /**
     * The fx:id of the source image of the drag
     */
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
     * A map to get a list of all the corresponding images in a given development slot
     */
    private final Map<Integer, List<ImageView>> devSlotsMapping = new HashMap<>();
    /**
     * List of all the corresponding images in tempResource
     */
    private final List<ImageView> tempResAsImage = new ArrayList<>();
    /**
     * The structure of the associated Depot
     */
    private final TempDepot tempDepot = new TempDepot();
    /**
     * The data of the associated temporary resource
     */
    private final List<ResourceType> tempResAsList = new ArrayList<>();
    /**
     * Reference to the Model in the client
     */
    private StorageModel storageModel;

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
     * Sets the StorageModel
     * @param storageModel the reference to the StorageModel in the Client
     */
    public void setStorageModel(StorageModel storageModel) {
        this.storageModel = storageModel;
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
        Collections.addAll(secondLayer, depot2_1, depot2_2);
        this.layerMapping.put(2, secondLayer);
        List<ImageView> thirdLayer = new ArrayList<>();
        Collections.addAll(thirdLayer, depot3_1, depot3_2, depot3_3);
        this.layerMapping.put(3, thirdLayer);
        List<ImageView> firstExtraLayer = new ArrayList<>();
        Collections.addAll(firstExtraLayer, depot4_1, depot4_2);
        depot4_1.setDisable(true);
        depot4_2.setDisable(true);
        this.layerMapping.put(4, firstExtraLayer);
        List<ImageView> secondExtraLayer = new ArrayList<>();
        Collections.addAll(secondExtraLayer, depot5_1, depot5_2);
        depot5_1.setDisable(true);
        depot5_2.setDisable(true);
        this.layerMapping.put(5, secondExtraLayer);
        Collections.addAll(tempResAsImage, tempRes1, tempRes2, tempRes3, tempRes4);
        resSupply.setVisible(false);
        tempRes1.setDisable(true);
        tempRes2.setDisable(true);
        tempRes3.setDisable(true);
        tempRes4.setDisable(true);
        initFaithTrack();
        initDevSlots();
    }

    private void initDevSlots() {
        List<ImageView> firstSlot = new ArrayList<>();
        Collections.addAll(firstSlot, slot1_1, slot1_2, slot1_3);
        this.devSlotsMapping.put(1, firstSlot);
        List<ImageView> secondSlot = new ArrayList<>();
        Collections.addAll(secondSlot, slot2_1, slot2_2, slot2_3);
        this.devSlotsMapping.put(2, secondSlot);
        List<ImageView> thirdSlot = new ArrayList<>();
        Collections.addAll(thirdSlot, slot3_1, slot3_2, slot3_3);
        this.devSlotsMapping.put(3, thirdSlot);
    }

    /**
     * Updates the strongbox
     * @param resource the resources to update the strongbox with
     */
    public void setStrongbox(Resource resource) {
        Resource myStrongbox = storageModel.getStrongboxMap().get(mainController.getNickname());
        for(ResourceType key: resource.keySet()) {
            try {
                myStrongbox.modifyValue(key, resource.getValue(key));
                switch (key) {
                    case STONE: sb_stone.setText("x" + resource.getValue(key)); break;
                    case SERVANT: sb_servant.setText("x" + resource.getValue(key)); break;
                    case SHIELD: sb_shield.setText("x" + resource.getValue(key)); break;
                    case COIN: sb_coin.setText("x" + resource.getValue(key)); break;
                    default: break;
                }
            } catch (NegativeResAmountException | InvalidKeyException e) {
                System.out.println("Invalid key or amount");
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
        List<ImageView> layer = this.layerMapping.get(layerNumber);
        if(res == null) {
            for(ImageView slot: layer) {
                slot.setImage(null);
                tempDepot.setSlot(slot.getId(), null);
            }
        }
        else if (amount != 0) {
            int i = 0;
            for (ImageView slot : layer) {
                if (i < amount) {
                    slot.setImage(Util.resToImage(res));
                    tempDepot.setSlot(slot.getId(), res);
                    i++;
                } else {
                    slot.setImage(null);
                    tempDepot.setSlot(slot.getId(), null);
                }
            }
        }
    }

    /**
     * Update the whole Depot using a list of DepotSetting
     * @param settings  a list of DepotSetting with instructions to update the Depot
     */
    public void setDepot(List<DepotSetting> settings) {
        //System.out.println("Received settings of " + settings.size() + " elements");
        //for(DepotSetting setting: settings) {
        //    System.out.println(setting);
        //}
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
            Request request;
            if(isPlacing) {
                System.out.println("[GUI] Sending place resource request");
                request = new PlaceResourceRequest(remainingRes(), tempDepot.convertToDepotSetting(), true);
                isPlacing = false;
                resetResSupply();
            }
            else {
                System.out.println("[GUI] Sending reorder request");
                request = new ReorderDepotGUIRequest(tempDepot.convertToDepotSetting());
            }
            this.mainController.sendMessage(request);
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
        ResourceType sourceRes;
        ImageView destination = (ImageView) event.getSource();
        //Save the source res
        sourceRes = getGenericSlot(dragSourceId);
        //Set the source, if destination is full
        if (destination.getImage() != null) {
            this.dragSource.setImage(destination.getImage());
            ResourceType resType = getGenericSlot(destination.getId());
            setGenericSlot(dragSourceId, resType);
        }
        //Set the source, if destination is empty
        else {
            this.dragSource.setImage(null);
            setGenericSlot(dragSourceId, null);
        }
        //Set the destination
        Image img = event.getDragboard().getImage();
        destination.setImage(img);
        setGenericSlot(destination.getId(), sourceRes);
    }

    private void setGenericSlot(String id, ResourceType resourceType) {
        if (id.contains("tempRes")) {
            int slot = Character.getNumericValue(id.charAt(id.length() - 1));
            this.tempResAsList.set(slot - 1, resourceType);
        }
        else {
            this.tempDepot.setSlot(id, resourceType);
        }
    }

    public ResourceType getGenericSlot(String id) {
        if (id.contains("tempRes")) {
            int slot = Character.getNumericValue(id.charAt(id.length() - 1));
            return this.tempResAsList.get(slot - 1);
        }
        else {
            return this.tempDepot.getSlot(id);
        }
    }

    /**
     * <p>Process the update of the temporary resources</p>
     * <p>Initializes the data structure to handle those resources</p>
     * <p>Makes the player able to drag and drop his resources</p>
     * @param tempRes   the new temporary resources
     */
    public void updateTempResources(Resource tempRes) {
        resSupply.setVisible(true);
        int currentImage = 0;
        try {
            for (ResourceType res : tempRes.keySet()) {
                int amount = tempRes.getValue(res);
                for (int j = 0; j < amount; j++) {
                    this.tempResAsList.add(res);
                    ImageView imageView = tempResAsImage.get(currentImage);
                    imageView.setDisable(false);
                    imageView.setImage(Util.resToImage(res));
                    currentImage++;
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

    private Resource remainingRes() {
        Resource toDiscard = new Resource(0,0,0,0);
        for(ResourceType resourceType: this.tempResAsList) {
            if (resourceType != null) {
                try {
                    toDiscard.modifyValue(resourceType, 1);
                } catch (NegativeResAmountException e) {
                    System.out.println("Invalid key or amount");
                }
            }
        }
        System.out.println("[GUI] Discarding " + toDiscard.getTotalAmount() + " resources");
        return toDiscard;
    }

    private void resetResSupply() {
        for(ImageView imageView: this.tempResAsImage) {
            imageView.setImage(null);
            imageView.setDisable(true);
        }
        resSupply.setVisible(false);
        this.tempResAsList.clear();
    }

    /**
     * Initializes faith track cells
     */
    public void initFaithTrack(){
        Collections.addAll(this.faithTrackCells, cell0, cell1, cell2, cell3, cell4, cell5, cell6, cell7, cell8, cell9, cell10, cell11, cell12, cell13, cell14, cell15, cell16, cell17, cell18, cell19, cell20, cell21, cell22, cell23, cell24);
        this.playerFaith = 0;
        this.faithTrackCells.get(this.playerFaith).setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/png/faithtrack/faith.png"))));

        Collections.addAll(this.popeFavorTiles, popeFavor01, popeFavor02, popeFavor03);
        for (int i=0; i<3; i++){
            popeFavorTiles.get(i).setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/png/faithtrack/popeFavor0" + i + "_fd.png"))));
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
        List<PopeFavorCard> popeCards = faithTrack.getPopeFavorCards();
        for (int i=0; i<3; i++){
            if(popeCards.get(i).isFaceUp()){
                popeFavorTiles.get(i).setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/png/faithtrack/popeFavor0" + i + "_fu.png"))));
            }
        }

        int tempBlackCrossPosition = faithTrack.getBlackCrossPosition();
        if (tempBlackCrossPosition > -1){
            if (tempBlackCrossPosition == this.playerFaith){
                if (this.blackCrossPosition > -1){
                    faithTrackCells.get(this.blackCrossPosition).setImage(null);
                }
                faithTrackCells.get(this.playerFaith).setImage(null);
                this.faithTrackCells.get(faithTrack.getPlayerFaith()).setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/png/solo_game/black_and_faith.png"))));
                this.blackCrossPosition = tempBlackCrossPosition;
            } else {
                faithTrackCells.get(this.blackCrossPosition).setImage(null);
                this.blackCrossPosition = tempBlackCrossPosition;
                this.faithTrackCells.get(this.blackCrossPosition).setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/png/solo_game/black_cross.png"))));
            }
        }
    }

    public void updateLeaderCards(List<LeaderCard> playerCards) {
        if (playerCards.size() > 0){
            String img0 = playerCards.get(0).getImg();
            leaderCard0.setImage(Util.getLeaderImg(img0));
            if (playerCards.size() > 1){
                String img1 = playerCards.get(1).getImg();
                leaderCard1.setImage(Util.getLeaderImg(img1));
            } else {
                leaderCard1.setImage(null);
            }
        } else {
            leaderCard0.setImage(null);
        }
    }

    public void updateDevSlots(List<DevelopmentSlot> developmentSlots) {
        int i = 1;
        for(DevelopmentSlot devSlot: developmentSlots) {
            List<ImageView> currentSlot = this.devSlotsMapping.get(i);
            int j = 0;
            for(DevelopmentCard card: devSlot.getCards()) {
                currentSlot.get(j).setImage(Util.getDevCardImg(card.getImg()));
                j++;
            }
            i++;
        }
    }

    public List<Image> getDepotImgs(){
        List<Image> imgs = new ArrayList<>();
        imgs.add(depot1_1.getImage());
        imgs.add(depot2_1.getImage());
        imgs.add(depot2_2.getImage());
        imgs.add(depot3_1.getImage());
        imgs.add(depot3_2.getImage());
        imgs.add(depot3_3.getImage());
        return imgs;
    }

}
