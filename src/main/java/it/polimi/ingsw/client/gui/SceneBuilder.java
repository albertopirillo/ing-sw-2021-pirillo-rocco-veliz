package it.polimi.ingsw.client.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SceneBuilder extends Application {

    private List<String> playerList;
    /*private static ClientGUI clientGUI;*/

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        //Load FXML
        FXMLLoader loader = Util.loadFXML("main");
        Parent root = loader.load();
        stage.setResizable(false);
        Scene scene = new Scene(root);
        stage.setScene(scene);

        //Load CSS
        String css = Util.getCSS("main");
        scene.getStylesheets().add(css);

        //Windows title and image
        stage.setTitle("Master of Renaissance");
        Image icon = new Image("/png/generic/inkwell.png");
        stage.getIcons().add(icon);

        //Tab creation
        this.playerList = new ArrayList<>();
        this.playerList.add("Player 1");
        this.playerList.add("Player 2");
        this.playerList.add("Player 3");
        MainController mainController = loader.getController();

        //Popup on quitting
       /* stage.setOnCloseRequest(event -> {
            event.consume();
            quit(stage);
        });*/

        mainController.init(this.playerList);
        /*clientGUI.setGuiController(mainController);*/
        stage.show();
    }

    /**
     * Sets the player list for the current game
     * @param playerList a list of String representing the players' nicknames
     */
    public void setPlayerList(List<String> playerList) {
        this.playerList = playerList;
    }

    /**
     * Pop-up that warns the player before making him quit the game
     * @param stage the stage that will be closed
     */
    public void quit(Stage stage) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Quit");
        alert.setHeaderText("You're about to quit");
        alert.setContentText("All data will be lost");
        Optional<ButtonType> button = alert.showAndWait();
        if (button.isPresent()) {
            if (button.get() == ButtonType.OK) {
                stage.close();
            }
        }
    }
}
