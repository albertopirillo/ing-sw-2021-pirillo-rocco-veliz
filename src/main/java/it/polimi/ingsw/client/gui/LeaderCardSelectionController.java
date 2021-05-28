package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.model.LeaderCard;
import it.polimi.ingsw.network.requests.ChooseLeaderRequest;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class LeaderCardSelectionController implements Initializable {
    @FXML
    private Button btnContinue;
    @FXML
    private ImageView imgCard1;
    @FXML
    private ImageView imgCard2;
    @FXML
    private ImageView imgCard3;
    @FXML
    private ImageView imgCard4;
    @FXML
    private CheckBox chkCard1;
    @FXML
    private CheckBox chkCard2;
    @FXML
    private CheckBox chkCard3;
    @FXML
    private CheckBox chkCard4;

    private final List<ImageView> imageViews = new ArrayList<>();
    private final List<CheckBox> checkBoxes = new ArrayList<>();

    private List<LeaderCard> leaderCards;


    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    private String nickname;

    /**
     * The reference to the mainController.
     */
    private MainController mainController = null;

    /**
     * An array storing the selected cards.
     */
    List<Integer> selectedCards = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        imageViews.add(imgCard1);
        imageViews.add(imgCard2);
        imageViews.add(imgCard3);
        imageViews.add(imgCard4);
        checkBoxes.add(chkCard1);
        checkBoxes.add(chkCard2);
        checkBoxes.add(chkCard3);
        checkBoxes.add(chkCard4);
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

    private void checkAndSet(Integer index){
        CheckBox chkCard = checkBoxes.get(index);
        if(countSelected() > 2){
            chkCard.setSelected(false);
        }
        if(chkCard.isSelected()){
            selectedCards.add(index);
        } else {
            selectedCards.remove(index);
        }
    }

    public void onClickChkCard1(ActionEvent actionEvent) {
        checkAndSet(0);
    }

    public void onClickChkCard2(ActionEvent actionEvent) {
        checkAndSet(1);
    }

    public void onClickChkCard3(ActionEvent actionEvent) {
        checkAndSet(2);
    }

    public void onClickChkCard4(ActionEvent actionEvent) {
        checkAndSet(3);
    }

    public List<Integer> getSelectedCards(){
        return selectedCards;
    }

    public void onClickContinue(ActionEvent actionEvent) {
        if(countSelected() == 2){
            ChooseLeaderRequest request = new ChooseLeaderRequest(selectedCards.get(0), selectedCards.get(1));
            request.setPlayer(nickname);
            mainController.sendMessage(request);
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("You must select 2 development cards.");
            alert.show();
        }
    }

    private int countSelected(){
        int ret = 0;
        for (CheckBox checkBox : checkBoxes) {
            if (checkBox.isSelected()) {
                ret++;
            }
        }
        return ret;
    }

    public void setLeaderCards(List<LeaderCard> cards) {
        this.leaderCards = cards;
        for(int i = 0; i < cards.size(); i++){
            setImage(i);
        }
    }

    private void setImage(int index){
        LeaderCard card = this.leaderCards.get(index);
        String img = card.getImg();
        imageViews.get(index).setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/png/leader_cards/" + img))));
    }
}