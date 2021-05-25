package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.network.Processable;
import it.polimi.ingsw.network.messages.GameSizeMessage;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class SetupController implements Initializable {

    @FXML
    private Text firstPlayerText;
    @FXML
    private Button nameButton, playerAmountButton;
    @FXML
    private TextField nameField;
    @FXML
    private Label formatError, firstPlayerLabel;
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

    /**
     * Gets and sets the selected name from the gui
     */
    public void confirmName() {
        if (nameField.getText().matches("[a-zA-Z0-9]+")) {
            synchronized (lock) {
                nickname = nameField.getText();
                lock.notifyAll();
            }
            formatError.setVisible(false);
            System.out.println("Hello " + nickname + "!");
            nameField.setDisable(true);
            nameButton.setDisable(true);
        }
        else {
            formatError.setVisible(true);
        }
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
        playerAmountButton.setDisable(true);
        for(Toggle toggle: playerAmountGroup.getToggles()) {
            Node node = (Node) toggle;
            node.setDisable(true);
        }
        String text = (playerAmount == 1 ? " player" : " players");
        firstPlayerLabel.setText("You selected " + playerAmount + text);
    }

    /**
     * <p>Sets the corresponding player number to every toggle</p>
     * <p>Disables all elements that only the first player should use</p>
     * <p>Called automatically when an entity is injected from FXML</p>
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        int i = 1;
        for(Toggle toggle: playerAmountGroup.getToggles()) {
            toggle.setUserData(i++);
            Node node = (Node) toggle ;
            node.setVisible(false);
            playerAmountButton.setVisible(false);
        }
        playerAmountButton.setDisable(true);
        formatError.setVisible(false);
        firstPlayerText.setVisible(false);
    }

    /**
     * Gives the first player to ability to select the game size
     */
    public void firstPlayerSetup() {
        firstPlayerText.setVisible(true);
        firstPlayerLabel.setText("Select the number of players");
        playerAmountButton.setDisable(false);
        for(Toggle toggle: playerAmountGroup.getToggles()) {
            Node node = (Node) toggle ;
            node.setVisible(true);
            playerAmountButton.setVisible(true);
        }
    }
}
