package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.network.updates.GameOverUpdate;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class EndGameController implements Initializable {

    @FXML
    private Label lblResult, lblSummary, lblScoreSingle;
    @FXML
    private Label lblPlayer1, lblPlayer2, lblPlayer3, lblPlayer4;

    private MainController mainController;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lblResult.setText("");
        lblSummary.setText("");
        lblScoreSingle.setText("");
        lblPlayer1.setText("");
        lblPlayer2.setText("");
        lblPlayer3.setText("");
        lblPlayer4.setText("");
    }

    public void setData(GameOverUpdate gameOverUpdate, String nickname){
        int numPlayers = gameOverUpdate.getScores().keySet().size();

        if (numPlayers == 1){
            if(gameOverUpdate.isWin()){
                lblResult.setText("YOU WON!");
                    lblSummary.setText("Your final score is");
                    lblScoreSingle.setText(gameOverUpdate.getScores().get(nickname).toString());
            } else {
                lblResult.setText("YOU LOST!");
            }
        }

        if(numPlayers > 1){
            lblResult.setText("Game Over");
            lblSummary.setText("The final scores are the following:");
            for(int i = 0; i < numPlayers; i++){
                String playerName = gameOverUpdate.getRanking().get(i);
                String text = i + ": " + playerName + " - " + gameOverUpdate.getScores().get(playerName);
                switch(i){
                    case 0:
                        lblPlayer1.setText(text);
                        break;
                    case 1:
                        lblPlayer2.setText(text);
                        break;
                    case 2:
                        lblPlayer3.setText(text);
                        break;
                    case 3:
                        lblPlayer4.setText(text);
                        break;
                    default:
                        break;
                }
            }
        }
    }

    public void onClickExit(ActionEvent actionEvent) {
        mainController.closeEndGamePopup();
    }
}
