package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.exceptions.DevSlotEmptyException;
import it.polimi.ingsw.exceptions.InvalidKeyException;
import it.polimi.ingsw.exceptions.NegativeResAmountException;
import it.polimi.ingsw.model.DevelopmentSlot;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.ResourceType;
import it.polimi.ingsw.network.requests.DevProductionRequest;
import it.polimi.ingsw.network.requests.Request;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.*;

/**
 * <p>JavaFX Controller for the file dev_production.fxml</p>
 * <p>This class initializes the production popup</p>
 * <p>Used to build the DevProductionRequest</p>
 */
public class DevProductionController implements Initializable {

    @FXML
    private ImageView imgCard1, imgCard2, imgCard3;
    @FXML
    private ImageView r_stone, r_servant, r_shield, r_coin;
    @FXML
    private ImageView depot1_1, depot2_1, depot2_2, depot3_1, depot3_2, depot3_3;
    @FXML
    private Label d_stone, d_servant, d_shield, d_coin;
    @FXML
    private Pane resourcePanel, depot, imgPane1, imgPane2, imgPane3;
    @FXML
    private Spinner<Integer> stone, servant, shield, coin;
    @FXML
    private Button confirmButton;
    @FXML
    Label labelDevCards;

    private ImageView source;

    /**
     * The set of selected card indexes (0-based) to be transferred to the server.
     */
    Set<Integer> selectedIndexes = new HashSet<>();

    /**
     * The list of player's dev cards
     */
    private final List<ImageView> imgsDevCards = new ArrayList<>();

    /**
     * The list of pane to disable dev cards
     */
    private final List<Pane> panesDevCards = new ArrayList<>();

    /**
     * The corresponding MainController
     */
    private MainController mainController;

