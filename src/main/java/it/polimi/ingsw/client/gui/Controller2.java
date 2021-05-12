package it.polimi.ingsw.client.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class Controller2 {

    @FXML
    Label textField;

    public void displayName(String username) {
        textField.setText("Hello: " + username);
    }
}
