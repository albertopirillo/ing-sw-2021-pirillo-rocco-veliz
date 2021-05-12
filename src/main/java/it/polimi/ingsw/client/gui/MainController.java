package it.polimi.ingsw.client.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

import java.io.IOException;
import java.util.Objects;

public class MainController {

    @FXML
    TabPane tabPane;
    private int amount = 2;

    public void addTab() throws IOException {
        Tab tab = new Tab();
        tab.setText("Player " + (++amount));
        Node node = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/personalBoard.fxml")));
        tab.setContent(node);
        tabPane.getTabs().add(tab);
    }
}
