package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.model.MarblesColor;
import it.polimi.ingsw.network.requests.InsertMarbleRequest;
import it.polimi.ingsw.network.requests.Request;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class TrayController implements Initializable {

    @FXML
    private ImageView tray_1, tray_2, tray_3, tray_4, tray_5, tray_6, tray_7, tray_8, tray_9, tray_10, tray_11, tray_12, tray_white;

    private final List<ImageView> marketTray = new ArrayList<>();
    private ImageView remaining;
    private ImageView indexInsert;
    private MainController mainController;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.marketTray.add(tray_1);
        this.marketTray.add(tray_2);
        this.marketTray.add(tray_3);
        this.marketTray.add(tray_4);
        this.marketTray.add(tray_5);
        this.marketTray.add(tray_6);
        this.marketTray.add(tray_7);
        this.marketTray.add(tray_8);
        this.marketTray.add(tray_9);
        this.marketTray.add(tray_10);
        this.marketTray.add(tray_11);
        this.marketTray.add(tray_12);
        this.remaining = tray_white;
    }

    public void updateMarketTray(MarblesColor[][] marbles, MarblesColor remainingMarble) {
        if(this.indexInsert != null) this.indexInsert.setImage(null);
        for(int i=0; i<3; i++){
            for(int j=0; j<4; j++){
                this.marketTray.get(4*i + j).setImage(Util.marbleToImage((marbles[i][j])));
            }
        }
        this.remaining.setImage(Util.marbleToImage(remainingMarble));
    }

    public void dragDetection(MouseEvent event) {
        Dragboard db = this.remaining.startDragAndDrop(TransferMode.ANY);
        ClipboardContent cb = new ClipboardContent();
        cb.putImage(this.remaining.getImage());
        db.setContent(cb);
        event.consume();
    }

    public void dragDrop(DragEvent event) {
        System.out.println("DRAG DROPPED");
        ImageView destination = (ImageView) event.getSource();
        if (destination.getImage() == null) {
            destination.setImage(remaining.getImage());
            this.indexInsert = destination;
            Request request = new InsertMarbleRequest(Integer.parseInt(destination.getId().substring(destination.getId().length() - 1)));
            this.mainController.sendMessage(request);
        }
    }

    public void dragOver(DragEvent event) {
        System.out.println("DRAGGING OVER");
        if(event.getDragboard().hasImage()) {
            event.acceptTransferModes(TransferMode.ANY);
        }
    }
}
