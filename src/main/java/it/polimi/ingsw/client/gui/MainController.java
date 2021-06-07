package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.client.ClientGUI;
import it.polimi.ingsw.client.model.ClientModel;
import it.polimi.ingsw.model.LeaderAction;
import it.polimi.ingsw.network.Processable;
import it.polimi.ingsw.network.requests.EndTurnRequest;
import it.polimi.ingsw.network.requests.QuitGameRequest;
import it.polimi.ingsw.network.requests.Request;
import it.polimi.ingsw.network.requests.UseLeaderRequest;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * <p>Main JavaFX Controller for the file main.fxml</p>
 * <p>This class initializes the player tabs</p>
 * <p>Used as a gateway to all JavaFX controllers</p>
 */
public class MainController implements Initializable {

    /**
     * The popup used to show the market tray
     */
    private final Popup trayPopup = new Popup();
    /**
     * The popup used to show the market
     */
    private final Popup marketPopup = new Popup();
    /**
     * The popup used to show solo game updates
     */
    private final Popup soloPopUp = new Popup();
    /**
     * The popup used to perform the basic production
     */
    private final Popup basicPopUp = new Popup();
    /**
     * The popup used to perform the leader cards production
     */
    private final Popup leaderPopUp = new Popup();
    /**
     * The popup used to perform the dev cards production
     */
    private final Popup devPopUp = new Popup();
    /**
     * A map to get get the right PersonalBoardController from a nickname
     */
    private final Map<String, PersonalBoardController> personalBoardControllerMap = new HashMap<>();
    /**
     * Reference to the actual MarketController
     */
    private MarketController marketController;
    /**
     * Reference to the actual SoloController
     */
    private SoloController soloController;
    /**
     * Reference to the actual TrayController
     */
    private TrayController trayController;
    /**
     * Reference to the actual SetupController
     */
    private SetupController setupController;
    /**
     * Reference to the actual basicProductionController
     */
    private BasicProductionController basicProductionController;
    /**
     * * Reference to the actual leaderProductionController
     */
    private LeaderProductionController leaderProductionController;
    /**
     * * Reference to the actual leaderProductionController
     */
    private DevProductionController devProductionController;
    /**
     * The UserInterface this controller is associated with
     */
    private ClientGUI clientGUI;
    /**
     * List of all elements to be disable when it isn't the player's turn
     */
    private final List<Node> buttonsList = new ArrayList<>();
    /**
     * Reference to the actual stage
     */
    private Stage stage;

