package it.polimi.ingsw.client.gui;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Optional;

public class SceneBuilder extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        Parent root = Util.loadFXML("test");
        stage.setResizable(false);
        Scene scene = new Scene(root);
        stage.setScene(scene);

        String css = Util.getCSS("market");
        scene.getStylesheets().add(css);

        stage.setTitle("Master of Renaissance");
        Image icon = new Image("/png/generic/inkwell.png");
        stage.getIcons().add(icon);

        stage.setOnCloseRequest(event -> {
            event.consume();
            quit(stage);
        });
        stage.show();
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
