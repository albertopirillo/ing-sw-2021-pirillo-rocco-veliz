package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.client.ClientGUI;
import it.polimi.ingsw.model.ResourceType;
import it.polimi.ingsw.network.requests.Request;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>Main JavaFX Controller</p>
 * <p>This is class initializes the tabs</p>
 * <p>Used as a gateway to all controllers</p>
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
     * Gets the ClientGUI the controller is associated with
     * @return a ClientGUI object representing the Client
     */
    public ClientGUI getClientGUI() {
        return clientGUI;
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
     * Wrapper to easily send a Request from a controller class
     * @param request   the request to be sent to the Server
     */
    public void sendMessage(Request request) {
        this.clientGUI.getClient().sendMessage(request);
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

    //TODO: remove
    public void depotTest() {
        /*this.personalBoardControllerMap.get("Player 1").setStrongbox(new Resource(0,2,3,1));
        this.personalBoardControllerMap.get("Player 2").setLayer(4, ResourceType.COIN, 2);
        this.personalBoardControllerMap.get("Player 2").setLayer(3, ResourceType.SHIELD, 2);
        this.personalBoardControllerMap.get("Player 2").setLayer(1, ResourceType.SERVANT, 1);*/
        this.personalBoardControllerMap.get("Player1").setLayer(1, ResourceType.SHIELD, 1);
        this.personalBoardControllerMap.get("Player1").setLayer(3, ResourceType.STONE, 2);
        this.personalBoardControllerMap.get("Player1").setLayer(4, ResourceType.COIN, 1);
    }
}
