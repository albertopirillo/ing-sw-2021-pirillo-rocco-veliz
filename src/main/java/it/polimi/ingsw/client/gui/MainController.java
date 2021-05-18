package it.polimi.ingsw.client.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class MainController {

    /**
     * The popup used to show the market tray
     */
    private final Popup trayPopUp = new Popup();
    /**
     * The popup used to show the market
     */
    private final Popup marketPopUp = new Popup();

    @FXML
    Button trayPopup;
    @FXML
    Button marketPopup;
    @FXML
    TabPane tabPane;

    /**
     * Creates one tab for each player in the given playerList
     * @param playerList    a list of String representing the players' nickname
     * @throws IOException  if the .fxml file is not found
     */
    public void addPlayers(List<String> playerList) throws IOException {
        for(String nickname: playerList) {
            Tab tab = new Tab();
            tab.setText(nickname);
            Node node = Util.loadFXML("personal_board");
            tab.setContent(node);
            tabPane.getTabs().add(tab);
        }
    }

    //TODO: popups show above all windows

    /**
     * Shows and hides the tray popup, changing also the text of the button
     * @param event the event triggered when the button is pressed
     * @throws IOException  if the .fxml file is not found
     */
    public void showTray(ActionEvent event) throws IOException {
        Scene lastScene = ((Node)(event.getSource())).getScene();
        Stage stage = (Stage)lastScene.getWindow();
        if (!this.trayPopUp.isShowing()) {
            trayPopup.setText("Back to Personal Board");
            Parent tray = Util.loadFXML("market_tray");
            this.trayPopUp.getContent().add(tray);
            this.trayPopUp.show(stage);
        }
        else {
            trayPopup.setText("Show Market Tray");
            this.trayPopUp.hide();
        }
    }

    /**
     * Shows and hides the market popup, changing also the text of the button
     * @param event the event triggered when the button is pressed
     * @throws IOException  if the .fxml file is not found
     */
    public void showMarket(ActionEvent event) throws IOException {
        Scene lastScene = ((Node)(event.getSource())).getScene();
        Stage stage = (Stage)lastScene.getWindow();
        if (!this.marketPopUp.isShowing()) {
            marketPopup.setText("Back to Personal Board");
            Parent tray = Util.loadFXML("market");
            this.marketPopUp.getContent().add(tray);
            this.marketPopUp.show(stage);
        }
        else {
            marketPopup.setText("Show Market Cards");
            this.marketPopUp.hide();
        }
    }
}
