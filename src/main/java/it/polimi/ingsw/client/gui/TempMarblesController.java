package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.model.MarblesColor;
import it.polimi.ingsw.model.ResourceType;
import it.polimi.ingsw.network.requests.ChangeMarblesRequest;
import it.polimi.ingsw.network.requests.Request;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * <p>JavaFX controller for the scene "temp_marbles"</p>
 * <p>Handles the marble's selection when the Player has two Change Leader Abilities activated</p>
 */
public class TempMarblesController implements Initializable {

    @FXML
    private Button changeButton;
    @FXML
    private ImageView res_1, res_2;
    @FXML
    private ImageView white_1, white_2, white_3, white_4;
    @FXML
    private Spinner<Integer> n_res1, n_res2;

    /**
     * List of the the white marbles images, the max is four
     */
    private final List<ImageView> white_marbles = new ArrayList<>();

    /**
     * The location where the current drag started
     */
    private ImageView source;
    /**
     * The amount of white marbles that the player must change
     */
    int numMarbles;

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
     * <p>Initializes @FXML fields, the white marbles are organizing in a list <br>
     * Called automatically when an entity is injected from FXML</p>
     */
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.white_marbles.add(white_1);
        this.white_marbles.add(white_2);
        this.white_marbles.add(white_3);
        this.white_marbles.add(white_4);
        this.n_res1.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,0));
        this.n_res2.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,0));
    }

    /**
     * Called when the Player has two Change Leader Ability activated and take one or more white marble from the Market<br>
     * Set two images to the corresponding Leader Ability's resource type image<br>
     * And set the correct amount of white marble images
     */
    public void updateTemMarbles(){
        List<ResourceType> res = this.mainController.getClientModel().getMarketModel().getTempMarbles();
        this.numMarbles = this.mainController.getClientModel().getMarketModel().getNumWhiteMarbles();
        ((SpinnerValueFactory.IntegerSpinnerValueFactory) n_res1.getValueFactory()).setMax(numMarbles);
        ((SpinnerValueFactory.IntegerSpinnerValueFactory) n_res2.getValueFactory()).setMax(numMarbles);
        n_res1.getValueFactory().setValue(0);
        n_res2.getValueFactory().setValue(0);
        int i = 0;
        for(ImageView marble: white_marbles){
            if(i<numMarbles){
                System.out.println(numMarbles);
                marble.setImage(Util.marbleToImage(MarblesColor.WHITE));
            }
            else {
                marble.setImage(null);
            }
            i++;

        }
        res_1.setImage(Util.resToImage(res.get(0)));
        res_2.setImage(Util.resToImage(res.get(1)));
    }
    /**
     * <p>Called when the player changed the white marbles</p>
     * <p>When the button is pressed, the Player send the correct ChangeMarbesRequest</p>
     */
    public void change(ActionEvent actionEvent) {
        Request request = new ChangeMarblesRequest(n_res1.getValue(), n_res2.getValue());
        this.mainController.sendMessage(request);
        this.mainController.getTrayController().switchTempStage();
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
        ImageView destination = (ImageView) dragEvent.getSource();
        this.source.setImage(null);
        if(destination.getId().equals("res_1"))
            n_res1.getValueFactory().increment(1);
        else
            n_res2.getValueFactory().increment(1);
        if(n_res1.getValue() + n_res2.getValue() == numMarbles)
            this.changeButton.setDisable(false);
    }
}
