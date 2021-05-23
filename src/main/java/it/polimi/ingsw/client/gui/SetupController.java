package it.polimi.ingsw.client.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;

import java.net.URL;
import java.util.ResourceBundle;

public class SetupController implements Initializable {

    @FXML
    private TextField nameField;
    @FXML
    private ToggleGroup playerAmountGroup;

    public String confirmName(ActionEvent event) {
        String nickname = nameField.getText();
        System.out.println("Hello " + nickname + "!");
        return nickname;
    }

    public int confirmPlayerAmount(ActionEvent event) {
        int playerAmount = (int) playerAmountGroup.getSelectedToggle().getUserData();
        System.out.println("Number of players: " + playerAmount);
        return playerAmount;
    }

    /**
     * <p>Sets the corresponding player number to every toggle</p>
     * <p>Called automatically when an entity is injected from FXML</p>
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        int i = 1;
        for(Toggle toggle: playerAmountGroup.getToggles()) {
            toggle.setUserData(i++);
        }
    }
}
