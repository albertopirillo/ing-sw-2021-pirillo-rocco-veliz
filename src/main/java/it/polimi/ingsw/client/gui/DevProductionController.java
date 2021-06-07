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
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

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

    /**
     * The ImageView that contains the select image card
     */
    private ImageView selectedCard;

    private ImageView source;

    /**
     * The list of player's dev cards
     */
    private final List<ImageView> imgsDevCards = new ArrayList<>();

    /**
     * The list of pane to disable dev cards
     */
    private final List<Pane> panesDevCards = new ArrayList<>();

    /**
     * The list of slots to activate
     */
    private List<Integer> cards = new ArrayList<>();

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
     * Initializes @FXML fields, organizing the corresponding images of leader cards in one list<br>
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
        if(selectedCard != null) this.selectedCard.getStyleClass().remove("selected-card");
    }

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

    public void loadStorages() {
        List<Image> imgs = this.mainController.getPersonalBoardController().getDepotImgs();
        depot1_1.setImage(imgs.get(0));
        depot2_1.setImage(imgs.get(1));
        depot2_2.setImage(imgs.get(2));
        depot3_1.setImage(imgs.get(3));
        depot3_2.setImage(imgs.get(4));
        depot3_3.setImage(imgs.get(5));
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

    public void useCard1(MouseEvent mouseEvent) {
        if (this.selectedCard != null) this.selectedCard.getStyleClass().remove("selected-card");
        this.selectedCard = ((ImageView) mouseEvent.getSource());
        selectedCard.getStyleClass().add("selected-card");
        this.resourcePanel.setVisible(true);
        this.depot.setVisible(true);

        //TODO set right slot
    }

    public void useCard2(MouseEvent mouseEvent) {
        if (this.selectedCard != null) this.selectedCard.getStyleClass().remove("selected-card");
        this.selectedCard = ((ImageView) mouseEvent.getSource());
        selectedCard.getStyleClass().add("selected-card");
        this.resourcePanel.setVisible(true);
        this.depot.setVisible(true);

        //TODO set right slot
    }

    public void useCard3(MouseEvent mouseEvent) {
        if (this.selectedCard != null) this.selectedCard.getStyleClass().remove("selected-card");
        this.selectedCard = ((ImageView) mouseEvent.getSource());
        selectedCard.getStyleClass().add("selected-card");
        this.resourcePanel.setVisible(true);
        this.depot.setVisible(true);

        //TODO Set right slot
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

        Request request = new DevProductionRequest(this.cards, depot, strongbox);
        this.mainController.sendMessage(request);
        this.mainController.closeDev();
    }
}
