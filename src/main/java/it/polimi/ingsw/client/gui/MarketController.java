package it.polimi.ingsw.client.gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MarketController implements Initializable {

    @FXML
    private ImageView imgCard1, imgCard2, imgCard3, imgCard4, imgCard5, imgCard6, imgCard7, imgCard8;
    @FXML
    private ImageView imgCard9, imgCard10, imgCard11, imgCard12;
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
    }

    public void updateMarket(List<String> imgs) {
        for(int i=0; i<imgs.size(); i++){
            devCards.get(i).setImage(Util.getCardImg(imgs.get(i)));
        }
    }

}
