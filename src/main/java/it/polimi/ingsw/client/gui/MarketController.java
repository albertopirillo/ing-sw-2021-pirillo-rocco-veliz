package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.exceptions.InvalidKeyException;
import it.polimi.ingsw.exceptions.NegativeResAmountException;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.network.requests.BuyDevCardRequest;
import it.polimi.ingsw.network.requests.Request;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MarketController implements Initializable {

    @FXML
    private ImageView imgCard1, imgCard2, imgCard3, imgCard4, imgCard5, imgCard6, imgCard7, imgCard8;
    @FXML
    private ImageView imgCard9, imgCard10, imgCard11, imgCard12;
    @FXML
    private ImageView r_stone, r_servant, r_shield, r_coin;
    @FXML
    private ImageView depot1_1, depot2_1, depot2_2, depot3_1, depot3_2, depot3_3;
    @FXML
    private Label d_stone, d_servant, d_shield, d_coin;
    @FXML
    private Pane buyPanel, depot, slots;
    @FXML
    private ChoiceBox comboBox;
    @FXML
    private Spinner<Integer> stone, servant, shield, coin;
    @FXML
    private Button buyButton;
    @FXML
    Label labelGreen, labelBlue, labelYellow, labelPurple, labelLv1, labelLv2, labelLv3;

    /**
     * The ImageView that contains the select image card
     */
    private ImageView selectedCard;

    private ImageView slot;

    private ImageView source;

    /**
     * The list of all sixteen corresponding images of the Developement Cards
     */
    private final List<ImageView> devCards = new ArrayList<>();

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
     * Initializes @FXML fields, organizing the corresponding images of Development Caed in one list<br>
     * <p>Called automatically when an entity is injected from FXML</p>
     */
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.devCards.add(imgCard1);
        this.devCards.add(imgCard2);
        this.devCards.add(imgCard3);
        this.devCards.add(imgCard4);
        this.devCards.add(imgCard5);
        this.devCards.add(imgCard6);
        this.devCards.add(imgCard7);
        this.devCards.add(imgCard8);
        this.devCards.add(imgCard9);
        this.devCards.add(imgCard10);
        this.devCards.add(imgCard11);
        this.devCards.add(imgCard12);
        this.buyPanel.setVisible(false);
        comboBox.getItems().clear();
        comboBox.getItems().addAll("No ability", "First Ability", "Second Ability", "Both Ability");
        comboBox.getSelectionModel().select("No ability");
    }

    public void loadPanes() {
        closeBuyPanel();
        loadStorages();
    }

    /**
     * Reset the Buy Panel to the default
     */
    private void closeBuyPanel(){
        this.r_stone.setImage(null);
        this.r_servant.setImage(null);
        this.r_shield.setImage(null);
        this.r_coin.setImage(null);
        this.d_stone.setText("x0");
        this.d_servant.setText("x0");
        this.d_shield.setText("x0");
        this.d_coin.setText("x0");
        this.buyPanel.setVisible(false);
        this.slots.setVisible(false);
        this.depot.setVisible(true);
        if(selectedCard != null) this.selectedCard.getStyleClass().remove("selected-card");
        if(slot != null) this.slot.getStyleClass().remove("selected-card");
    }

    private void loadStorages() {
        List<Image> imgs = this.mainController.getPersonalBoardController().getDepotImgs();
        depot1_1.setImage(imgs.get(0));
        depot2_1.setImage(imgs.get(1));
        depot2_2.setImage(imgs.get(2));
        depot3_1.setImage(imgs.get(3));
        depot3_2.setImage(imgs.get(4));
        depot3_3.setImage(imgs.get(5));
        Resource strongbox = this.mainController.getClientModel().getStoragesModel().getStrongboxMap().get(this.mainController.getNickname());
        try {
            this.stone.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, strongbox.getValue(ResourceType.STONE)));
            this.servant.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, strongbox.getValue(ResourceType.SERVANT)));
            this.shield.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, strongbox.getValue(ResourceType.SHIELD)));
            this.coin.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, strongbox.getValue(ResourceType.COIN)));
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
    }

    public void updateMarket(List<String> imgs) {
        for(int i=0; i<imgs.size(); i++){
            devCards.get(i).setImage(Util.getDevCardImg((imgs.get(i))));
        }
    }

    public void buyCard(MouseEvent mouseEvent) {
        if(mainController.isMainActionDone()) {
            String errorMsg = "You already performed an action this turn";
            ErrorAlert errorAlert = new ErrorAlert(errorMsg);
            errorAlert.showAndWait();
        }
        else {
            if (this.selectedCard != null) this.selectedCard.getStyleClass().remove("selected-card");
            this.selectedCard = ((ImageView) mouseEvent.getSource());
            selectedCard.getStyleClass().add("selected-card");
            this.buyPanel.setVisible(true);
        }
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

    public void selectSlot(MouseEvent mouseEvent) {
        if(slot!=null) this.slot.getStyleClass().remove("selected-card");
        this.slot = (ImageView) mouseEvent.getSource();
        this.slot.getStyleClass().add("selected-card");
        this.buyButton.setText("Buy Card");
        this.buyButton.setDisable(false);
    }

    public void buildRequest(ActionEvent actionEvent){
        if(this.depot.isVisible()){
            this.depot.setVisible(false);
            this.slots.setVisible(true);
            this.buyButton.setDisable(true);
        }
        else {
            this.slots.setVisible(false);
            this.depot.setVisible(true);
            this.buyButton.setText("Select Slot");
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
            int index = Integer.parseInt(this.selectedCard.getId().substring(7));
            DevelopmentCard card = this.mainController.getClientModel().getMarketModel().getDevCardList().get(index-1);
            int level = card.getLevel();
            CardColor color = card.getType();
            int numSlot = Integer.parseInt(this.slot.getId().substring(4));
            AbilityChoice choice = getAbilityChoice(comboBox.getSelectionModel().getSelectedIndex());
            Request request = new BuyDevCardRequest(level, color, choice, depot, strongbox, numSlot - 1);
            this.mainController.sendMessage(request);
        }
    }

    private AbilityChoice getAbilityChoice(int index){
        switch (index){
            case 0: return AbilityChoice.STANDARD;
            case 1: return AbilityChoice.FIRST;
            case 2: return AbilityChoice.SECOND;
            case 3: return AbilityChoice.BOTH;
        }
        return null;
    }
}
