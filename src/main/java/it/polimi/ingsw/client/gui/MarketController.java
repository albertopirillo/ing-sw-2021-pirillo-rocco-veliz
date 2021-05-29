package it.polimi.ingsw.client.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
    /**
     * The ImageView that contains the select image card
     */
    private ImageView cardSelected;


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
        if(cardSelected != null) this.cardSelected.getStyleClass().clear();
    }

    public void updateMarket(List<String> imgs) {
        for(int i=0; i<imgs.size(); i++){
            devCards.get(i).setImage(Util.getDevCardImg((imgs.get(i))));
        }
    }

    public void buyCard(MouseEvent mouseEvent) {
        System.out.println("Non so cosa minchia fare per mandare la BuyDevCard Request");
        if(cardSelected != null) {
            closeBuyPanel();
            this.cardSelected.getStyleClass().clear();
        }
        this.cardSelected = ((ImageView) mouseEvent.getSource());
        cardSelected.getStyleClass().add("selected-card");
        this.buyPanel.setVisible(true);
        /*String id = ((ImageView) mouseEvent.getSource()).getId();
        switch (id.substring(7)){
            case "1": //verde livello 3
                break;
            case "2":
                break;
            case "3":
                break;
            case "4":
                break;
            case "5":
                break;
            case "6":
                break;
            case "7":
                break;
            case "8":
                break;
            case "9":
                break;
            case "10":
                break;
            case "11":
                break;
            case "12": //viola livello 1
                break;
        }*/

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

    public void buildRequest(ActionEvent actionEvent) {
        System.out.println("ok");
        System.out.println("Carta selezione è " + this.cardSelected.getId());
        System.out.println("Risorse dal depot");
        System.out.println("Stone " + this.d_stone.getText());
        System.out.println("Servant" + this.d_servant.getText());
        System.out.println("Shield" + this.d_shield.getText());
        System.out.println("Coin" + this.d_coin.getText());
        switch (this.cardSelected.getId().substring(7)){
            case "1": //verde livello 3
                break;
            case "2":
                break;
            case "3":
                break;
            case "4":
                break;
            case "5":
                break;
            case "6":
                break;
            case "7":
                break;
            case "8":
                break;
            case "9":
                break;
            case "10":
                break;
            case "11":
                break;
            case "12": //viola livello 1
                break;
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
}