    @FXML
    private TabPane tabPane;
    @FXML
    private Button trayButton, marketButton, endTurnButton, quitButton;
    @FXML
    private MenuButton activateLeaderButton, prodButton;
    @FXML
    private MenuButton discardLeaderButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        buttonsList.add(trayButton);
        buttonsList.add(marketButton);
        buttonsList.add(endTurnButton);
        buttonsList.add(activateLeaderButton);
        buttonsList.add(prodButton);
        buttonsList.add(discardLeaderButton);
        buttonsList.add(quitButton);
    }

    /**
     * Sets a reference to the actual stage
     * @param stage the actual stage
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Gets the Model stored in the Client
     * @return a reference to the main class of the model
     */
    public ClientModel getClientModel() {
        return clientGUI.getClientModel();
    }

    /**
     * Gets the nickname of the associated player
     * @return a String representing the nickname
     */
    public String getNickname() {
        return clientGUI.getNickname();
    }

    /**
     * Gets the mapping between nicknames and PersonalBoardController
     * @return  a map of nicknames and PersonalBoardControllers
     */
    public Map<String, PersonalBoardController> getPersonalBoardControllerMap() {
        return personalBoardControllerMap;
    }

    public PersonalBoardController getPersonalBoardController(){
        return getPersonalBoardController(getNickname());
    }

    /**
     * Gets the PersonalBoardController associated to the given nickname
     * @param nickname the player to retrieve the controller
     * @return a single PersonalBoardController
     */
    public PersonalBoardController getPersonalBoardController(String nickname) {
        return personalBoardControllerMap.get(nickname);
    }

    /**
     * Helper to get the mainActionDone flag of the Client
     * @return true if action was already performed this turn
     */
    public boolean isMainActionDone() {
        return this.clientGUI.isMainActionDone();
    }

    /**
     * Helper to get the productionDone flag of the Client
     * @return true if a production was already activated this turn
     */
    public boolean isProductionDone() {
        return this.clientGUI.isProductionDone();
    }

    /**
     * Helper to set the mainActionDone flag of the Client
     * @param mainActionDone the value to set the flag at
     */
    public void setMainActionDone(Boolean mainActionDone) {
        this.clientGUI.setMainActionDone(mainActionDone);
    }

    /**
     * Helper to set the productionDone flag of the Client
     * @param productionDone the value to set the flag at
     */
    public void setProductionDone(Boolean productionDone) {
        this.clientGUI.setProductionDone(productionDone);
    }

    /**
     * Gets the MarketController
     * @return  the current MarketController
     */
    public MarketController getMarketController() {
        return marketController;
    }

    /**
     * Gets the LeaderProductionController
     * @return  the current LeaderProductionController
     */
    public LeaderProductionController getLeaderProductionController() {
        return leaderProductionController;
    }

    /**
     * Gets the DevProductionController
     * @return  the current DevProductionController
     */
    public DevProductionController getDevProductionController() {
        return devProductionController;
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
     * Gets the SoloController
     * @return the current SoloController
     */
    public SoloController getSoloController() {
        return soloController;
    }

    /**
     * Sets the SetupController
     * @param setupController the SetupController to set
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
        initSoloPopup();
        initBasicPopup();
        initLeaderPopup();
        initDevPopup();
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
            personalBoardController.setStorageModel(getClientModel().getStoragesModel());
            buttonsList.add(personalBoardController.getReorderButton());
            this.personalBoardControllerMap.put(nickname, personalBoardController);

            Tab tab = new Tab();
            tab.setText(nickname);
            tab.setContent(node);
            Image inkwell = new Image("/png/generic/inkwell.png", 22, 24, true, true);
            tab.setGraphic(new ImageView(inkwell));
            tab.getGraphic().setVisible(false);
            tabPane.getTabs().add(tab);
        }
    }

    private void initTrayPopup() throws IOException {
        this.trayButton.setText("Market Tray");
        FXMLLoader loader = Util.loadFXML("market_tray");
        Parent tray = loader.load();
        this.trayController = loader.getController();
        this.trayController.setMainController(this);
        this.trayPopup.getContent().add(tray);
        this.trayPopup.setX(800);
        this.trayPopup.setY(120);
        this.trayPopup.setAutoHide(true);
        this.trayPopup.setOnAutoHide(event -> this.trayButton.setText("Market Tray"));
    }

    private void initMarketPopup() throws IOException {
        this.marketButton.setText("Market Cards");
        FXMLLoader loader = Util.loadFXML("market");
        Parent market = loader.load();
        this.marketController = loader.getController();
        this.marketController.setMainController(this);
        this.marketPopup.getContent().add(market);
        this.marketPopup.setX(650);
        this.marketPopup.setY(120);
        this.marketPopup.setAutoHide(true);
        this.marketPopup.setOnAutoHide(event -> this.marketButton.setText("Market Cards"));
    }

    private void initSoloPopup() throws IOException {
        FXMLLoader loader = Util.loadFXML("solo");
        Parent solo = loader.load();
        this.soloController = loader.getController();
        this.soloController.setMainController(this);
        this.soloPopUp.getContent().add(solo);
        this.soloPopUp.setAutoHide(true);
        this.soloPopUp.setOnAutoHide(event -> this.soloController.hideCards());
    }

    private void initBasicPopup() throws IOException {
        FXMLLoader loader = Util.loadFXML("basic_production");
        Parent basic = loader.load();
        this.basicProductionController = loader.getController();
        this.basicProductionController.setMainController(this);
        this.basicPopUp.getContent().add(basic);
        this.basicPopUp.setX(900);
        this.basicPopUp.setY(120);
        this.basicPopUp.setAutoHide(true);
        this.basicPopUp.setOnAutoHide(event -> prodButton.setText("Use production"));
    }

    private void initLeaderPopup() throws IOException {
        FXMLLoader loader = Util.loadFXML("leader_production");
        Parent leader = loader.load();
        this.leaderProductionController = loader.getController();
        this.leaderProductionController.setMainController(this);
        this.leaderPopUp.getContent().add(leader);
        this.leaderPopUp.setX(600);
        this.leaderPopUp.setY(100);
        this.leaderPopUp.setAutoHide(true);
        this.leaderPopUp.setOnAutoHide(event -> prodButton.setText("Use production"));
        this.leaderProductionController.updateLeaderCards();
    }

    private void initDevPopup() throws IOException {
        FXMLLoader loader = Util.loadFXML("dev_production");
        Parent dev = loader.load();
        this.devProductionController = loader.getController();
        this.devProductionController.setMainController(this);
        this.devPopUp.getContent().add(dev);
        this.devPopUp.setX(600);
        this.devPopUp.setY(100);
        this.devPopUp.setAutoHide(true);
        this.devPopUp.setOnAutoHide(event -> prodButton.setText("Use production"));
        this.devProductionController.loadSlots();
    }


    public void switchSoloPopup() {
        if (!this.soloPopUp.isShowing()) {
            this.soloPopUp.show(stage);
        }
        else {
            this.soloPopUp.hide();
        }
    }

    /**
     * Shows and hides the tray popup, changing also the text of the button
     * @param event the event triggered when the button is pressed
     */
    public void showTray(ActionEvent event) {
        Stage stage = Util.getStageFromEvent(event);
        if (!this.trayPopup.isShowing()) {
            trayButton.setText("Back");
            this.trayPopup.show(stage);
        }
        else {
            trayButton.setText("Market Tray");
            this.trayPopup.hide();
        }
    }

    /**
     * Shows and hides the market popup, changing also the text of the button
     * @param event the event triggered when the button is pressed
     */
    public void showMarket(ActionEvent event) {
        Stage stage = Util.getStageFromEvent(event);
        if (!this.marketPopup.isShowing()) {
            this.marketController.closeBuyPanel();
            marketButton.setText("Back");
            this.marketPopup.show(stage);
        }
        else {
            marketButton.setText("Market Cards");
            this.marketPopup.hide();
        }
    }

    /**
     * Shows and hides the basicProduction popup, changing also the text of the button
     * @param event the event triggered when the button is pressed
     */
    public void showBasic(ActionEvent event) {
        Stage stage = (Stage)((MenuItem)event.getTarget()).getParentPopup().getOwnerWindow();

        this.stage = stage;
        if (!this.basicPopUp.isShowing()) {
            prodButton.setText("Back");
            this.basicPopUp.show(stage);
        }
        else {
            prodButton.setText("Use production");
            this.basicPopUp.hide();
        }
    }

    /**
     * Shows and hides the leaderProduction popup, changing also the text of the button
     * @param event the event triggered when the button is pressed
     */
    public void showLeader(ActionEvent event) {
        if (isProductionDone()){
            Stage stage = (Stage)((MenuItem)event.getTarget()).getParentPopup().getOwnerWindow();
            this.stage = stage;

            if ( this.leaderProductionController.hasProductionCard() ) {

                this.leaderProductionController.updateLeaderCards();
                this.leaderProductionController.loadStorages();

                if (!this.leaderPopUp.isShowing()) {
                    this.leaderProductionController.closeResourcePanel();
                    prodButton.setText("Back");
                    this.leaderPopUp.show(stage);
                }
                else {
                    prodButton.setText("Use production");
                    this.leaderPopUp.hide();
                }
            } else {
                displayError("You don't have leader cards with an extra production ability");
            }
        } else {
            displayError("You have to perform a basic production first");
        }
    }

    /**
     * Shows and hides the devProduction popup, changing also the text of the button
     * @param event the event triggered when the button is pressed
     */
    public void showDev(ActionEvent event) {
        if (isProductionDone()){
            Stage stage = (Stage)((MenuItem)event.getTarget()).getParentPopup().getOwnerWindow();
            this.stage = stage;
            if (!this.devPopUp.isShowing()) {
                    this.devProductionController.loadStorages();
                    this.devProductionController.closeResourcePanel();
                    prodButton.setText("Back");
                    this.devPopUp.show(stage);
                }
                else {
                    prodButton.setText("Use production");
                    this.devPopUp.hide();
                }
        } else {
            displayError("You have to perform a basic production first");
        }
    }

    /**
     * Hide the tray popup and also change the text of the button
     */
    public void closeTray(){
        trayButton.setText("Market Tray");
        this.trayPopup.hide();
    }

    /**
     * Hide the market popup and also change the text of the button
     */
    public void closeMarket(){
        marketButton.setText("Market Cards");
        this.marketPopup.hide();
    }

    /**
     * Hide the basic production popup and also change the text of the button
     */
    public void closeBasic(){
        prodButton.setText("Use production");
        this.basicPopUp.hide();
    }

    /**
     * Hide the leader production popup and also change the text of the button
     */
    public void closeLeader(){
        prodButton.setText("Use production");
        this.leaderPopUp.hide();
    }

    /**
     * Hide the dev production popup and also change the text of the button
     */
    public void closeDev(){
        prodButton.setText("Use production");
        this.devPopUp.hide();
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
        ErrorAlert errorAlert = new ErrorAlert(errorMsg);
        errorAlert.showAndWait();
    }

    /**
     * Selects the tab of the player corresponding to the given nickname
     * @param nickname  the nickname of the player to display the tab
     */
    public void switchToTab(String nickname) {
        for(Tab tab: tabPane.getTabs()) {
            if(tab.getText().equals(nickname)) {
                tabPane.getSelectionModel().select(tab);
                tab.getGraphic().setVisible(true);
            }
            else {
                tab.getGraphic().setVisible(false);
            }
        }
    }

    /**
     * Disables or enables all the buttons only the active player can interact with
     * @param disable   whether the buttons should be disable or not
     */
    public void disableGUI(boolean disable) {
        for(Node node: buttonsList) {
            node.setDisable(disable);
        }
    }

    /**
     * Sends a EndTurnRequest to the Server
     */
    public void endTurn() {
        Request request = new EndTurnRequest();
        sendMessage(request);
        this.setMainActionDone(false);
        this.setProductionDone(false);
    }

    public void discardLeaderRequest01(){
        Request request = new UseLeaderRequest(0, LeaderAction.DISCARD);
        sendMessage(request);
    }

    public void discardLeaderRequest02(){
        Request request = new UseLeaderRequest(1, LeaderAction.DISCARD);
        sendMessage(request);
    }

    public void useLeaderRequest01(){
        Request request = new UseLeaderRequest(0, LeaderAction.USE_ABILITY);
        sendMessage(request);
    }

    public void useLeaderRequest02(){
        Request request = new UseLeaderRequest(1, LeaderAction.USE_ABILITY);
        sendMessage(request);
    }
}
