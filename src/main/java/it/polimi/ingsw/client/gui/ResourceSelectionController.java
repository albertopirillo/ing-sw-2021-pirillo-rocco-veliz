package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.model.ResourceType;
import it.polimi.ingsw.network.requests.InitialResRequest;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class ResourceSelectionController implements Initializable {

    @FXML
    private Label lblMessage;
    @FXML
    private Button btnContinue;
    @FXML
    private RadioButton rbStone;
    @FXML
    private RadioButton rbCoin;
    @FXML
    private RadioButton rbShield;
    @FXML
    private RadioButton rbServant;
    @FXML
    private CheckBox chkStone1;
    @FXML
    private CheckBox chkStone2;
    @FXML
    private CheckBox chkCoin1;
    @FXML
    private CheckBox chkCoin2;
    @FXML
    private CheckBox chkShield1;
    @FXML
    private CheckBox chkShield2;
    @FXML
    private CheckBox chkServant1;
    @FXML
    private CheckBox chkServant2;
    @FXML
    private ToggleGroup toggleGroup;
    @FXML
    private ImageView stoneRes, coinRes, shieldRes, servantRes;

    /**
     * The map of selected resources.
     */
    private final Map<ResourceType, Integer> res = new HashMap<>();

    /**
     * The nickname of the current player..
     */
    private String nickname;

    /**
     * The order num of the current player (0 to 3)
     */
    private int numPlayer = 0;

    /**
     * The amount of resources to be selected (1 or 2)
     */
    private int selectionAmount = 0;

    /**
     * The counter of the selected resources.
     */
    private int resourceCounter = 0;

    /**
     * The reference to the mainController.
     */
    private MainController mainController = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initResources();
    }

    private void initResources(){
        res.put(ResourceType.STONE, 0);
        res.put(ResourceType.COIN, 0);
        res.put(ResourceType.SHIELD, 0);
        res.put(ResourceType.SERVANT, 0);
    }

    /**
     * Setter for nickname
     * @param nickname the nickname
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * Setter for numPlayer.
     * @param numPlayer the order number of the current player
     */
    public void setNumPlayer(int numPlayer) {
        this.numPlayer = numPlayer;
        selectionAmount = (numPlayer == 3 ? 2 : 1); //if ==3 then 2 else 1
        lblMessage.setText(selectionAmount == 1 ? "Select a resource" : "Select two resources");
        rbStone.setVisible(selectionAmount == 1);
        rbCoin.setVisible(selectionAmount == 1);
        rbShield.setVisible(selectionAmount == 1);
        rbServant.setVisible(selectionAmount == 1);
        chkStone1.setVisible(selectionAmount == 2);
        chkStone2.setVisible(selectionAmount == 2);
        chkCoin1.setVisible(selectionAmount == 2);
        chkCoin2.setVisible(selectionAmount == 2);
        chkShield1.setVisible(selectionAmount == 2);
        chkShield2.setVisible(selectionAmount == 2);
        chkServant1.setVisible(selectionAmount == 2);
        chkServant2.setVisible(selectionAmount == 2);
    }


    /**
     * Getter for main controller.
     * @return mainController
     */
    public MainController getMainController() {
        return mainController;
    }

    /**
     * Setter for mainController.
     * @param mainController the MainController instance
     */
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void onClickStone(ActionEvent actionEvent) {
        initResources();
        res.put(ResourceType.STONE, 1);
        resourceCounter = 1;
    }
    public void onClickCoin(ActionEvent actionEvent) {
        initResources();
        res.put(ResourceType.COIN, 1);
        resourceCounter = 1;
    }
    public void onClickShield(ActionEvent actionEvent) {
        initResources();
        res.put(ResourceType.SHIELD, 1);
        resourceCounter = 1;
    }
    public void onClickServant(ActionEvent actionEvent) {
        initResources();
        res.put(ResourceType.SERVANT, 1);
        resourceCounter = 1;
    }

    private void onClickChk1(CheckBox chk1, CheckBox chk2, ResourceType resourceType){
        if(chk1.isSelected()) {
            if (resourceCounter < 2) {
                res.put(resourceType, 1);
                resourceCounter++;
            } else {
                chk1.setSelected(false);
            }
        } else {
            resourceCounter--;
            res.put(resourceType, 0);
            if(chk2.isSelected()){
                chk1.setSelected(true);
                chk2.setSelected(false);
            }
        }
    }

    private void onClickChk2(CheckBox chk1, CheckBox chk2, ResourceType resourceType){
        if(chk2.isSelected()) {
            if (resourceCounter == 0) {
                chk1.setSelected(true);
                chk2.setSelected(false);
                res.put(resourceType, 1);
                resourceCounter++;
            } else if (resourceCounter == 1) {
                resourceCounter++;
                if (chk1.isSelected()) {
                    chk2.setSelected(true);
                    res.put(resourceType, 2);
                } else {
                    chk1.setSelected(true);
                    chk2.setSelected(false);
                    res.put(resourceType, 1);
                }
            } else {
                chk2.setSelected(false);
            }
        } else {
            //it is possible to unselect the checkBox2 only when checkBox1 is selected
            resourceCounter--;
            res.put(resourceType, 1);
        }
    }



    public void onClickStone1(ActionEvent actionEvent) {
        onClickChk1(chkStone1, chkStone2, ResourceType.STONE);
    }
    public void onClickCoin1(ActionEvent actionEvent) {
        onClickChk1(chkCoin1, chkCoin2, ResourceType.COIN);
    }
    public void onClickShield1(ActionEvent actionEvent) {
        onClickChk1(chkShield1, chkShield2, ResourceType.SHIELD);
    }
    public void onClickServant1(ActionEvent actionEvent) {
        onClickChk1(chkServant1, chkServant2, ResourceType.SERVANT);
    }
    public void onClickStone2(ActionEvent actionEvent) {
        onClickChk2(chkStone1, chkStone2, ResourceType.STONE);
    }
    public void onClickCoin2(ActionEvent actionEvent) {
        onClickChk2(chkCoin1, chkCoin2, ResourceType.COIN);
    }
    public void onClickShield2(ActionEvent actionEvent) {
        onClickChk2(chkShield1, chkShield2, ResourceType.SHIELD);
    }
    public void onClickServant2(ActionEvent actionEvent) {
        onClickChk2(chkServant1, chkServant2, ResourceType.SERVANT);
    }


    /**
     * Click event handler for btnContinue.
     * @param actionEvent the action event
     */
    public void onClickContinue(ActionEvent actionEvent) {
        if(resourceCounter == selectionAmount){
            InitialResRequest request = new InitialResRequest(res);
            request.setNumPlayer(numPlayer);
            request.setPlayer(nickname);
            mainController.sendMessage(request);
            hideOnContinue();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            String textMessage = (numPlayer == 3 ? "Please select 2 resources." : "Please select 1 resource.");
            alert.setHeaderText(textMessage);
            alert.show();
        }
    }

    private void hideOnContinue(){
        lblMessage.setText("Waiting for the other player's choice");
        lblMessage.setLayoutY(320);
        lblMessage.setLayoutX(510);
        rbStone.setVisible(false);
        rbCoin.setVisible(false);
        rbShield.setVisible(false);
        rbServant.setVisible(false);
        chkStone1.setVisible(false);
        chkStone2.setVisible(false);
        chkCoin1.setVisible(false);
        chkCoin2.setVisible(false);
        chkShield1.setVisible(false);
        chkShield2.setVisible(false);
        chkServant1.setVisible(false);
        chkServant2.setVisible(false);
        coinRes.setVisible(false);
        shieldRes.setVisible(false);
        servantRes.setVisible(false);
        stoneRes.setVisible(false);
        btnContinue.setVisible(false);
    }
}
