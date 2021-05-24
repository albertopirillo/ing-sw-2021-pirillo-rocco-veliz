package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.network.Processable;
import it.polimi.ingsw.network.messages.GameSizeMessage;
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
    /**
     * The client's nickname
     */
    private String nickname;
    /**
     * The corresponding MainController
     */
    private MainController mainController;
    /**
     * Lock object to make the Client wait for nickname insertion
     */
    public static final Object lock = new Object();

    /**
     * Sets the MainController
     * @param mainController the associated MainController
     */
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    /**
     * Gets the client's nickname
     * @return a String representing the nickname
     */
    public String getNickname() {
        return nickname;
    }

    //TODO: disable the input after

    /**
     * Gets and sets the selected name from the gui
     */
    public void confirmName() {
        synchronized (lock) {
            nickname = nameField.getText();
            lock.notifyAll();
        }
        System.out.println("Hello " + nickname + "!");
    }

    /**
     * <p>Gets the selected player amount from the gui</p>
     * <p>Sends a message to the player with that amount</p>
     */
    public void confirmPlayerAmount() {
        int playerAmount = (int) playerAmountGroup.getSelectedToggle().getUserData();
        System.out.println("Number of players: " + playerAmount);
        Processable rsp = new GameSizeMessage(getNickname(), playerAmount);
        mainController.sendMessage(rsp);
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
