package it.polimi.ingsw.client.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class MainController {

    private final Popup marketPopUp = new Popup();
    private int playerAmount;

    @FXML
    TabPane tabPane;

    @FXML
    public Button popUpButton;

    public void addTab() throws IOException {
        Tab tab = new Tab();
        tab.setText("Player " + (++playerAmount));
        Node node = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/personalBoard.fxml")));
        tab.setContent(node);
        tabPane.getTabs().add(tab);
    }

    public void switchToMarket(ActionEvent event) throws IOException {
        Scene lastScene = ((Node)(event.getSource())).getScene();
        Stage stage = (Stage)lastScene.getWindow();
        if (!this.marketPopUp.isShowing()) {
            popUpButton.setText("Back to Personal Board");
            Parent market = Util.loadFXML("market");
            this.marketPopUp.getContent().add(market);
            this.marketPopUp.show(stage);
        }
        else {
            popUpButton.setText("Show Market Tray");
            this.marketPopUp.hide();
        }
    }
}
