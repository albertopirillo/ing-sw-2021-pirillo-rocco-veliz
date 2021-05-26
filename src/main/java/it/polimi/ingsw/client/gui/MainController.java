package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.client.ClientGUI;
import it.polimi.ingsw.network.Processable;
import it.polimi.ingsw.network.requests.QuitGameRequest;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * <p>Main JavaFX Controller for the file main.fxml</p>
 * <p>This class initializes the player tabs</p>
 * <p>Used as a gateway to all JavaFX controllers</p>
 */
public class MainController {

    /**
     * The popup used to show the market tray
     */
    private final Popup trayPopUp = new Popup();
    /**
     * The popup used to show the market
     */
    private final Popup marketPopUp = new Popup();
    /**
     * A map to get get the right PersonalBoardController from a nickname
     */
    private final Map<String, PersonalBoardController> personalBoardControllerMap = new HashMap<>();
    /**
     * Reference to the actual MarketController
     */
    private MarketController marketController;
    /**
     * Reference to the actual TrayController
     */
    private TrayController trayController;
    /**
     * Reference to the actual SetupController
     */
    private SetupController setupController;
    /**
     * The UserInterface this controller is associated with
     */
    private ClientGUI clientGUI;

    @FXML
    private Button trayPopup;
    @FXML
    private Button marketPopup;
    @FXML
    private TabPane tabPane;

    /**
     * Gets the mapping between nicknames and PersonalBoardController
     * @return  a map of nicknames and PersonalBoardControllers
     */
    public Map<String, PersonalBoardController> getPersonalBoardControllerMap() {
        return personalBoardControllerMap;
    }

    /**
     * Gets the MarketController
     * @return  the current MarketController
     */
    public MarketController getMarketController() {
        return marketController;
    }

    /**
     * Gets the TrayController
     * @return  the current TrayController
     */
    public TrayController getTrayController() {
        return trayController;
    }

    /**
     * Gets the SetupController
     * @return  the current SetupController
     */
    public SetupController getSetupController() {
        return setupController;
    }

    /**
     * Sets the SetupController
     * @param setupController   the SetupController to set
     */
    public void setSetupController(SetupController setupController) {
        this.setupController = setupController;
    }

    /**
     * Sets the UserInterface
     * @param clientGUI the UserInterface to associate with this controller
     */
    public void setClientGUI(ClientGUI clientGUI) {
        this.clientGUI = clientGUI;
    }

    /**
     * <p>Creates one tab for each player in the given playerList</p>
     * <p>Initializes all popups and links them to the given stage</p>
     * @param playerList a list of String representing the player's nickname
     * @throws IOException if a .fxml file is not found
     */
    public void init(List<String> playerList) throws IOException {
        addPlayers(playerList);
        initTrayPopup();
        initMarketPopup();
    }

    /**
     * Wrapper to easily send a message from a controller class
     * @param message   the message to be sent to the Server
     */
    public void sendMessage(Processable message) {
        this.clientGUI.getClient().sendMessage(message);
    }

    private void addPlayers(List<String> playerList) throws IOException {
        for(String nickname: playerList) {
            FXMLLoader loader = Util.loadFXML("personal_board");
            Parent node = loader.load();
            PersonalBoardController personalBoardController = loader.getController();
            personalBoardController.setMainController(this);
            this.personalBoardControllerMap.put(nickname, personalBoardController);

            Tab tab = new Tab();
            tab.setText(nickname);
            tab.setContent(node);
            tabPane.getTabs().add(tab);
        }
    }

    private void initTrayPopup() throws IOException {
        this.trayPopup.setText("Show Market Tray");
        FXMLLoader loader = Util.loadFXML("market_tray");
        Parent tray = loader.load();
        this.trayController = loader.getController();
        this.trayPopUp.getContent().add(tray);
    }

    private void initMarketPopup() throws IOException {
        this.marketPopup.setText("Show Market Cards");
        FXMLLoader loader = Util.loadFXML("market");
        Parent tray = loader.load();
        this.marketController = loader.getController();
        this.marketPopUp.getContent().add(tray);
    }

    //TODO: popups show above all windows
    /**
     * Shows and hides the tray popup, changing also the text of the button
     * @param event the event triggered when the button is pressed
     */
    public void showTray(ActionEvent event) {
        Stage stage = Util.getStageFromEvent(event);
        if (!this.trayPopUp.isShowing()) {
            trayPopup.setText("Back to Personal Board");
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
     */
    public void showMarket(ActionEvent event) {
        Stage stage = Util.getStageFromEvent(event);
        if (!this.marketPopUp.isShowing()) {
            marketPopup.setText("Back to Personal Board");
            this.marketPopUp.show(stage);
        }
        else {
            marketPopup.setText("Show Market Cards");
            this.marketPopUp.hide();
        }
    }

    /**
     * Show an alert before quitting the game
     * @param event the event triggered by pressing the button
     */
    public void quitGame(Event event) {
        event.consume();
        QuitAlert alert = new QuitAlert();
        Optional<ButtonType> button = alert.showAndWait();
        if (button.isPresent()) {
            if (button.get() == ButtonType.OK) {
                QuitGameRequest req = new QuitGameRequest();
                sendMessage(req);
                Platform.exit();
            }
        }
    }

    /**
     * Displays an alert to notify the Client that his last move wasn't valid
     * @param errorMsg  a String representing the error to display
     */
    public void displayError(String errorMsg) {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setTitle("Error");
        errorAlert.setHeaderText("Received an error message from the server");
        errorAlert.setContentText(errorMsg);
        errorAlert.showAndWait();
    }
}
