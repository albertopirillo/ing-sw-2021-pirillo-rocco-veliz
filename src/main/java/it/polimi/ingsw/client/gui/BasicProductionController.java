package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.exceptions.NegativeResAmountException;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.ResourceType;
import it.polimi.ingsw.network.requests.BasicProductionRequest;
import it.polimi.ingsw.network.requests.Request;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class BasicProductionController implements Initializable {

    private MainController mainController;

    @FXML
    private ChoiceBox choiceBox1, choiceBox2;
    @FXML
    private RadioButton rdIN1_1, rdIN1_2, rdIN1_3, rdIN1_4, rdIN2_1, rdIN2_2, rdIN2_3, rdIN2_4, rdOUT_1, rdOUT_2, rdOUT_3, rdOUT_4;
    @FXML
    private Button btnConfirm;
    @FXML
    private ToggleGroup toggleGroupIN1, toggleGroupIN2, toggleGroupOUT;

    private ResourceType input1, input2, output;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for (ChoiceBox choiceBox : Arrays.asList(choiceBox1, choiceBox2)) {
            choiceBox.getItems().addAll("Depot", "Strongbox");
            choiceBox.getSelectionModel().select("Depot");
        }
    }

    /**
     * Sets the MainController
     * @param mainController  the MainController to associate with this controller
     */
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    /**
     * Based on selection sets input1, input2 and output ResourceType
     */
    public void onClickStone1(ActionEvent actionEvent) { this.input1 = ResourceType.STONE; }
    public void onClickCoin1(ActionEvent actionEvent) { this.input1 = ResourceType.COIN; }
    public void onClickShield1(ActionEvent actionEvent) { this.input1 = ResourceType.SHIELD; }
    public void onClickServant1(ActionEvent actionEvent) { this.input1 = ResourceType.SERVANT; }
    public void onClickStone2(ActionEvent actionEvent) { this.input2 = ResourceType.STONE; }
    public void onClickCoin2(ActionEvent actionEvent) { this.input2 = ResourceType.COIN; }
    public void onClickShield2(ActionEvent actionEvent) { this.input2 = ResourceType.SHIELD; }
    public void onClickServant2(ActionEvent actionEvent) { this.input2 = ResourceType.SERVANT; }
    public void onClickStone3(ActionEvent actionEvent) { this.output = ResourceType.STONE; }
    public void onClickCoin3(ActionEvent actionEvent) { this.output = ResourceType.COIN; }
    public void onClickShield3(ActionEvent actionEvent) { this.output = ResourceType.SHIELD; }
    public void onClickServant3(ActionEvent actionEvent) { this.output = ResourceType.SERVANT; }

    public void onBtnConfirm(){
        if(mainController.isMainActionDone()) {
            String errorMsg = "You already performed an action this turn";
            ErrorAlert errorAlert = new ErrorAlert(errorMsg);
            errorAlert.showAndWait();
        }
        else {
            try {
                Resource depotResource = new Resource(0, 0, 0, 0);
                Resource strongboxResource = new Resource(0, 0, 0, 0);
                if (choiceBox1.getSelectionModel().getSelectedIndex() == 0) {
                    //if choicebox1 equals depot
                    depotResource.modifyValue(input1, 1);
                } else {
                    strongboxResource.modifyValue(input1, 1);
                }
                if (choiceBox2.getSelectionModel().getSelectedIndex() == 0) {
                    //if choicebox2 equals depot
                    depotResource.modifyValue(input2, 1);
                } else {
                    strongboxResource.modifyValue(input2, 1);
                }

                Request request = new BasicProductionRequest(input1, input2, output, depotResource, strongboxResource);
                mainController.sendMessage(request);
                mainController.closeBasic();
            } catch (NegativeResAmountException e) {
                e.printStackTrace();
            }
        }
    }
}