    /**
     * Sets the MainController
     * @param mainController  the MainController to associate with this controller
     */
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    /**
     * Initializes @FXML fields, organizing the corresponding images of development cards in one list<br>
     * <p>Called automatically when an entity is injected from FXML</p>
     */
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.imgsDevCards.add(imgCard1);
        this.imgsDevCards.add(imgCard2);
        this.imgsDevCards.add(imgCard3);
        this.panesDevCards.add(imgPane1);
        this.panesDevCards.add(imgPane2);
        this.panesDevCards.add(imgPane3);
        this.resourcePanel.setVisible(false);
        this.stone.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 0));
        this.servant.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 0));
        this.shield.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 0));
        this.coin.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 0));
    }


    /**
     * Reset the Buy Panel to the default
     */
    public void closeResourcePanel(){
        this.r_stone.setImage(null);
        this.r_servant.setImage(null);
        this.r_shield.setImage(null);
        this.r_coin.setImage(null);
        this.d_stone.setText("x0");
        this.d_servant.setText("x0");
        this.d_shield.setText("x0");
        this.d_coin.setText("x0");
        this.resourcePanel.setVisible(false);
        this.depot.setVisible(true);
        this.selectedIndexes.clear();
        this.imgCard1.getStyleClass().remove("selected-card");
        this.imgCard2.getStyleClass().remove("selected-card");
        this.imgCard3.getStyleClass().remove("selected-card");
    }

    /**
     * Updates the three devCard slots status from local model
     */
    public void loadSlots(){
        List<DevelopmentSlot> slots = this.mainController.getClientModel().getPersonalBoardModel().getDevSlotMap().get(this.mainController.getNickname());
        int i = 0;
        for (DevelopmentSlot slot : slots){
            try {
                imgsDevCards.get(i).setImage(Util.getDevCardImg(slot.getTopCard().getImg()));
                panesDevCards.get(i).setVisible(false);
            } catch (DevSlotEmptyException e) {
                imgsDevCards.get(i).setImage(Util.getGenericImg("emptyCard"));
                panesDevCards.get(i).setVisible(true);
            }
            finally {
                i++;
            }
        }
    }

    /**
     * Updates the player depot and strongbox from local model
     */
    public void loadStorages() {
        List<Image> imgs = this.mainController.getPersonalBoardController().getDepotImages();
        depot1_1.setImage(imgs.get(0));
        depot2_1.setImage(imgs.get(1));
        depot2_2.setImage(imgs.get(2));
        depot3_1.setImage(imgs.get(3));
        depot3_2.setImage(imgs.get(4));
        depot3_3.setImage(imgs.get(5));
        //Reset default value to 0
        this.stone.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 0));
        this.servant.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 0));
        this.shield.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 0));
        this.coin.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 0));
        //Set max value to corresponding amount in strongbox
        Resource strongbox = this.mainController.getClientModel().getStoragesModel().getStrongboxMap().get(this.mainController.getNickname());
        try {
            ((SpinnerValueFactory.IntegerSpinnerValueFactory)this.stone.getValueFactory()).setMax(strongbox.getValue(ResourceType.STONE));
            ((SpinnerValueFactory.IntegerSpinnerValueFactory)this.servant.getValueFactory()).setMax(strongbox.getValue(ResourceType.SERVANT));
            ((SpinnerValueFactory.IntegerSpinnerValueFactory)this.shield.getValueFactory()).setMax(strongbox.getValue(ResourceType.SHIELD));
            ((SpinnerValueFactory.IntegerSpinnerValueFactory)this.coin.getValueFactory()).setMax(strongbox.getValue(ResourceType.COIN));
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
    }

    private void useCard(ImageView imgCard, Integer index){
        System.out.println(selectedIndexes);
        if(selectedIndexes.contains(index)){
            imgCard.getStyleClass().remove("selected-card");
            selectedIndexes.remove(index);
        } else {
            imgCard.getStyleClass().add("selected-card");
            selectedIndexes.add(index);
        }

        if(!resourcePanel.isVisible()){
            resourcePanel.setVisible(true);
            depot.setVisible(true);
        }
    }

    public void useCard1(MouseEvent mouseEvent) {
        useCard(imgCard1, 0);
    }

    public void useCard2(MouseEvent mouseEvent) {
        useCard(imgCard2, 1);
    }

    public void useCard3(MouseEvent mouseEvent) {
        useCard(imgCard3, 2);
    }

   public void dragDetection(MouseEvent mouseEvent) {
        this.source = (ImageView) mouseEvent.getSource();
        Dragboard db = this.source.startDragAndDrop(TransferMode.ANY);
        ClipboardContent cb = new ClipboardContent();
        cb.putImage(this.source.getImage());
        db.setContent(cb);
        mouseEvent.consume();
    }

    public void dragOver(DragEvent dragEvent) {
        if(dragEvent.getDragboard().hasImage()) {
            dragEvent.acceptTransferModes(TransferMode.ANY);
        }
    }

    public void dragDrop(DragEvent dragEvent) {
        String sourceId = this.source.getId();
        switch (this.mainController.getPersonalBoardController().getGenericSlot(sourceId)){
            case STONE:
                addRes(r_stone, ResourceType.STONE, d_stone);
                break;
            case SERVANT:
                addRes(r_servant, ResourceType.SERVANT, d_servant);
                break;
            case SHIELD:
                addRes(r_shield, ResourceType.SHIELD, d_shield);
                break;
            case COIN:
                addRes(r_coin, ResourceType.COIN, d_coin);
                break;
            default:
                break;
        }
        this.source.setImage(null);
    }

    private void addRes(ImageView id, ResourceType res, Label d){
        if(id.getImage()==null) id.setImage(Util.resToImage(res));
        int newValue = Integer.parseInt(d.getText().substring(1)) + 1;
        d.setText("x" + newValue);
    }

    public void buildRequest(ActionEvent actionEvent){
        Resource depot = new Resource(0, 0, 0, 0);
        Resource strongbox = new Resource(0, 0, 0, 0);
        try {
            depot.modifyValue(ResourceType.STONE, Integer.parseInt(d_stone.getText().substring(1)));
            depot.modifyValue(ResourceType.SERVANT, Integer.parseInt(d_servant.getText().substring(1)));
            depot.modifyValue(ResourceType.SHIELD, Integer.parseInt(d_shield.getText().substring(1)));
            depot.modifyValue(ResourceType.COIN, Integer.parseInt(d_coin.getText().substring(1)));
            strongbox.modifyValue(ResourceType.STONE, stone.getValue());
            strongbox.modifyValue(ResourceType.SERVANT, servant.getValue());
            strongbox.modifyValue(ResourceType.SHIELD, shield.getValue());
            strongbox.modifyValue(ResourceType.COIN, coin.getValue());
        } catch (NegativeResAmountException e) {
            e.printStackTrace();
        }
        System.out.println(depot);
        System.out.println(strongbox);
        System.out.println(selectedIndexes);
        Request request = new DevProductionRequest(new ArrayList<>(selectedIndexes), depot, strongbox);
        this.mainController.sendMessage(request);
        this.mainController.closeDev();
    }
}
