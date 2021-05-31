package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.exceptions.InvalidKeyException;
import it.polimi.ingsw.exceptions.NegativeResAmountException;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.network.requests.BuyDevCardRequest;
import it.polimi.ingsw.network.requests.Request;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
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
    private Label d_stone, d_servant, d_shield, d_coin;
    @FXML
    private Pane buyPanel;
    @FXML
    private ChoiceBox comboBox;
    @FXML
    private Button buyButton;

    /**
     * The ImageView that contains the select image card
     */
    private ImageView selectedCard;

    private int slot;

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

    /**
     * Reset the Buy Panel to the default
     */
    public void closeBuyPanel(){
        this.buyPanel.setVisible(false);
        this.r_stone.setImage(null);
        this.r_servant.setImage(null);
        this.r_shield.setImage(null);
        this.r_coin.setImage(null);
        this.d_stone.setText("x0");
        this.d_servant.setText("x0");
        this.d_shield.setText("x0");
        this.d_coin.setText("x0");
        if(selectedCard != null) this.selectedCard.getStyleClass().clear();
    }

    public void updateMarket(List<String> imgs) {
        for(int i=0; i<imgs.size(); i++){
            devCards.get(i).setImage(Util.getDevCardImg((imgs.get(i))));
        }
    }

    public void buyCard(MouseEvent mouseEvent) {
        System.out.println("Non so cosa minchia fare per mandare la BuyDevCard Request");
        if(selectedCard != null) {
            closeBuyPanel();
            this.selectedCard.getStyleClass().clear();
        }
        this.selectedCard = ((ImageView) mouseEvent.getSource());
        selectedCard.getStyleClass().add("selected-card");
        this.buyPanel.setVisible(true);
    }

    public void dragDrop(DragEvent dragEvent) {
        ImageView destination = (ImageView) dragEvent.getSource();
        Dragboard db = dragEvent.getDragboard();
        System.out.println(d_stone.getText().substring(1));
        int newValue;
        if(destination.getImage() != null || destination.getImage() == db.getImage()) {
            destination.setImage(db.getImage());
            switch (destination.getId()) {
                case "r_stone":
                    newValue = Integer.parseInt(d_stone.getText().substring(1)) + 1;
                    d_stone.setText("x" + newValue);
                    break;
                case "r_servant":
                    newValue = Integer.parseInt(d_stone.getText().substring(1)) + 1;
                    d_servant.setText("x" + newValue);
                    break;
                case "r_shield":
                    newValue = Integer.parseInt(d_stone.getText().substring(1)) + 1;
                    d_shield.setText("x" + newValue);
                    break;
                case "r_coin":
                    newValue = Integer.parseInt(d_stone.getText().substring(1)) + 1;
                    d_coin.setText("x" + newValue);
                    break;
                default:
                    break;
            }
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
    public void buildRequest(ActionEvent actionEvent){
        System.out.println(comboBox.getValue());
        System.out.println(comboBox.getSelectionModel().getSelectedIndex());
        System.out.println("ok");
        System.out.println("Carta selezione è " + this.selectedCard.getId());
        System.out.println("Risorse dal depot");
        System.out.println("Stone " + this.d_stone.getText());
        System.out.println("Servant" + this.d_servant.getText());
        System.out.println("Shield" + this.d_shield.getText());
        System.out.println("Coin" + this.d_coin.getText());
        Resource depot = new Resource(0,0,0,0);
        Resource strongbox = new Resource(0,0,0,0);
        try {
            depot.modifyValue(ResourceType.STONE,Integer.parseInt(d_stone.getText().substring(1)));
            depot.modifyValue(ResourceType.SERVANT, Integer.parseInt(d_servant.getText().substring(1)));
            depot.modifyValue(ResourceType.SHIELD, Integer.parseInt(d_shield.getText().substring(1)));
            depot.modifyValue(ResourceType.COIN,Integer.parseInt(d_coin.getText().substring(1)));
        } catch (InvalidKeyException | NegativeResAmountException e) {
            e.printStackTrace();
        int index = Integer.parseInt(this.selectedCard.getId().substring(7));
        DevelopmentCard card = this.mainController.getClientModel().getMarketModel().getDevCardList().get(index);
        int level = card.getLevel();
        CardColor color = card.getType();
        AbilityChoice choice = getAbilityChoice(comboBox.getSelectionModel().getSelectedIndex());
        Request request = new BuyDevCardRequest(level, color, choice, depot, strongbox, this.slot - 1);
        this.mainController.sendMessage(request);
        }
    }

    public void dragDetection(MouseEvent mouseEvent) {
        ImageView start = (ImageView) mouseEvent.getSource();
        Dragboard db = start.startDragAndDrop(TransferMode.ANY);
        ClipboardContent cb = new ClipboardContent();
        cb.putImage(start.getImage());
        db.setContent(cb);
        mouseEvent.consume();
    }

    public void dragOver(DragEvent dragEvent) {
        dragEvent.acceptTransferModes(TransferMode.ANY);
        ((ImageView)dragEvent.getSource()).setImage(dragEvent.getDragboard().getImage());
        dragEvent.consume();
    }

    public void selectSlot(MouseEvent mouseEvent) {
        selectedCard.getStyleClass().add("selected-card");
        this.slot = Integer.parseInt(((ImageView) mouseEvent.getSource()).getId().substring(4));
        this.buyButton.setDisable(false);
    }
}
