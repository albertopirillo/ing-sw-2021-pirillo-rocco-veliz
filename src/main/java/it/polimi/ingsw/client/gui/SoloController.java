package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.model.DevelopmentCard;
import it.polimi.ingsw.model.SoloActionToken;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class SoloController implements Initializable {

    private MainController mainController;

    @FXML
    private ImageView nextToken, firstCard, secondCard;
    @FXML
    private Label lastTokenLabel;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void updateTokens(SoloActionToken token) {
        nextToken.setImage(Util.getActionToken(token.getID()));
    }

    public void continueGame() {
        mainController.switchSoloPopup();
        lastTokenLabel.setText("Lorenzo did not discard any card");
        firstCard.setVisible(false);
        secondCard.setVisible(false);
    }

    public void updateDiscardedCards(List<DevelopmentCard> cards) {
        lastTokenLabel.setText("Lorenzo discarded the following cards:");
        firstCard.setImage(Util.getDevCardImg(cards.get(0).getImg()));
        secondCard.setImage(Util.getDevCardImg(cards.get(1).getImg()));
        firstCard.setVisible(true);
        secondCard.setVisible(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lastTokenLabel.setText("Lorenzo did not discard any card");
        firstCard.setVisible(false);
        secondCard.setVisible(false);
    }
}
